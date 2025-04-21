package us.lsi.aeropuerto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class PreguntasImperativo implements Preguntas {

	PreguntasImperativo() {
	}
	
	//1. Dada una cadena de caracteres s devuelve el n�mero total de pasajeros a
	// ciudades destino que tienen
	// como prefijo s (esto es, comienzan por s).
	
	public Integer numeroDepasajeros(String prefix) {
		Vuelos ls = Vuelos.of();
		Integer sum = 0;
		for(Vuelo ocp:ls.todas()) {
			if(ocp.vueloProgramado().ciudadDestino().startsWith(prefix)) {
				Integer numPasajeros = ocp.numPasajeros();
				sum = sum + numPasajeros;
			}
		}
		return sum;
	}
	
	//2.  Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
	// existe un vuelo en la fecha f con destino en s.
	
	public Boolean hayDestino(Set<String> destinos, LocalDate f) {
		Vuelos ls = Vuelos.of();
		Boolean a = false;
		for(Vuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f)) {
				if(destinos.contains(ocp.vueloProgramado().ciudadDestino())) {
					a = true;
					break;
				}
			}
		}
		return a;
	}
	
	public Boolean hayDestino2(Set<String> destinos, LocalDate f) {
		Vuelos ls = Vuelos.of();
		Vuelo a = null;
		for(Vuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f) &&
			  destinos.contains(ocp.vueloProgramado().ciudadDestino())) {
					a = ocp;
					break;
			}
		}
		return a != null;
	}

	//3. Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
	// los vuelos de fecha f
	
	public Set<String> destinosDiferentes(LocalDate f) {
		Vuelos ls = Vuelos.of();
		Set<String> a = new HashSet<>();
		for(Vuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f)) {
				String ciudadDestino = ocp.vueloProgramado().ciudadDestino();
				a.add(ciudadDestino);			
			}
		}
		return a;
	}
	
	
	public SortedSet<String> destinosDiferentes2(LocalDate f) {
		Vuelos ls = Vuelos.of();
		SortedSet<String> a = new TreeSet<>(Comparator.naturalOrder());
		for(Vuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f)) {
				String ciudadDestino = ocp.vueloProgramado().ciudadDestino();
				a.add(ciudadDestino);			
			}
		}
		return a;
	}
	
	public List<String> destinosDiferentes3(LocalDate f) {
		Vuelos ls = Vuelos.of();
		Set<String> a = new HashSet<>();
		for(Vuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f)) {
				String ciudadDestino = ocp.vueloProgramado().ciudadDestino();
				a.add(ciudadDestino);			
			}
		}
		List<String> nls = new ArrayList<>();
		for(String s:a) {
			nls.add(s);
		}
		Collections.sort(nls);
		return nls;
	}
	
	//4. Dado un anyo devuelve un SortedMap que relacione cada destino con el
	// total de pasajeros a ese destino en el a�o anyo
	
	public SortedMap<String, Integer> totalPasajerosADestino(Integer any) {
		Vuelos ls = Vuelos.of();
		SortedMap<String,Integer> a = new TreeMap<String, Integer>(Comparator.reverseOrder());
		for(Vuelo ocp:ls.todas()) {
			if(ocp.fecha().getYear() == any) {
				String key = ocp.vueloProgramado().ciudadDestino();
				if(a.containsKey(key)) {
					Integer numPasajeros = a.get(key)+ocp.numPasajeros();
					a.put(key,numPasajeros);
				} else {
					a.put(key,ocp.numPasajeros());
				}
			}
		}
		return a;
	}
	
	//5. Dado un destino devuelve el c�digo de la aerolinea del primer vuelo con plazas libres a ese
	// destino
	
	public String primerVuelo(String destino) {
		Vuelos ls = Vuelos.of();
		Vuelo a = null;
		for(Vuelo ocp:ls.todas()) {
			if(ocp.vueloProgramado().ciudadDestino().equals(destino) &&
			   ocp.vueloProgramado().numPlazas() > ocp.numPasajeros() &&	
			   ocp.fecha().isAfter(LocalDateTime.now())  &&
			   (a==null || ocp.fecha().isBefore(a.fecha()))) {
				   a = ocp;
			   }
		}
		if(a==null) throw new IllegalArgumentException("La lista est� vac�a");
		return a.vueloProgramado().codigoAerolinea();
	}

	//6. Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
	// destino la media de los precios de los vuelos a ese destino.
	
	private Double preM(List<Vuelo> ls){
		Double sum = 0.;
		Integer n = 0;
		for(Vuelo ocp:ls) {
			sum = sum + ocp.vueloProgramado().precio();
			n = n +1;
		}
		if(n==0) throw new IllegalArgumentException("El grupo est� vac�o");
		return sum/n;
	}
	
	public Map<String, Double> precioMedio(Integer n) {
		Vuelos ls = Vuelos.of();
		Map<String, List<Vuelo>> a = new HashMap<>();
		for(Vuelo ocp:ls.todas()) {
			if(ocp.vueloProgramado().numPlazas()-ocp.numPasajeros()==n) {
				String key = ocp.vueloProgramado().ciudadDestino();
				if(a.containsKey(key)) {
					a.get(key).add(ocp);
				} else {
					List<Vuelo> lsn = new ArrayList<>();
					lsn.add(ocp);
					a.put(key, lsn);
				}
			}
		}
		Map<String, Double> r = new HashMap<>();
		for(String key:a.keySet()) {
			r.put(key,this.preM(a.get(key)));
		}
		return r;
	}
	
	//7. Devuelve un Map tal que dado un entero n haga corresponder
	// a cada fecha la lista de los n destinos con los vuelos de mayor duraci�n.
	
	private static Comparator<Vuelo> cmp = 
			Comparator.comparing((Vuelo ocp) -> 
			ocp.vueloProgramado().duracion().getSeconds()).reversed();
	
	private List<String> mayorDuracion(List<Vuelo> ls,Integer n){
//		Stream<String> st = ls.stream()
//				.sorted(cmp)
//				.limit(n)
//				.map(ocp -> ocp.vuelo().ciudadDestino());
		List<Vuelo> ls2 = new ArrayList<>(ls);
		Collections.sort(ls2,cmp);
		List<Vuelo> ls3 = ls2.subList(0, n);
		List<String> ls4 = new ArrayList<>();
		for(Vuelo ocp:ls3) {
			ls4.add(ocp.vueloProgramado().ciudadDestino());
		}
		return	ls4;
	}
	
	public Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n) {
		Vuelos ls = Vuelos.of();
		Map<LocalDate, List<Vuelo>> a = new HashMap<>();
		for(Vuelo ocp:ls.todas()) {
				LocalDate key = ocp.fecha().toLocalDate();
				if(a.containsKey(key)) {
					a.get(key).add(ocp);
				} else {
					List<Vuelo> lsn = new ArrayList<>();
					lsn.add(ocp);
					a.put(key, lsn);
				}
		}
		Map<LocalDate, List<String>> r = new HashMap<>();
		for(LocalDate key:a.keySet()) {
			r.put(key,this.mayorDuracion(a.get(key),n));
		}
		return r;
	}
	
	
	//8. Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
	// a f. Si no hubiera vuelos devuelve 0.0
	
	public Double precioMedio(LocalDateTime f) {
		Vuelos ls = Vuelos.of();
		Double sum = 0.;
		Integer n = 0;
		for(Vuelo ocp: ls.todas()) {
			if(ocp.fecha().isAfter(f)) {
				Double precio = ocp.vueloProgramado().precio();
				sum = sum +precio;
				n= n+1;
			}
		}
		return n!=0?sum/n:0.0;
	}
	
	//9. Devuelve un Map que haga corresponder a cada destino un conjunto con las
	// fechas de los vuelos a ese destino.
	
	public Map<String, Set<LocalDate>> fechasADestino() {
		Vuelos ls = Vuelos.of();
		Map<String, Set<LocalDate>> a = new HashMap<>();
		for(Vuelo ocp: ls.todas()) {
			String key = ocp.vueloProgramado().ciudadDestino();
			LocalDate fecha = ocp.fechaSalida();
			if(a.containsKey(key)) {
				a.get(key).add(fecha);
			} else {
				Set<LocalDate> s = new HashSet<>();
				s.add(fecha);
				a.put(key, s);
			}
		}
		return a;
	}
	
	//10. Devuelve el destino con mayor n�mero de vuelos
	
	@Override
	public String destinoConMasVuelos() {
		Map<String,Integer> numVuelosDeDestino = new HashMap<>();
		for(VueloProgramado v:VuelosProgramados.of().todos()) {
			String key = v.codigoDestino();
			if(numVuelosDeDestino.containsKey(key)) {
				numVuelosDeDestino.put(key, numVuelosDeDestino.get(key)+1);
			} else {
				numVuelosDeDestino.put(key,1);
			}
		}
		String r = null;
		Integer n = null;
		for(String d:numVuelosDeDestino.keySet()) {
			if(n==null ||  numVuelosDeDestino.get(d) > n) {
				r = d;
				n = numVuelosDeDestino.get(d);
			}
		}
		return r;
	}
	
	//11. Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duraci�n es mayor que m minutos.

	@Override
	public SortedSet<Duration> duraciones(Integer m) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//12. Dado un n�mero n devuelve un conjunto con los destinos de los vuelos que est�n entre los n que m�s duraci�n tienen.
	
	@Override
	public Set<String> destinosMayorDuracion(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//13. Dado un n�mero n devuelve un conjunto con los n destinos con más vuelos
	
	@Override
	public Set<String> entreLosMasVuelos(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 14. Dado un número entero n devuelve una lista con los destinos que tienen m�s de n vuelos
	
	@Override
	public List<String> masDeNVuelos(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 15. Devuelve un Map que relaci�n cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	@Override
	public Map<String, Double> porcentajeADestino() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Double> porcentajeADestinoOcupacionesVuelos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de m�s barato
	
	@Override
	public Map<String, VueloProgramado> masBarato() {
		VuelosProgramados ls =VuelosProgramados.of();
		Map<String,VueloProgramado> res = new HashMap<String,VueloProgramado>();

		for(VueloProgramado v:ls.todos()) {
			String key = v.ciudadDestino();
			if(!res.containsKey(key)) {
				res.put(key, v);
			}
			else if(v.precio()<res.get(key).precio()) {
				res.replace(key, v);
			}
		}
		return res;
	}
	
	// 17. Devuelve un Map que haga corresponder a cada destino el n�mero de fechas
	// distintas en las que hay vuelos a ese destino.
	
	@Override
	public Map<String, Integer> fechasDistintas() {
		// TODO Auto-generated method stub
		return null;
	}

	// 18. Calcula el número de vuelos atrasados por año
	@Override
	public Map<Integer, Integer> vuelosAtrasados() {
		Vuelos ls = Vuelos.of();
		Map<Integer, Integer> a = new HashMap<>();
		for (Vuelo ocp : ls.todas()) {
			if (ocp.vueloProgramado().hora().isBefore(ocp.horaSalida())) {
				Integer key = ocp.fecha().getYear();
				if (a.containsKey(key)) {
					a.put(key, a.get(key) + 1);
				} else {
					a.put(key, 1);
				}
			}
		}
		return a;
	}

}
