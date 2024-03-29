package us.lsi.sevici;

import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.sevici.RedA.TipoImplementacion;


public interface Red {
	
	public static void setTipoImplementacion(TipoImplementacion tipo) {
		RedA.tipo = tipo;
	}
	
	public static Red of(Set<Estacion> marcas) {
		return switch(RedA.tipo) {
		case Funcional->RedF.of(marcas);
		case Imperativa->RedI.of(marcas);
		};
	}
	
	public static Red parse(String fichero) {
		return switch(RedA.tipo) {
		case Funcional->RedF.parse(fichero);
		case Imperativa->RedI.parse(fichero);
		};
	}
		
	void add(Estacion e);
	
	void remove(Estacion e);
	
	Set<Estacion> estaciones();

	Integer numero();

	Estacion estacion(Integer numero);

	Set<Estacion> estacion(String nombre);

	Set<Estacion> estacionesConBicisDisponibles();

	Set<Estacion> estacionesConBicisDisponibles(Integer n);

	Set<Coordenadas2D> ubicaciones();

	Set<Coordenadas2D> ubicacionEstacionesDisponibles(Integer k);

	Estacion estacionMasBicisDisponibles();

	Map<Integer, List<Estacion>> estacionesPorBicisDisponibles();

	Map<Integer, Integer> numeroDeEstacionesPorBicisDisponibles();

	void escribe(Integer n, String file);

}