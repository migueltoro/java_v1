package us.lsi.aeropuerto;

import static us.lsi.tools.StreamTools.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class OcupacionesVuelos {

	public static List<OcupacionVuelo> ocupaciones;
	private static Random rnd = new Random(System.nanoTime());

	public static void random(Integer numOcupaciones, Integer anyo) {
		Integer n = Vuelos.numVuelos;
		OcupacionesVuelos.ocupaciones = toList(
				IntStream.range(0, numOcupaciones).boxed()
				.map(e -> OcupacionVuelo.random(Vuelos.vuelos.get(rnd.nextInt(n)), anyo)));
	}

	public static void leeFicheroOcupaciones(String fichero) {
		OcupacionesVuelos.ocupaciones = FileTools.streamFromFile(fichero)
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toList());
	}

	// Dada una cadena de caracteres s devuelve el número total de pasajeros a
	// ciudades destino que tienen
	// como prefijo s (esto es, comienzan por s).

	public static Integer numeroDepasajeros(String prefix) {
		IntStream st = OcupacionesVuelos.ocupaciones.stream()
				.filter(ocp -> ocp.vuelo().ciudadDestino().startsWith(prefix))
				.mapToInt(v -> v.numPasajeros());
		return 	st.sum();
	}

	// Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
	// existe un vuelo en la fecha f con destino en s.

	public static Boolean hayDestino(Set<String> destinos, LocalDate f) {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.ocupaciones.stream()
				.filter(ocp -> ocp.fecha().toLocalDate().equals(f));
		return st.anyMatch(ocp -> destinos.contains(ocp.vuelo().ciudadDestino()));
	}

	// Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
	// los vuelos de fecha f

	public static Set<String> destinosDiferentes(LocalDate f) {
		Stream<String> st = OcupacionesVuelos.ocupaciones.stream()
				.filter(ocp -> ocp.fecha().toLocalDate().equals(f))
				.map(ocp -> ocp.vuelo().ciudadDestino());
		return 	toSet(st);
	}

	// Dado un anyo devuelve un SortedMap que relacione cada destino con el
	// total de pasajeros a ese destino en el año anyo

	public static SortedMap<String, Integer> totalPasajerosADestino(Integer a) {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.ocupaciones.stream().filter(ocp -> ocp.fecha().getYear() == a);
		
//		return	st.collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadDestino(),
//						() -> new TreeMap<String, Integer>(Comparator.reverseOrder()),
//						Collectors.summingInt(ocp -> ocp.numPasajeros())));
		
		return groupingReduceSorted(
				st,    //stream
				(OcupacionVuelo ocp) -> ocp.vuelo().ciudadDestino(),  //key
				Comparator.reverseOrder(),  //order
				ocp -> ocp.numPasajeros(),  //value
				(x,y)->x+y,  //binary operator
				0);  //initial
	}

	// Dado un destino devuelve el código del primer vuelo con plazas libres a ese
	// destino

	public static String primerVuelo(String destino) {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.ocupaciones.stream()
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
		Stream<OcupacionVuelo> st = OcupacionesVuelos.ocupaciones.stream()
				.filter(ocp -> ocp.numPasajeros().equals(ocp.vuelo().numPlazas()));
		return groupingListAndThen(
				st,
				ocp -> ocp.vuelo().ciudadDestino(),
				g->preM(g));
	}

	// Devuelve un Map tal que dado un entero n haga corresponder
	// a cada fecha la lista de los n destinos con los vuelos de mayor duración.
	
	private static List<String> mayorDuracion(List<OcupacionVuelo> ls,Integer n){
		Stream<String> st = ls.stream()
				.sorted(Comparator.comparing(ocp -> ocp.vuelo().duracion()))
				.limit(n)
				.map(ocp -> ocp.vuelo().ciudadDestino());
		
		return	toList(st);
	}

	public static Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n) {
		Stream<OcupacionVuelo> st =  OcupacionesVuelos.ocupaciones.stream();
		
		return groupingListAndThen(
				st,
				oc -> oc.fecha().toLocalDate(),
				ls->mayorDuracion(ls,n));
	}

	// Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
	// a f. Si no hubiera vuelos devuelve 0.0

	public static Double precioMedio(LocalDateTime f) {
		DoubleStream st = OcupacionesVuelos.ocupaciones.stream()
				.filter(ocp -> ocp.fecha().isAfter(f))
				.mapToDouble(ocp -> ocp.vuelo().precio());
		
		return	st.average()
				.orElse(0.0);
	}

	// Devuelve un Map que haga corresponder a cada destino un conjunto con las
	// fechas de los vuelos a ese destino.

	public static Map<String, Set<LocalDate>> fechasADestino() {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.ocupaciones.stream();
		return groupingSet(
				st,
				ocp -> ocp.vuelo().ciudadDestino(),
				OcupacionVuelo::fechaSalida);
	}

	// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas
	// distintas en las que hay vuelos a ese destino.

	public static Map<String, Integer> fechasDistintas() {
		Stream<OcupacionVuelo> st = OcupacionesVuelos.ocupaciones.stream();

		return groupingSetAndThen(
				st,
				ocp -> ocp.vuelo().ciudadDestino(),
				OcupacionVuelo::fecha,
				s->s.size());
	}

}
