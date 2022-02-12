package us.lsi.aeropuerto;

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

public class PreguntasFuncional implements Preguntas {

	PreguntasFuncional() {
		super();
	}


	//1. Dada una cadena de caracteres s devuelve el número total de pasajeros a
	// ciudades destino que tienen
	// como prefijo s (esto es, comienzan por s).

	public Integer numeroDepasajeros(String prefix) {
		IntStream st = OcupacionesVuelos.stream()
				.filter(ocp -> ocp.vuelo().ciudadDestino().startsWith(prefix))
				.mapToInt(v -> v.numPasajeros());
		return 	st.sum();
	}
		

	//2.  Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
	// existe un vuelo en la fecha f con destino en s.

	public Boolean hayDestino(Set<String> destinos, LocalDate f) {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
			.filter(ocp -> ocp.fecha().toLocalDate().equals(f));
		return st.anyMatch(ocp -> destinos.contains(ocp.vuelo().ciudadDestino()));
	}
		
		
	//3. Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
	// los vuelos de fecha f

	public Set<String> destinosDiferentes(LocalDate f) {
		Stream<String> st = OcupacionesVuelos.stream()
			.filter(ocp -> ocp.fecha().toLocalDate().equals(f))
			.map(ocp -> ocp.vuelo().ciudadDestino());
		return 	st.collect(Collectors.toSet());
	}
		
		
	//4. Dado un anyo devuelve un SortedMap que relacione cada destino con el
	// total de pasajeros a ese destino en el año anyo

