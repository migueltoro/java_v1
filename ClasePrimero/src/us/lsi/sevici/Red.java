package us.lsi.sevici;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
	
	public Estacion getPorNumero2(Integer numero) {
		Estacion a = null;
		for(Estacion e:this.estaciones) {
			if(e.getNumero().equals(numero)) {
				a = e;
				break;
			}
		}
		return Optional.ofNullable(a).get();
	}
	
	public Estacion getPorName(String nombre) {
		Optional<Estacion> r = this.estaciones.stream().filter(e->e.getName().equals(nombre)).findFirst();
		return r.get();	
	}
	
	public Estacion getPorNombre2(String nombre) {
		Estacion a = null;
		for(Estacion e:this.estaciones) {
			if(e.getName().equals(nombre)) {
				a = e;
				break;
			}
		}
		return Optional.ofNullable(a).get();
	}
	
	public Set<Estacion> getEstacionesConBicisDisponibles() {
		return this.estaciones.stream().filter(e->e.getFree_bikes()>0).collect(Collectors.toSet());
	}
	
	public Set<Estacion> getEstacionesConBicisDisponibles2() {
		Set<Estacion> a = new HashSet<>();
		for(Estacion e:this.estaciones) {
			if(e.getFree_bikes()>0) {
				a.add(e);
			}
		}
		return a;
	}
	
	public Set<Estacion> getEstacionesConBicisDisponibles(Integer n){
		return this.estaciones.stream().filter(e->e.getFree_bikes()>=n).collect(Collectors.toSet());
	}
	
	public Set<Estacion> getEstacionesConBicisDisponibles2(Integer n) {
		Set<Estacion> a = new HashSet<>();
		for(Estacion e:this.estaciones) {
			if(e.getFree_bikes()>n) {
				a.add(e);
			}
		}
		return a;
	}
	
	public Set<Coordenadas2D> getUbicaciones(){
		return this.estaciones.stream().map(e->e.getCoordenadas()).collect(Collectors.toSet());
	}
	
	public Set<Coordenadas2D> getUbicaciones2() {
		Set<Coordenadas2D> a = new HashSet<>();
		for(Estacion e:this.estaciones) {
				Coordenadas2D c = e.getCoordenadas();
				a.add(c);
		}
		return a;
	}
	
	public Set<Coordenadas2D> getUbicacionEstacionesDisponibles(Integer k){
		return this.estaciones.stream().filter(e->e.getFree_bikes()>=k).map(e->e.getCoordenadas()).collect(Collectors.toSet());
	}
	
	public Set<Coordenadas2D> getUbicacionEstacionesDisponibles2(Integer k){
		Set<Coordenadas2D> a = new HashSet<>();
		for(Estacion e:this.estaciones) {
				if (e.getFree_bikes()>=k) {
					Coordenadas2D c = e.getCoordenadas();
					a.add(c);
				}
		}
		return a;
	}
	
	
	public Estacion getEstacionMasBicisDisponibles() {
		return this.estaciones.stream().max(Comparator.comparing(e->e.getFree_bikes())).get();
	}
	
	public Estacion getEstacionMasBicisDisponibles2() {
		Estacion a = null;
		for(Estacion e: this.estaciones){
			if(a == null || e.getFree_bikes() > a.getFree_bikes()) {   // con el comparador
				a = e;
			}	
		}
		return Optional.ofNullable(a).get();	
	}
	
	
	public Map<Integer,List<Estacion>> getEstacionesPorBicisDisponibles(){
		return this.estaciones.stream().collect(Collectors.groupingBy(e->e.getFree_bikes()));
	}
	
	
	public Map<Integer,List<Estacion>> getEstacionesPorBicisDisponibles2(){
		Map<Integer,List<Estacion>> a = new HashMap<>();
		for(Estacion e: this.estaciones){
			Integer key = e.getFree_bikes();
			if(!a.keySet().contains(key)){
				a.put(key, new ArrayList<>());
			}
			a.get(key).add(e);
		}
		return a;

	}
	
	public Map<Integer,Integer> getNumeroDeEstacionesPorBicisDisponibles() {
		return this.estaciones.stream()
				.collect(Collectors.groupingBy(e->e.getFree_bikes(),
						Collectors.collectingAndThen(Collectors.counting(),r->r.intValue())));
//						Collectors.counting()));
	}
	
	public Map<Integer,Integer> getNumeroDeEstacionesPorBicisDisponibles2() {
		Map<Integer,Integer> a = new HashMap<>();
		for(Estacion e: this.estaciones){
			Integer key = e.getFree_bikes();
			Integer r = 1 + a.getOrDefault(key,0);
			a.put(key,r);	
		}
		return a;
	}
	
	@Override
	public String toString() {
		return estaciones.stream().map(e->e.toString()).collect(Collectors.joining("\n"));
	}
	
}
