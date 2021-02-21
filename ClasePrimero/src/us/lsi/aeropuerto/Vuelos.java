package us.lsi.aeropuerto;

import java.time.Duration;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Vuelos {
	
	public static List<Vuelo> vuelos;
	public static Map<String,Vuelo> datosVuelos;
	
	public static void addVuelo(Vuelo v) {
		Vuelos.vuelos.add(v);
	}
	
	public void removeVuelo(Vuelo v) {
		Vuelos.vuelos.remove(v);
	}
	

	
	
	
	//Devuelve el destino con mayor número de vuelos
	
	public String destinoConMasVuelos() {
		Map<String,Integer> numVuelosDeDestino = Vuelos.vuelos.stream()
				.collect(Collectors.groupingBy(
						Vuelo::codeDestino,
						Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));					
		return 	numVuelosDeDestino.keySet().stream()
				.max(Comparator.comparing(d->numVuelosDeDestino.get(d)))
				.get();	
	}
	
	
	
	
	//Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
	
	public SortedSet<Duration> duraciones(Integer m) {
		return Vuelos.vuelos.stream()
				.map(Vuelo::duracion)
				.filter(d->d.getSeconds()/60. > m)
				.collect(Collectors.toCollection(()->new TreeSet<>()));
				
	}
 
	
	
	
	// Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
	
	public Set<String> destinosMayorDuracion(Integer n) {
		return Vuelos.vuelos.stream()
				.sorted(Comparator.comparing(Vuelo::duracion).reversed())
				.limit(n)
				.map(Vuelo::codeDestino)
				.collect(Collectors.toSet());
	}
	
	//13. Dado un número n devuelve un conjunto con los destinos que están entre los n con más vuelos
	
	public Set<String> entreLosMasVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = Vuelos.vuelos.stream().collect(Collectors.groupingBy(Vuelo::codeDestino));
		return vuelosADestino.keySet().stream()
				.sorted(Comparator.comparing(d->vuelosADestino.get(d).size()))
				.limit(n)
				.collect(Collectors.toSet());
	}
	
	
	// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
	
	public List<String> masDeNVuelos(Integer n) {
		Map<String,List<Vuelo>> vuelosADestino = vuelos.stream().collect(Collectors.groupingBy(Vuelo::codeDestino));
		return vuelosADestino.keySet().stream()
				.filter(d->vuelosADestino.get(d).size() > n)
				.collect(Collectors.toList());
	}
	
	
	// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	public Map<String,Double>  procentajeADestino() {
		Double n = (double) Vuelos.vuelos.stream().count();
		return vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::codeDestino,
						Collectors.collectingAndThen(Collectors.toList(),g->g.size()/n)));
		
	}
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
	
	public Map<String,Vuelo> masBarato() {
		return Vuelos.vuelos.stream()
				.collect(Collectors.groupingBy(Vuelo::ciudadDestino,
						Collectors.collectingAndThen(
								Collectors.minBy(Comparator.comparing(Vuelo::precio)),
								op->op.get())));	
	}
	
	
	
	
	
}
