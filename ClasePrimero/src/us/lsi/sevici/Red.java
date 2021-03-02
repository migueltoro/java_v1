package us.lsi.sevici;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.FileTools;

public record Red(List<Estacion> estaciones) {

	public static Red parse(String fichero) {
		List<Estacion> estaciones = FileTools.streamFromFile("ficheros/estaciones.csv").skip(1)
				.map(linea -> Estacion.parse(linea)).collect(Collectors.toList());
		return new Red(estaciones);
	}

	public Integer numero() {
		return this.estaciones.size();
	}

	public Estacion porNumero(Integer numero) {
		Optional<Estacion> r = this.estaciones.stream().filter(e -> e.numero().equals(numero)).findFirst();
		return r.get();
	}

	public Estacion porName(String nombre) {
		Optional<Estacion> r = this.estaciones.stream().filter(e -> e.name().equals(nombre)).findFirst();
		return r.get();
	}

	public Set<Estacion> estacionesConBicisDisponibles() {
		return this.estaciones.stream().filter(e -> e.free_bikes() > 0).collect(Collectors.toSet());
	}

	public Set<Estacion> estacionesConBicisDisponibles(Integer n) {
		return this.estaciones.stream().filter(e -> e.free_bikes() >= n).collect(Collectors.toSet());
	}

	public Set<Coordenadas2D> ubicaciones() {
		return this.estaciones.stream().map(e -> e.coordenadas()).collect(Collectors.toSet());
	}

	public Set<Coordenadas2D> ubicacionEstacionesDisponibles(Integer k) {
		return this.estaciones.stream().filter(e -> e.free_bikes() >= k).map(e -> e.coordenadas())
				.collect(Collectors.toSet());
	}

	public Estacion estacionMasBicisDisponibles() {
		return this.estaciones.stream().max(Comparator.comparing(e -> e.free_bikes())).get();
	}

	public Map<Integer, List<Estacion>> estacionesPorBicisDisponibles() {
		return this.estaciones.stream().collect(Collectors.groupingBy(e -> e.free_bikes()));
	}

	public Map<Integer, Integer> numeroDeEstacionesPorBicisDisponibles() {
		return this.estaciones.stream().collect(Collectors.groupingBy(e -> e.free_bikes(),
				Collectors.collectingAndThen(Collectors.counting(), r -> r.intValue())));
	}

	@Override
	public String toString() {
		return estaciones.stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
	}
	
	public void escribe(Integer n, String file) {
		Stream<String> s = this.estaciones.subList(0,n).stream().map(e->e.toString());
		FileTools.writeStream(s,file);
	}

}
