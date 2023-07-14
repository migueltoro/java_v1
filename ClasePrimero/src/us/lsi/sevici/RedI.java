package us.lsi.sevici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class RedI extends RedA implements Red{
	
	public static Red parse(String fichero) {
		List<String> lineas = File2.lineasDeFichero("ficheros/estaciones.csv","WINDOWS-1252");
		lineas = lineas.subList(1, lineas.size());
		Set<Estacion> estaciones = new HashSet<>();
		for(String ln:lineas) {
			Estacion e = Estacion.parse(ln);
			estaciones.add(e);
		}
		return RedI.of(estaciones);
	}

	public static RedI of(Set<Estacion> estaciones) {
		return new RedI(estaciones);
	}

	private RedI(Set<Estacion> estaciones) {
		super(estaciones);
	}
	
	@Override
	public Map<Integer, Estacion> indices() {
		if (super.indices == null) {
			Map<Integer, Estacion> m = new HashMap<>();
			for (Estacion e : super.estaciones()) {
				Integer key = e.numero();
				if (m.containsKey(key)) {
					Preconditions.checkArgument(false,
							String.format("El numero %d de estacion esta repetido", e.numero()));
				} else {
					m.put(key, e);
				}
			}
			super.indices = m;
		}
		return super.indices;
	}

	@Override
	public Set<Estacion> estacion(String nombre) {
		Set<Estacion> s = new HashSet<>();
		for(Estacion e:super.estaciones()) {
			if(e.name().equals(nombre))
				s.add(e);
		}
		return s;
	}

	@Override
	public Set<Estacion> estacionesConBicisDisponibles() {
		Set<Estacion> s = new HashSet<>();
		for(Estacion e:super.estaciones()) {
			if(e.free_bikes() > 0)
				s.add(e);
		}
		return s;
	}

	@Override
	public Set<Estacion> estacionesConBicisDisponibles(Integer n) {
		Set<Estacion> s = new HashSet<>();
		for(Estacion e:super.estaciones()) {
			if(e.free_bikes() >= n)
				s.add(e);
		}
		return s;
	}

	@Override
	public Set<Coordenadas2D> ubicaciones() {
		Set<Coordenadas2D> s = new HashSet<>();
		for(Estacion e:super.estaciones()) {
			Coordenadas2D c = e.coordenadas();
			s.add(c);
		}
		return s;
	}

	@Override
	public Set<Coordenadas2D> ubicacionEstacionesDisponibles(Integer k) {
		Set<Coordenadas2D> s = new HashSet<>();
		for(Estacion e:super.estaciones()) {
			if (e.free_bikes() >= k) {
				Coordenadas2D c = e.coordenadas();
				s.add(c);
			}
		}
		return s;
	}

	@Override
	public Estacion estacionMasBicisDisponibles() {
		Estacion em = null;
		for(Estacion e:super.estaciones()) {
			if(em == null || e.free_bikes() > em.free_bikes())
				em = e;
		}
		return em;
	}

	@Override
	public Map<Integer, List<Estacion>> estacionesPorBicisDisponibles() {
		Map<Integer, List<Estacion>> m = new HashMap<>();
		for (Estacion e : super.estaciones()) {
			Integer key = e.free_bikes();
			if (m.containsKey(key)) {
				m.get(key).add(e);
			} else {
				List<Estacion> ls = new ArrayList<>();
				ls.add(e);
				m.put(key, ls);
			}
		}
		return m;
	}

	@Override
	public Map<Integer, Integer> numeroDeEstacionesPorBicisDisponibles() {
		Map<Integer, Integer> m = new HashMap<>();
		for (Estacion e : super.estaciones()) {
			Integer key = e.free_bikes();
			if (m.containsKey(key)) {
				Integer n = m.get(key);
				n++;
				m.put(key,n);
			} else {
				m.put(key,1);
			}
		}
		return m;
	}	

	@Override
	public void escribe(Integer n, String file) {		
		String r = "";
		Integer i = 0;
		for(Estacion e:this.estaciones()) {
			r = r+e.toString()+"\n";
			i++;
			if(i == n) break;
		}
		File2.write(file, r);
	}
	
	@Override
	public String toString() {
		String r = "";
		for(Estacion e: this.estaciones()) {
			r += e.toString()+"\n";
		}
		return r;
	}

}
