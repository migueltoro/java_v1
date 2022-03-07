package us.lsi.sevici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;

public class RedI extends RedA implements Red{
	
	public static Red parse(String fichero) {
		List<String> lineas = FileTools.lineasFromFile("ficheros/estaciones.csv");
		lineas = lineas.subList(1, lineas.size());
		List<Estacion> estaciones = new ArrayList<>();
		for(String ln:lineas) {
			Estacion e = Estacion.parse(ln);
			estaciones.add(e);
		}
		return of(estaciones);
	}

	public static RedI of(List<Estacion> estaciones) {
		return new RedI(estaciones);
	}

	private RedI(List<Estacion> estaciones) {
		super(estaciones);
	}
	
	@Override
	public Map<Integer, Estacion> indices() {
		if (super.indices == null) {
			super.indices = new HashMap<>();
			for (Estacion e : estaciones) {
				Preconditions.checkArgument(!indices.containsKey(e.numero()),
						String.format("El numero %d de estacion esta repetido", e.numero()));
				super.indices.put(e.numero(), e);
			}
		}
		return super.indices;
	}

	@Override
	public Estacion porNumero(Integer numero) {
		return null;
	}

	@Override
	public Set<Estacion> porName(String nombre) {
		return null;
	}

	@Override
	public Set<Estacion> estacionesConBicisDisponibles() {
		return null;
	}

	@Override
	public Set<Estacion> estacionesConBicisDisponibles(Integer n) {
		return null;
	}

	@Override
	public List<Coordenadas2D> ubicaciones() {
		return null;
	}

	@Override
	public List<Coordenadas2D> ubicacionEstacionesDisponibles(Integer k) {
		return null;
	}

	@Override
	public Estacion estacionMasBicisDisponibles() {
		return null;
	}

	@Override
	public Map<Integer, List<Estacion>> estacionesPorBicisDisponibles() {
		return null;
	}

	@Override
	public Map<Integer, Integer> numeroDeEstacionesPorBicisDisponibles() {
		return null;
	}

	@Override
	public void escribe(Integer n, String file) {
		
	}
	
	@Override
	public String toString() {
		return null;
	}

}