	public SortedMap<String, Integer> totalPasajerosADestino(Integer a) {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
				.filter(ocp -> ocp.fecha().getYear() == a);
			
		return	st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					    () -> new TreeMap<String, Integer>(Comparator.reverseOrder()),
					    		Collectors.summingInt(ocp -> ocp.numPasajeros())));
	}

	//5. Dado un destino devuelve el código de la aerolinea del primer vuelo con plazas libres a ese
	// destino

	public String primerVuelo(String destino) {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
				.filter(ocp -> ocp.vuelo().ciudadDestino().equals(destino))
				.filter(ocp->ocp.vuelo().numPlazas() > ocp.numPasajeros())
				.filter(ocp -> ocp.fecha().isAfter(LocalDateTime.now()));
			
		return st.min(Comparator.comparing(OcupacionVuelo::fecha))
					.get()
					.vuelo()
					.codigoAerolinea();

	}

	//6. Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
	// destino la media de los precios de los vuelos a ese destino.
		
		private Double preM(List<OcupacionVuelo> ls){
			return ls.stream().mapToDouble(ocp->ocp.vuelo().precio()).average().getAsDouble();
		}

		public Map<String, Double> precioMedio() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.numPasajeros().equals(ocp.vuelo().numPlazas()));
			
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.collectingAndThen(Collectors.toList(),g->preM(g))));
		}
		

		//7. Devuelve un Map tal que dado un entero n haga corresponder
		// a cada fecha la lista de los n destinos con los vuelos de mayor duración.
		
		private static Comparator<OcupacionVuelo> cmp = 
				Comparator.comparing((OcupacionVuelo ocp) -> ocp.vuelo().duracion().getSeconds()).reversed();
		
		private List<String> mayorDuracion(List<OcupacionVuelo> ls,Integer n){
			Stream<String> st = ls.stream()
					.sorted(cmp)
					.limit(n)
					.map(ocp -> ocp.vuelo().ciudadDestino());
			
			return	st.toList();
		}

		public Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n) {
			Stream<OcupacionVuelo> st =  OcupacionesVuelos.stream();
			
			return st.collect(Collectors.groupingBy(oc -> oc.fecha().toLocalDate(),
					Collectors.collectingAndThen(Collectors.toList(),ls->mayorDuracion(ls,n))));
		}

		//8. Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
		// a f. Si no hubiera vuelos devuelve 0.0

		public Double precioMedio(LocalDateTime f) {
			DoubleStream st = OcupacionesVuelos.stream()
					.filter(ocp -> ocp.fecha().isAfter(f))
					.mapToDouble(ocp -> ocp.vuelo().precio());
			
			return	st.average()
					.orElse(0.0);
		}
		
		

		//9. Devuelve un Map que haga corresponder a cada destino un conjunto con las
		// fechas de los vuelos a ese destino.

		public Map<String, Set<LocalDate>> fechasADestino() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream();
			
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.mapping(OcupacionVuelo::fechaSalida,Collectors.toSet())));
		}
		
		
		
		//10. Devuelve el destino con mayor número de vuelos
		
		public String destinoConMasVuelos() {	
			Map<String,Integer> numVuelosDeDestino = Vuelos.stream()
					 .collect(Collectors.groupingBy(Vuelo::codigoDestino,
							  Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
			return 	numVuelosDeDestino.keySet().stream()
					.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
					.get();	
		}
		
		
		//11. Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
		
		public SortedSet<Duration> duraciones(Integer m) {
			Stream<Duration> st = Vuelos.stream()
					.map(Vuelo::duracion)
					.filter(d->d.getSeconds()/60. > m);

			return st.collect(Collectors.toCollection(()->new TreeSet<>()));
		}
	 
		
		//12. Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
		
		public Set<String> destinosMayorDuracion(Integer n) {
			Stream<String> st = Vuelos.stream()
					.sorted(Comparator.comparing(Vuelo::duracion).reversed())
					.limit(n)
					.map(Vuelo::codigoDestino);
			
			return st.collect(Collectors.toSet());
		}
		
		//13. Dado un número n devuelve un conjunto con los n destinos con más vuelos
		
		public Set<String> entreLosMasVuelos(Integer n) {			
			Map<String,Long> vuelosADestino = Vuelos.stream()
					.collect(Collectors.groupingBy(Vuelo::codigoDestino, Collectors.counting()));
			
			return vuelosADestino.keySet().stream()
					.sorted(Comparator.comparing(d->vuelosADestino.get(d)).reversed())
					.limit(n)
					.collect(Collectors.toSet());
		}
		
		
		
		// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
		
		public List<String> masDeNVuelos(Integer n) {
			Map<String,Long> vuelosADestino = Vuelos.stream()
					.collect(Collectors.groupingBy(Vuelo::codigoDestino,Collectors.counting()));
			
			return vuelosADestino.keySet().stream()
					.filter(d->vuelosADestino.get(d) > n)
					.collect(Collectors.toList());
		}
		
		
		// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
		
		public Map<String,Double>  porcentajeADestino() {
			Integer n = Vuelos.size();
			
			return Vuelos.stream().collect(Collectors.groupingBy(Vuelo::codigoDestino,
					Collectors.collectingAndThen(Collectors.toList(),g->(1.0*g.size())/n)));
		}
		
		public Map<String,Double>  porcentajeADestinoOcupacionesVuelos() {
			Integer n = OcupacionesVuelos.size();
			
			return OcupacionesVuelos.stream()
					.map(ocp->ocp.vuelo())
					.collect(Collectors.groupingBy(Vuelo::codigoDestino,
					    Collectors.collectingAndThen(Collectors.toList(),g->(1.0*g.size())/n)));
		}
		
		// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
		
		public Map<String,Vuelo> masBarato() {
			
			return Vuelos.stream().collect(Collectors.groupingBy(Vuelo::ciudadDestino,
					Collectors.collectingAndThen(
							Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(Vuelo::precio))),
							e->e.get())));
		}
		
		
		// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas
		// distintas en las que hay vuelos a ese destino.

		public Map<String, Integer> fechasDistintas() {
			Stream<OcupacionVuelo> st = OcupacionesVuelos.stream();
	
			return st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
					Collectors.mapping(OcupacionVuelo::fecha,
							Collectors.collectingAndThen(Collectors.toSet(),s->s.size()))));
		}

}
