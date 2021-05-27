package us.lsi.aeropuerto;

import static us.lsi.tools.StreamTools.counting;
import static us.lsi.tools.StreamTools.toSet;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

public class Questions {
	
	// Dada una cadena de caracteres s devuelve el número total de pasajeros a
		// ciudades destino que tienen
		// como prefijo s (esto es, comienzan por s).

		

		public static Integer numeroDepasajeros(String prefix) {
			IntStream st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.vuelo().ciudadDestino().startsWith(prefix))
					.mapToInt(v -> v.numPasajeros());
			return 	st.sum();
		}

		// Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
		// existe un vuelo en la fecha f con destino en s.

		public static Boolean hayDestino(Set<String> destinos, LocalDate f) {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.fecha().toLocalDate().equals(f));
			return st.anyMatch(ocp -> destinos.contains(ocp.vuelo().ciudadDestino()));
		}

		// Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
		// los vuelos de fecha f

		public static Set<String> destinosDiferentes(LocalDate f) {
			Stream<String> st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.fecha().toLocalDate().equals(f))
					.map(ocp -> ocp.vuelo().ciudadDestino());
			return 	toSet(st);
		}

		// Dado un anyo devuelve un SortedMap que relacione cada destino con el
		// total de pasajeros a ese destino en el año anyo

		public static SortedMap<String, Integer> totalPasajerosADestino(Integer a) {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.fecha().getYear() == a);
			
			return	st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
							() -> new TreeMap<String, Integer>(Comparator.reverseOrder()),
							Collectors.summingInt(ocp -> ocp.numPasajeros())));
		}

		// Dado un destino devuelve el código del primer vuelo con plazas libres a ese
		// destino

		public static String primerVuelo(String destino) {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.vuelo().ciudadDestino().equals(destino))
				    .filter(ocp->ocp.vuelo().numPlazas() > ocp.numPasajeros())
					.filter(ocp -> ocp.fecha().isAfter(LocalDateTime.now()));
			
			return st.min(Comparator.comparing(OcupacionVuelo::fecha))
					.get()
					.vuelo()
					.codigoAerolinea();

		}

		// Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
		// destino la media de los precios de los vuelos a ese destino.
		
		private static Double preM(List<OcupacionVuelo> ls){
			return ls.stream().mapToDouble(ocp->ocp.vuelo().precio()).average().getAsDouble();
		}

		public static Map<String, Double> precioMedio() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.numPasajeros().equals(ocp.vuelo().numPlazas()));
			
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.collectingAndThen(Collectors.toList(),g->preM(g))));
		}

		// Devuelve un Map tal que dado un entero n haga corresponder
		// a cada fecha la lista de los n destinos con los vuelos de mayor duración.
		
		private static List<String> mayorDuracion(List<OcupacionVuelo> ls,Integer n){
			Stream<String> st = ls.stream()
					.sorted(Comparator.comparing(ocp -> ocp.vuelo().duracion()))
					.limit(n)
					.map(ocp -> ocp.vuelo().ciudadDestino());
			
			return	st.toList();
		}

		public static Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n) {
			Stream<OcupacionVuelo> st =  OcupacionesVuelos.datos().ocupaciones().stream();
			
			return st.collect(Collectors.groupingBy(oc -> oc.fecha().toLocalDate(),
					Collectors.collectingAndThen(Collectors.toList(),ls->mayorDuracion(ls,n))));
		}

		// Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
		// a f. Si no hubiera vuelos devuelve 0.0

		public static Double precioMedio(LocalDateTime f) {
			DoubleStream st = OcupacionesVuelos.datos().ocupaciones().stream()
					.filter(ocp -> ocp.fecha().isAfter(f))
					.mapToDouble(ocp -> ocp.vuelo().precio());
			
			return	st.average()
					.orElse(0.0);
		}

		// Devuelve un Map que haga corresponder a cada destino un conjunto con las
		// fechas de los vuelos a ese destino.

		public static Map<String, Set<LocalDate>> fechasADestino() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.datos().ocupaciones().stream();
			
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.mapping(OcupacionVuelo::fechaSalida,Collectors.toSet())));
		}


		
		//Devuelve el destino con mayor número de vuelos
		
		public static String destinoConMasVuelos() {	
			Map<String,Integer> numVuelosDeDestino = counting(Vuelos.datos().vuelos().stream(),Vuelo::codigoDestino);
			
			return 	numVuelosDeDestino.keySet().stream()
					.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
					.get();	
		}
		
		
		
		
		//Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
		
		public static SortedSet<Duration> duraciones(Integer m) {
			Stream<Duration> st = Vuelos.datos().vuelos().stream()
					.map(Vuelo::duracion)
					.filter(d->d.getSeconds()/60. > m);

			return st.collect(Collectors.toCollection(()->new TreeSet<>()));
		}
	 
		
		
		
		// Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
		
		public static Set<String> destinosMayorDuracion(Integer n) {
			Stream<String> st = Vuelos.datos().vuelos().stream()
					.sorted(Comparator.comparing(Vuelo::duracion).reversed())
					.limit(n)
					.map(Vuelo::codigoDestino);
			
			return st.collect(Collectors.toSet());
		}
		
		//13. Dado un número n devuelve un conjunto con los n destinos con más vuelos
		
		public static Set<String> entreLosMasVuelos(Integer n) {
			Map<String,List<Vuelo>> vuelosADestino = Vuelos.datos().vuelos().stream().collect(Collectors.groupingBy(Vuelo::codigoDestino));
			
			return vuelosADestino.keySet().stream()
					.sorted(Comparator.comparing(d->vuelosADestino.get(d).size()))
					.limit(n)
					.collect(Collectors.toSet());
		}
		
		
		// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
		
		public static List<String> masDeNVuelos(Integer n) {
			Map<String,List<Vuelo>> vuelosADestino = Vuelos.datos().vuelos().stream().collect(Collectors.groupingBy(Vuelo::codigoDestino));
			
			return vuelosADestino.keySet().stream()
					.filter(d->vuelosADestino.get(d).size() > n)
					.collect(Collectors.toList());
		}
		
		
		// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
		
		public static Map<String,Double>  procentajeADestino() {
			Integer n = Vuelos.datos().numVuelos();
			
			return Vuelos.datos().vuelos().stream().collect(Collectors.groupingBy(Vuelo::codigoDestino,
					Collectors.collectingAndThen(Collectors.toList(),g->(1.0*g.size())/n)));
		}
		
		// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
		
		public static Map<String,Vuelo> masBarato() {
			
			return Vuelos.datos().vuelos().stream().collect(Collectors.groupingBy(Vuelo::ciudadDestino,
					Collectors.collectingAndThen(
							Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(Vuelo::precio))),
							e->e.get())));
		}
		
		
		// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas
		// distintas en las que hay vuelos a ese destino.

		public static Map<String, Integer> fechasDistintas() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.datos().ocupaciones().stream();
	
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.mapping(OcupacionVuelo::fecha,
							Collectors.collectingAndThen(Collectors.toSet(),s->s.size()))));
		}

}
