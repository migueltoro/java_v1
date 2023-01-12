package us.lsi.ruta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.ruta.Intervalo.Type;
import us.lsi.tools.FileTools;

public class RutaI extends RutaA implements Ruta {

	public static Ruta of(List<Marca> marcas) {
		return new RutaI(marcas);
	}
	
	public static Ruta leeDeFichero(String fichero) {
		List<Marca> marcas = new ArrayList<>();
		for (String linea:FileTools.lineasDeFichero("ficheros/ruta.csv")) {
			Marca m = Marca.parse(linea);
			marcas.add(m);
		}
		return of(marcas);
	}

	private RutaI(List<Marca> marcas) {
		super(marcas);
	}

	public Double getLongitud() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
			Double ln = it.longitud();
			a = a + ln;
		}
		return a;
	}

	@Override
	public Double getTiempo() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
			Double ln = it.tiempo();
			a = a + ln;
		}
		return a;
	}

	@Override
	public Double getDesnivelCrecienteAcumulado() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
			if (it.desnivel()>0) {
				Double ln = it.longitud();
				a = a + ln;
			}
		}
		return a;
	}

	@Override
	public Double getDesnivelDecrecienteAcumulado() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
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
	public Map<Type, Integer> getFrecuencias() {
		Map<Type,Integer> m = new HashMap<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.getIntervalo(i);
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
	public Set<Intervalo> getLLanos() {
		Set<Intervalo> s = new HashSet<>();
		Integer n = this.marcas.size();
		for(Integer i=0; i< n-1; i++) {
			Intervalo in = this.getIntervalo(i);
			if (in.type().equals(Type.Llano)) {
				s.add(in);
			}
		}
		return s;
	}

	
}
