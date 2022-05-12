package us.lsi.sevici;

import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.ruta.RutaA.TipoImplementacion;

public interface Red {
	
	public static void setTipoImplementacion(TipoImplementacion tipo) {
		RedA.tipo = tipo;
	}
	
	public static Red of(List<Estacion> marcas) {
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