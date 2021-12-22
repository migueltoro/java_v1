package us.lsi.aeropuerto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Preguntas {
	
	//1. Dada una cadena de caracteres s devuelve el número total de pasajeros a
		// ciudades destino que tienen
		// como prefijo s (esto es, comienzan por s).

		

		public static Integer numeroDepasajeros(String prefix) {
			IntStream st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.vuelo().ciudadDestino().startsWith(prefix))
					.mapToInt(v -> v.numPasajeros());
			return 	st.sum();
		}
		
		public static Integer numeroDepasajeros2(String prefix) {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			Integer sum = 0;
			for(OcupacionVuelo ocp:ls) {
				if(ocp.vuelo().ciudadDestino().startsWith(prefix)) {
					Integer numPasajeros = ocp.numPasajeros();
					sum = sum + numPasajeros;
				}
			}
			return sum;
		}
		
		

		//2.  Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
		// existe un vuelo en la fecha f con destino en s.

		public static Boolean hayDestino(Set<String> destinos, LocalDate f) {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.fecha().toLocalDate().equals(f));
			return st.anyMatch(ocp -> destinos.contains(ocp.vuelo().ciudadDestino()));
		}
		
		public static Boolean hayDestino2(Set<String> destinos, LocalDate f) {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			Boolean a = false;
			for(OcupacionVuelo ocp:ls) {
				if(ocp.fecha().toLocalDate().equals(f)) {
					if(destinos.contains(ocp.vuelo().ciudadDestino())) {
						a = true;
						break;
					}
				}
			}
			return a;
		}

		//3. Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
		// los vuelos de fecha f

		public static Set<String> destinosDiferentes(LocalDate f) {
			Stream<String> st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.fecha().toLocalDate().equals(f))
					.map(ocp -> ocp.vuelo().ciudadDestino());
			return 	st.collect(Collectors.toSet());
		}
		
		public static Set<String> destinosDiferentes2(LocalDate f) {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			Set<String> a = new HashSet<>();
			for(OcupacionVuelo ocp:ls) {
				if(ocp.fecha().toLocalDate().equals(f)) {
					String ciudadDestino = ocp.vuelo().ciudadDestino();
					a.add(ciudadDestino);			
				}
			}
			return a;
		}

		//4. Dado un anyo devuelve un SortedMap que relacione cada destino con el
		// total de pasajeros a ese destino en el año anyo

		public static SortedMap<String, Integer> totalPasajerosADestino(Integer a) {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.fecha().getYear() == a);
			
			return	st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
							() -> new TreeMap<String, Integer>(Comparator.reverseOrder()),
							Collectors.summingInt(ocp -> ocp.numPasajeros())));
		}
		
		public static SortedMap<String, Integer> totalPasajerosADestino2(Integer any) {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			SortedMap<String,Integer> a = new TreeMap<String, Integer>(Comparator.reverseOrder());
			for(OcupacionVuelo ocp:ls) {
				if(ocp.fecha().getYear() == any) {
					String key = ocp.vuelo().ciudadDestino();
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

		//5. Dado un destino devuelve el código de la aerolinea del primer vuelo con plazas libres a ese
		// destino

		public static String primerVuelo(String destino) {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.vuelo().ciudadDestino().equals(destino))
				    .filter(ocp->ocp.vuelo().numPlazas() > ocp.numPasajeros())
					.filter(ocp -> ocp.fecha().isAfter(LocalDateTime.now()));
			
			return st.min(Comparator.comparing(OcupacionVuelo::fecha))
					.get()
					.vuelo()
					.codigoAerolinea();

		}
		
		public static String primerVuelo2(String destino) {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			OcupacionVuelo a = null;
			for(OcupacionVuelo ocp:ls) {
				if(ocp.vuelo().ciudadDestino().equals(destino) &&
				   ocp.vuelo().numPlazas() > ocp.numPasajeros() &&	
				   ocp.fecha().isAfter(LocalDateTime.now())) {
				   if(a==null || ocp.fecha().isBefore(a.fecha())) {
					   a = ocp;
				   }
				}
			}
			if(a==null) throw new IllegalArgumentException("La lista está vacía");
			return a.vuelo().codigoAerolinea();
		}

		//6. Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
		// destino la media de los precios de los vuelos a ese destino.
		
		private static Double preM(List<OcupacionVuelo> ls){
			return ls.stream().mapToDouble(ocp->ocp.vuelo().precio()).average().getAsDouble();
		}

		public static Map<String, Double> precioMedio() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.numPasajeros().equals(ocp.vuelo().numPlazas()));
			
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.collectingAndThen(Collectors.toList(),g->preM(g))));
		}
		
		
		private static Double preM2(List<OcupacionVuelo> ls){
			Double sum = 0.;
			Integer n = 0;
			for(OcupacionVuelo ocp:ls) {
				sum = sum + ocp.vuelo().precio();
				n = n +1;
			}
			if(n==0) throw new IllegalArgumentException("El grupo está vacío");
			return sum/n;
		}
		
		public static Map<String, Double> precioMedio2() {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			Map<String, List<OcupacionVuelo>> a = new HashMap<>();
			for(OcupacionVuelo ocp:ls) {
				if(ocp.numPasajeros().equals(ocp.vuelo().numPlazas())) {
					String key = ocp.vuelo().ciudadDestino();
					if(a.containsKey(key)) {
						a.get(key).add(ocp);
					} else {
						List<OcupacionVuelo> lsn = new ArrayList<>();
						lsn.add(ocp);
						a.put(key, lsn);
					}
				}
			}
			Map<String, Double> r = new HashMap<>();
			for(String key:a.keySet()) {
				r.put(key,Preguntas.preM2(a.get(key)));
			}
			return r;
		}
		

		//7. Devuelve un Map tal que dado un entero n haga corresponder
		// a cada fecha la lista de los n destinos con los vuelos de mayor duración.
		
		private static Comparator<OcupacionVuelo> cmp = 
				Comparator.comparing((OcupacionVuelo ocp) -> ocp.vuelo().duracion().getSeconds()).reversed();
		
		private static List<String> mayorDuracion(List<OcupacionVuelo> ls,Integer n){
			Stream<String> st = ls.stream()
					.sorted(cmp)
					.limit(n)
					.map(ocp -> ocp.vuelo().ciudadDestino());
			
			return	st.toList();
		}

		public static Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n) {
			Stream<OcupacionVuelo> st =  OcupacionesVuelos.stream();
			
			return st.collect(Collectors.groupingBy(oc -> oc.fecha().toLocalDate(),
					Collectors.collectingAndThen(Collectors.toList(),ls->mayorDuracion(ls,n))));
		}

		//8. Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
		// a f. Si no hubiera vuelos devuelve 0.0

		public static Double precioMedio(LocalDateTime f) {
			DoubleStream st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.fecha().isAfter(f))
					.mapToDouble(ocp -> ocp.vuelo().precio());
			
			return	st.average()
					.orElse(0.0);
		}
		
		public static Double precioMedio2(LocalDateTime f) {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			Double sum = 0.;
			Integer n = 0;
			for(OcupacionVuelo ocp: ls) {
				if(ocp.fecha().isAfter(f)) {
					Double precio = ocp.vuelo().precio();
					sum = sum +precio;
					n= n+1;
				}
			}
			return n==0?sum/n:0.0;
		}

		//9. Devuelve un Map que haga corresponder a cada destino un conjunto con las
		// fechas de los vuelos a ese destino.

		public static Map<String, Set<LocalDate>> fechasADestino() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream();
			
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.mapping(OcupacionVuelo::fechaSalida,Collectors.toSet())));
		}
		
		public static Map<String, Set<LocalDate>> fechasADestino2() {
			List<OcupacionVuelo> ls = OcupacionesVuelos.stream().toList();
			Map<String, Set<LocalDate>> a = new HashMap<>();
			for(OcupacionVuelo ocp: ls) {
				String key = ocp.vuelo().ciudadDestino();
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

		
		//10. Devuelve el destino con mayor número de vuelos
		
		public static String destinoConMasVuelos() {	
			Map<String,Integer> numVuelosDeDestino = Vuelos.stream()
					 .collect(Collectors.groupingBy(Vuelo::codigoDestino,
							  Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
			return 	numVuelosDeDestino.keySet().stream()
					.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
					.get();	
		}
		
		
		
		
		//11. Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
		
		public static SortedSet<Duration> duraciones(Integer m) {
			Stream<Duration> st = Vuelos.stream()
					.map(Vuelo::duracion)
					.filter(d->d.getSeconds()/60. > m);

			return st.collect(Collectors.toCollection(()->new TreeSet<>()));
		}
	 
		
		
		
		//12. Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
		
		public static Set<String> destinosMayorDuracion(Integer n) {
			Stream<String> st = Vuelos.stream()
					.sorted(Comparator.comparing(Vuelo::duracion).reversed())
					.limit(n)
					.map(Vuelo::codigoDestino);
			
			return st.collect(Collectors.toSet());
		}
		
		//13. Dado un número n devuelve un conjunto con los n destinos con más vuelos
		
		public static Set<String> entreLosMasVuelos(Integer n) {			
			Map<String,Long> vuelosADestino = Vuelos.stream()
					.collect(Collectors.groupingBy(Vuelo::codigoDestino, Collectors.counting()));
			
			return vuelosADestino.keySet().stream()
					.sorted(Comparator.comparing(d->vuelosADestino.get(d)).reversed())
					.limit(n)
					.collect(Collectors.toSet());
		}
		
		
		
		// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
		
		public static List<String> masDeNVuelos(Integer n) {
			Map<String,Long> vuelosADestino = Vuelos.stream()
					.collect(Collectors.groupingBy(Vuelo::codigoDestino,Collectors.counting()));
			
			return vuelosADestino.keySet().stream()
					.filter(d->vuelosADestino.get(d) > n)
					.collect(Collectors.toList());
		}
		
		
		// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
		
		public static Map<String,Double>  porcentajeADestino() {
			Integer n = Vuelos.size();
			
			return Vuelos.stream().collect(Collectors.groupingBy(Vuelo::codigoDestino,
					Collectors.collectingAndThen(Collectors.toList(),g->(1.0*g.size())/n)));
		}
		
		public static Map<String,Double>  porcentajeADestinoOcupacionesVuelos() {
			Integer n = OcupacionesVuelos.size();
			
			return OcupacionesVuelos.stream()
					.map(ocp->ocp.vuelo())
					.collect(Collectors.groupingBy(Vuelo::codigoDestino,
					    Collectors.collectingAndThen(Collectors.toList(),g->(1.0*g.size())/n)));
		}
		
		// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
		
		public static Map<String,Vuelo> masBarato() {
			
			return Vuelos.stream().collect(Collectors.groupingBy(Vuelo::ciudadDestino,
					Collectors.collectingAndThen(
							Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(Vuelo::precio))),
							e->e.get())));
		}
		
		
		// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas
		// distintas en las que hay vuelos a ese destino.

		public static Map<String, Integer> fechasDistintas() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream();
	
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.mapping(OcupacionVuelo::fecha,
							Collectors.collectingAndThen(Collectors.toSet(),s->s.size()))));
		}

}
