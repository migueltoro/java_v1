package us.lsi.ruta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.ruta.Intervalo.Type;
import us.lsi.tools.File2;

public class RutaI extends RutaA implements Ruta {

	public static Ruta of(List<Marca> marcas) {
		return new RutaI(marcas);
	}
	
	public static Ruta of(String fichero) {
		List<Marca> marcas = new ArrayList<>();
		for (String linea:File2.lineasDeFichero("ficheros/ruta.csv")) {
			Marca m = Marca.parse(linea);
			marcas.add(m);
		}
		return of(marcas);
	}
	
	
	private RutaI(List<Marca> marcas) {
		super(marcas);
	}

	public Double longitud() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.intervalo(i);
			Double ln = it.longitud();
			a = a + ln;
		}
		return a;
	}

	@Override
	public Double tiempo() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.intervalo(i);
			Double ln = it.tiempo();
			a = a + ln;
		}
		return a;
	}

	@Override
	public Double desnivelCrecienteAcumulado() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.intervalo(i);
			if (it.desnivel()>0) {
				Double ln = it.longitud();
				a = a + ln;
			}
		}
		return a;
	}

	@Override
	public Double desnivelDecrecienteAcumulado() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.intervalo(i);
			if (it.desnivel()<0) {
				Double ln = it.longitud();
				a = a + ln;
			}
		}
		return a;
	}
	
	@Override
	public String toString() {
		String r = marcas.get(0).toString();
		for(int i = 1;i<marcas.size();i++) {
			r += "\n"+marcas.get(i).toString();
		}
		return r;
	}
	
	@Override
	public Map<Type, Integer> frecuencias() {
		Map<Type,Integer> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			Type key = in.type();
			m.put(key,m.getOrDefault(key,0)+1);		
		}
		return m;
	}
	
	
	public Map<Type, Integer> frecuenciasE() {
		Map<Type,Integer> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			Type key = in.type();
			if(m.containsKey(key)) {
				Integer v = m.get(key);
				m.put(key, v+1);
			} else {
				m.put(key, 1);
			}				
		}
		return m;
	}

	@Override
	public Set<Intervalo> llanos() {
		Set<Intervalo> s = new HashSet<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			if (in.type().equals(Type.Llano)) {
				s.add(in);
			}
		}
		return s;
	}
	
	@Override
	public Map<Type, List<Intervalo>> agrupaPorTipoLista() {
		Map<Type,List<Intervalo>> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			Type key = in.type();
			m.computeIfAbsent(key, k->new ArrayList<>()).add(in);	
		}
		return m;
	}
	
	@Override
	public Map<Type, List<Intervalo>> limita(Map<Type, List<Intervalo>> m, Integer n) {
		Map<Type, List<Intervalo>> r = new HashMap<>();
		for (Type t : m.keySet()) {
			List<Intervalo> v = m.get(t);
			if (v.size() > n) {
				r.put(t, v.subList(0, n));
			} else {
				r.put(t, v);
			}
		}
		return r;
	}

	public Map<Type, List<Intervalo>> agrupaPorTipoListaE() {
		Map<Type,List<Intervalo>> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			Type key = in.type();
			if(m.containsKey(key)) {
				List<Intervalo> v = m.get(key);
				v.add(in);
				m.put(key, v);
			} else {
				List<Intervalo> v = new ArrayList<>();
				v.add(in);
				m.put(key, v);
			}				
		}
		return m;
	}
	
	@Override
	public Map<Type, Set<Intervalo>> agrupaPorTipoConjunto() {
		Map<Type,Set<Intervalo>> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			Type key = in.type();
			m.computeIfAbsent(key, k->new HashSet<>()).add(in);			
		}
		return m;
	}

	
	public Map<Type, Set<Intervalo>> agrupaPorTipoConjuntoE() {
		Map<Type,Set<Intervalo>> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.intervalo(i);
			Type key = in.type();
			if(m.containsKey(key)) {
				Set<Intervalo> v = m.get(key);
				v.add(in);
				m.put(key, v);
			} else {
				Set<Intervalo> v = new HashSet<>();
				v.add(in);
				m.put(key, v);
			}				
		}
		return m;
	}

	@Override
	public String imprimeGrupos(Map<Type, List<Intervalo>> m) {
		String r = "";
		for(Type t:m.keySet()) {
			r += "\n"+ t + " -> ";
			for(Intervalo in:m.get(t)) {
                r += "\n\t"+in.toString();
			}
		}
		return r;
	}
	
}
