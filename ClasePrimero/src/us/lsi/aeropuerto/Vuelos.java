package us.lsi.aeropuerto;

import java.time.Duration;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BinaryOperator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

import static us.lsi.tools.StreamTools.*;

public class Vuelos {
	
	public static List<Vuelo> vuelos;
	public static Map<String, Vuelo> datosVuelos;
	public static Integer numVuelos;
	
	public static void random(Integer numVuelos) {
		Vuelos.vuelos = toList(IntStream.range(0,numVuelos).boxed().map(e->Vuelo.random()));
		Vuelos.datosVuelos = Vuelos.vuelos.stream().collect(Collectors.toMap(Vuelo::codigoVuelo,x->x));
		Vuelos.numVuelos = Vuelos.datosVuelos.size();
	}

	public static void leeFicheroVuelos(String fichero) {
		Vuelos.vuelos = FileTools.streamFromFile(fichero)
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toList());
		Vuelos.datosVuelos = Vuelos.vuelos.stream().collect(Collectors.toMap(Vuelo::codigoVuelo,x->x));
		Vuelos.numVuelos = Vuelos.datosVuelos.size();
	}
	
	public static void addVuelo(Vuelo v) {
		Vuelos.vuelos.add(v);
	}
	
	public static void removeVuelo(Vuelo v) {
		Vuelos.vuelos.remove(v);
	}
	
	//Devuelve el destino con mayor número de vuelos
	
	public static String destinoConMasVuelos() {	
		Map<String,Integer> numVuelosDeDestino = counting(Vuelos.vuelos.stream(),Vuelo::codeDestino);
		
		return 	numVuelosDeDestino.keySet().stream()
				.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
				.get();	
	}
	
	
	
	
	//Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
	
	public static SortedSet<Duration> duraciones(Integer m) {
		Stream<Duration> st = Vuelos.vuelos.stream()
				.map(Vuelo::duracion)
				.filter(d->d.getSeconds()/60. > m);

		return toSortedSet(st);
	}
 
	
	
	
	// Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
	
	public static Set<String> destinosMayorDuracion(Integer n) {
		Stream<String> st = Vuelos.vuelos.stream()
				.sorted(Comparator.comparing(Vuelo::duracion).reversed())
				.limit(n)
				.map(Vuelo::codeDestino);
		
		return toSet(st);
	}
	
	//13. Dado un número n devuelve un conjunto con los n destinos con más vuelos
	
	public static Set<String> entreLosMasVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = Vuelos.vuelos.stream().collect(Collectors.groupingBy(Vuelo::codeDestino));
		
		return vuelosADestino.keySet().stream()
				.sorted(Comparator.comparing(d->vuelosADestino.get(d).size()))
				.limit(n)
				.collect(Collectors.toSet());
	}
	
	
	// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
	
	public static List<String> masDeNVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = vuelos.stream().collect(Collectors.groupingBy(Vuelo::codeDestino));
		return vuelosADestino.keySet().stream()
				.filter(d->vuelosADestino.get(d).size() > n)
				.collect(Collectors.toList());
	}
	
	
	// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	public static Map<String,Double>  procentajeADestino() {
		Integer n = Vuelos.numVuelos;
		
		return groupingListAndThen(Vuelos.vuelos.stream(),Vuelo::codeDestino,g->(1.0*g.size())/n);
	}
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
	
	public static Map<String,Vuelo> masBarato() {
		
		return groupingReduce(Vuelos.vuelos.stream(),Vuelo::ciudadDestino,
				BinaryOperator.minBy(Comparator.comparing(Vuelo::precio)));
	}
	
	
}
