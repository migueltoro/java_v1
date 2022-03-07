package us.lsi.sevici;

import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.coordenadas.Coordenadas2D;

public interface Red {
	
	void add(Estacion e);
	
	void remove(Estacion e);
	
	List<Estacion> estaciones();
	
	Map<Integer, Estacion> indices();

	Integer numero();

	Estacion porNumero(Integer numero);

	Set<Estacion> porName(String nombre);

	Set<Estacion> estacionesConBicisDisponibles();

	Set<Estacion> estacionesConBicisDisponibles(Integer n);

	List<Coordenadas2D> ubicaciones();

	List<Coordenadas2D> ubicacionEstacionesDisponibles(Integer k);

	Estacion estacionMasBicisDisponibles();

	Map<Integer, List<Estacion>> estacionesPorBicisDisponibles();

	Map<Integer, Integer> numeroDeEstacionesPorBicisDisponibles();

	void escribe(Integer n, String file);

}