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
import us.lsi.tools.Preconditions;

import static us.lsi.tools.StreamTools.*;

public class Vuelos {
	
	private static Vuelos datos;
	
	public static Vuelos datos() {
		return datos;
	}

	public static void random(Integer numVuelos) {
		List<Vuelo> vuelos = toList(IntStream.range(0,numVuelos).boxed().map(e->Vuelo.random()));
		Vuelos.datos = new Vuelos(vuelos);
	}

	public static void leeFicheroVuelos(String fichero) {
		List<Vuelo>  vuelos = FileTools.streamFromFile(fichero)
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toList());
		Vuelos.datos = new Vuelos(vuelos);
	}
	
	private List<Vuelo> vuelos;
	private Map<String, Vuelo> codigosVuelos;
	private Integer numVuelos;
	
	private Vuelos(List<Vuelo> vuelos) {
		super();
		this.vuelos = vuelos;
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
		this.numVuelos = this.codigosVuelos.size();
	}

	public List<Vuelo> vuelos() {
		return vuelos;
	}

	public Map<String, Vuelo> codigosVuelos() {
		return codigosVuelos;
	}

	public Integer numVuelos() {
		return numVuelos;
	}

	public void addVuelo(Vuelo v) {
		Preconditions.checkArgument(!this.codigosVuelos.containsKey(v.codigoAerolinea()),
				String.format("La aerolina %s ya existe",v.toString()));
		this.vuelos.add(v);
		this.codigosVuelos.put(v.codigoAerolinea(),v);
		this.numVuelos +=1;
	}
	
	public void removeVuelo(Vuelo v) {
		this.vuelos.remove(v);
	}
	
	//Devuelve el destino con mayor número de vuelos
	
	public static String destinoConMasVuelos() {	
		Map<String,Integer> numVuelosDeDestino = counting(Vuelos.datos.vuelos().stream(),Vuelo::codigoDestino);
		
		return 	numVuelosDeDestino.keySet().stream()
				.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
				.get();	
	}
	
	
	
	
	//Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
	
	public static SortedSet<Duration> duraciones(Integer m) {
		Stream<Duration> st = Vuelos.datos.vuelos().stream()
				.map(Vuelo::duracion)
				.filter(d->d.getSeconds()/60. > m);

		return toSortedSet(st);
	}
 
	
	
	
	// Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
	
	public static Set<String> destinosMayorDuracion(Integer n) {
		Stream<String> st = Vuelos.datos.vuelos().stream()
				.sorted(Comparator.comparing(Vuelo::duracion).reversed())
				.limit(n)
				.map(Vuelo::codigoDestino);
		
		return toSet(st);
	}
	
	//13. Dado un número n devuelve un conjunto con los n destinos con más vuelos
	
	public static Set<String> entreLosMasVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = Vuelos.datos.vuelos().stream().collect(Collectors.groupingBy(Vuelo::codigoDestino));
		
		return vuelosADestino.keySet().stream()
				.sorted(Comparator.comparing(d->vuelosADestino.get(d).size()))
				.limit(n)
				.collect(Collectors.toSet());
	}
	
	
	// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
	
	public static List<String> masDeNVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = Vuelos.datos.vuelos().stream().collect(Collectors.groupingBy(Vuelo::codigoDestino));
		return vuelosADestino.keySet().stream()
				.filter(d->vuelosADestino.get(d).size() > n)
				.collect(Collectors.toList());
	}
	
	
	// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	public static Map<String,Double>  procentajeADestino() {
		Integer n = Vuelos.datos.numVuelos();
		
		return groupingListAndThen(Vuelos.datos.vuelos().stream(),Vuelo::codigoDestino,g->(1.0*g.size())/n);
	}
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
	
	public static Map<String,Vuelo> masBarato() {
		
		return groupingReduce(Vuelos.datos.vuelos().stream(),Vuelo::ciudadDestino,
				BinaryOperator.minBy(Comparator.comparing(Vuelo::precio)));
	}
	
	public static void main(String[] args) {
		Aeropuertos.leeAeropuertos("ficheros/aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/aerolineas.csv");
		Vuelos.leeFicheroVuelos("ficheros/vuelos.csv");
	}
	
}
