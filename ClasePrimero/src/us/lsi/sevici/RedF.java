package us.lsi.sevici;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;

public class RedF extends RedA implements Red {
	
	public static Red parse(String fichero) {
		List<Estacion> estaciones = FileTools.streamFromFile("ficheros/estaciones.csv").skip(1)
				.map(linea -> Estacion.parse(linea)).collect(Collectors.toList());
		return of(estaciones);
	}

	public static RedF of(List<Estacion> estaciones) {
		return new RedF(estaciones);
	}

	private RedF(List<Estacion> estaciones) {
		super(estaciones);
	}
	
	@Override
	public Map<Integer, Estacion> indices() {
		if(super.indices == null) 
			super.indices= estaciones.stream().collect(Collectors.toMap(e->e.numero(),e->e));
		return super.indices;
	}

	@Override
	public Estacion porNumero(Integer numero) {
		Estacion e = this.indices().getOrDefault(numero,null);
		Preconditions.checkNotNull(e);
		return e;
	}

	@Override
	public Set<Estacion> porName(String nombre) {
		return this.estaciones.stream().filter(e -> e.name().equals(nombre)).collect(Collectors.toSet());
	}

	@Override
	public Set<Estacion> estacionesConBicisDisponibles() {
		return this.estaciones.stream().filter(e -> e.free_bikes() > 0).collect(Collectors.toSet());
	}

	@Override
	public Set<Estacion> estacionesConBicisDisponibles(Integer n) {
		return this.estaciones.stream().filter(e -> e.free_bikes() >= n).collect(Collectors.toSet());
	}

	@Override
	public List<Coordenadas2D> ubicaciones() {
		return this.estaciones.stream().map(e -> e.coordenadas()).collect(Collectors.toList());
	}


	@Override
	public List<Coordenadas2D> ubicacionEstacionesDisponibles(Integer k) {
		return this.estaciones.stream().filter(e -> e.free_bikes() >= k).map(e -> e.coordenadas())
				.collect(Collectors.toList());
	}

	@Override
	public Estacion estacionMasBicisDisponibles() {
		return this.estaciones.stream().max(Comparator.comparing(e -> e.free_bikes())).get();
	}

	@Override
	public Map<Integer, List<Estacion>> estacionesPorBicisDisponibles() {
		return this.estaciones.stream().collect(Collectors.groupingBy(e -> e.free_bikes()));
	}

	@Override
	public Map<Integer, Integer> numeroDeEstacionesPorBicisDisponibles() {
		return this.estaciones.stream().collect(Collectors.groupingBy(e -> e.free_bikes(),
				Collectors.collectingAndThen(Collectors.toList(),ls->ls.size())));
	}

	@Override
	public String toString() {
		return estaciones.stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
	}
	
	@Override
	public void escribe(Integer n, String file) {
		Stream<String> s = this.estaciones.subList(0,n).stream().map(e->e.toString());
		FileTools.writeStream(s,file);
	}

}
