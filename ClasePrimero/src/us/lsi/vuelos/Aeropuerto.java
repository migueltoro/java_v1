package us.lsi.vuelos;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aeropuerto {
	
	private static Collection<Vuelo> vuelos;
	
	// Dado un conjunto de destinos s y una fecha f devuelve cierto si existe un vuelo en la fecha f con destino en s.
	
	public static Boolean  hayDestino(Set<String>  destinos, LocalDate f  ) {
		return Aeropuerto.vuelos.stream().filter(v->v.getFecha().equals(f)).anyMatch(v->destinos.contains(v.getDestino()));
	}
	
	//Dada una fecha f devuelve el conjunto de destinos diferentes de todos los vuelos de fecha f
	
	public static Set<String> destinosDiferentes(LocalDate f) {
		return Aeropuerto.vuelos.stream().filter(v->v.getFecha().equals(f)).map(v->v.getDestino()).collect(Collectors.toSet());
	}
	
	//Dada una cadena de caracteres s devuelve el número total de pasajeros de los destinos que tienen 
	// como prefijo s (esto es, comienzan por s).
	
	public static Integer numeroDepasajeros(String prefix) {
		return Aeropuerto.vuelos.stream().filter(v->v.getDestino().startsWith(prefix)).mapToInt(v->v.getNumPasajeros()).sum();	
	}
	
	//Dado una entero anyo devuelve un SortedMap que relacione cada destino con el total de pasajeros a ese destino en el año anyo
	
	public static  SortedMap<String,Integer> totalPasajerosADestino(Integer a) {
		return Aeropuerto.vuelos.stream()
			.filter(v->v.getFecha().getYear() == a)
			.collect(Collectors.groupingBy( 
					Vuelo::getDestino, 
					()->new TreeMap<String,Integer>(Comparator.reverseOrder()), 
					Collectors.summingInt(Vuelo::getNumPasajeros)));	
	}
	
	//Dado un destino devuelve el código del primer vuelo con plazas libres a ese destino
	
	public static String  primerVuelo(String destino) {
		return Aeropuerto.vuelos.stream()
				.filter(v->v.getDestino().equals(destino) && v.getNumPlazas()>v.getNumPasajeros())
				.filter(v->v.getFecha().isAfter(LocalDate.now()))
				.min(Comparator.comparing(Vuelo::getFecha))
				.get()
				.getCodigo();
	}
	
	//Devuelve el destino con mayor número de vuelos
	
	public static String destinoConMasVuelos() {
		Map<String,Integer> numVuelosDeDestino = Aeropuerto.vuelos.stream()
				.collect(Collectors.groupingBy(
						Vuelo::getDestino,
						Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));					
		return 	numVuelosDeDestino.keySet().stream()
				.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
				.get();	
	}
	
	
	//Devuelve un Map tal que dado un entero n haga corresponder 
	//a cada fecha la lista de los n destinos con los vuelos de mayor duración.
	
	public static Map<LocalDate,List<String>>  destinosConMayorDuracion(Integer n) {
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::getFecha,
						Collectors.collectingAndThen(
								Collectors.toList(),
								g->g.stream().sorted(Comparator.comparing(Vuelo::getDuracion))
											 .map(Vuelo::getDestino)
											 .collect(Collectors.toList())
						))); 	
	}
	
	//Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
	
	public static SortedSet<Duration> duraciones(Integer m) {
		return vuelos.stream()
				.map(Vuelo::getDuracion)
				.filter(d->d.getSeconds()/60. > m)
				.collect(Collectors.toCollection(()->new TreeSet<>()));
				
	}
 
	
	// Dada una fecha f devuelve el precio medio de los vuelos con salida posterior a f. Si no hubiera vuelos devuelve 0.0
	
	public static Double precioMedio(LocalDate f) {
		return vuelos.stream()
				.filter(v->v.getFecha().isAfter(f))
				.mapToDouble(Vuelo::getPrecio)
				.average()
				.orElse(0.0);
	}
	
	// Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
	
	public static Set<String> destinosMayorDuracion(Integer n) {
		return vuelos.stream()
				.sorted(Comparator.comparing(Vuelo::getDuracion).reversed())
				.limit(n)
				.map(Vuelo::getDestino)
				.collect(Collectors.toSet());
	}
	
	// Devuelve para los vuelos completos un Map que haga corresponder a cada destino la media de los precios de los vuelos a ese destino.
	
	public static Map<String,Double> precioMedio() {
		return vuelos.stream()
				.filter(v->v.getNumPasajeros().equals(v.getNumPlazas()))
				.collect(Collectors.groupingBy(Vuelo::getDestino,Collectors.averagingDouble(Vuelo::getPrecio)));
	}
	
	// Devuelve un Map que haga corresponder a cada destino un conjunto con las fechas de los vuelos a ese destino.
	
	public static Map<String,Set<LocalDate>> fechasADestino() {
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::getDestino,Collectors.mapping(Vuelo::getFecha,Collectors.toSet())));		
	}
	
	//13. Dado un número n devuelve un conjunto con los destinos que están entre los n con más vuelos
	
	public static Set<String> entreLosMasVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = vuelos.stream().collect(Collectors.groupingBy(Vuelo::getDestino));
		return vuelosADestino.keySet().stream()
				.sorted(Comparator.comparing(d->vuelosADestino.get(d).size()))
				.limit(n)
				.collect(Collectors.toSet());
	}
	
	
	// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
	
	public static List<String> masDeNVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = vuelos.stream().collect(Collectors.groupingBy(Vuelo::getDestino));
		return vuelosADestino.keySet().stream()
				.filter(d->vuelosADestino.get(d).size() > n)
				.collect(Collectors.toList());
	}
	
	
	// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	public static Map<String,Double>  procentajeADestino() {
		Double n = (double) vuelos.stream().count();
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::getDestino,
						Collectors.collectingAndThen(Collectors.toList(),g->g.size()/n)));
		
	}
	
	// 16. Devuelve un Map que haga corresponder a cada destino el vuelo de más barato
	
	public static Map<String,Vuelo> masBarato() {
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::getDestino,
						Collectors.collectingAndThen(
								Collectors.minBy(Comparator.comparing(Vuelo::getPrecio)),
								op->op.get())));	
	}
	
	// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas distintas en las que hay vuelos a ese destino.
	
	public static Map<String,Integer> fechasDistintas() {
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::getDestino, 
						Collectors.mapping(Vuelo::getFecha,
								Collectors.collectingAndThen(Collectors.toSet(),s->s.size()))));
	}
	
	// Otra forma de resolver el anterior
	
	public static Map<String,Integer> fechasDistintas2() {
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::getDestino, 
								Collectors.collectingAndThen(Collectors.toList(),
										g->(int) g.stream().map(Vuelo::getFecha).distinct().count()
								)
						 ));
	}
	
}
