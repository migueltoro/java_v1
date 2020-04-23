package us.lsi.sevici;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.FileTools;

public class Red {
	
	public static Red parse(String fichero) {
		List<Estacion> estaciones = FileTools.streamFromFile("ficheros/estaciones.csv")
				.skip(1)
				.map(linea->Estacion.parse(linea))
				.collect(Collectors.toList());
		return new Red(estaciones);
	}
	
	public static Red parse2(String fichero) {
		List<String> est = FileTools.lineasFromFile("ficheros/estaciones.csv");
		List<Estacion> estaciones = new ArrayList<>();
		List<String> est2 = est.subList(1, est.size());
		for(String linea: est2) {
			Estacion e = Estacion.parse(linea);
			estaciones.add(e);
		}		
		return new Red(estaciones);
	}
	
	private List<Estacion> estaciones;

	
	private Red(List<Estacion> estaciones) {
		super();
		this.estaciones = estaciones;
	}
	
	public Integer getNumero() {
		return this.estaciones.size();
	}
	
	public Estacion getPorNumero(Integer numero) {
		Optional<Estacion> r = this.estaciones.stream().filter(e->e.getNumero().equals(numero)).findFirst();
		return r.get();		
	}
	
	public Estacion getPorName(String nombre) {
		Optional<Estacion> r = this.estaciones.stream().filter(e->e.getName().equals(nombre)).findFirst();
		return r.get();	
	}
	
	public Set<Estacion> getEstacionesConBicisDisponibles() {
		return this.estaciones.stream().filter(e->e.getFree_bikes()>0).collect(Collectors.toSet());
	}
	
	public Set<Estacion> getEstacionesConBicisDisponibles(Integer n){
		return this.estaciones.stream().filter(e->e.getFree_bikes()>=n).collect(Collectors.toSet());
	}
	
	public Set<Coordenadas2D> getUbicaciones(){
		return this.estaciones.stream().map(e->e.getCoordenadas()).collect(Collectors.toSet());
	}
	
	public Set<Coordenadas2D> getUbicacionEstacionesDisponibles(Integer k){
		return this.estaciones.stream().filter(e->e.getFree_bikes()>=k).map(e->e.getCoordenadas()).collect(Collectors.toSet());
	}
	
	public Estacion getEstacionMasBicisDisponibles() {
		return this.estaciones.stream().max(Comparator.comparing(e->e.getFree_bikes())).get();
	}
	
	public Map<Integer,List<Estacion>> getEstacionesPorBicisDisponibles(){
		return this.estaciones.stream().collect(Collectors.groupingBy(e->e.getFree_bikes()));
	}
	
	public Map<Integer,Long> getNumeroDeEstacionesPorBicisDisponibles() {
		return this.estaciones.stream().collect(Collectors.groupingBy(e->e.getFree_bikes(),Collectors.counting()));
	}

	@Override
	public String toString() {
		return estaciones.stream().map(e->e.toString()).collect(Collectors.joining("\n"));
	}
	
	
	
}
