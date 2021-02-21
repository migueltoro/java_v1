package us.lsi.aeropuerto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Ocupaciones {

	public static List<Ocupacion> ocupaciones;
	public static Map<String,Integer> ocupacionVuelo;


	//Dada una cadena de caracteres s devuelve el número total de pasajeros a ciudades destino que tienen 
	// como prefijo s (esto es, comienzan por s).
		
	public static Integer numeroDepasajeros(String prefix) {
			return Ocupaciones.ocupaciones.stream()
					.filter(ocp->ocp.vuelo().ciudadDestino().startsWith(prefix))
					.mapToInt(v->v.numPasajeros())
					.sum();	
	}
	
	// Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si existe un vuelo en la fecha f con destino en s.
	
		public Boolean  hayDestino(Set<String>  destinos, LocalDate f) {
			return Ocupaciones.ocupaciones.stream().filter(ocp->ocp.fecha().equals(f))
					.anyMatch(ocp->destinos.contains(ocp.vuelo().ciudadDestino()));
		}
		
		//Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos los vuelos de fecha f
		
		public Set<String> destinosDiferentes(LocalDate f) {
			return Ocupaciones.ocupaciones.stream()
					.filter(ocp->ocp.fecha().equals(f))
					.map(ocp->ocp.vuelo().ciudadDestino())
					.collect(Collectors.toSet());
		}
	
	//Dado una entero anyo devuelve un SortedMap que relacione cada destino con el total de pasajeros a ese destino en el año anyo
	
		public  SortedMap<String,Integer> totalPasajerosADestino(Integer a) {
			return Ocupaciones.ocupaciones.stream()
				.filter(ocp->ocp.fecha().getYear() == a)
				.collect(Collectors.groupingBy( 
						ocp->ocp.vuelo().ciudadDestino(), 
						()->new TreeMap<String,Integer>(Comparator.reverseOrder()), 
						Collectors.summingInt(ocp->ocp.numPasajeros())));	
		}
		
		//Dado un destino devuelve el código del primer vuelo con plazas libres a ese destino
		
		public String primerVuelo(String destino) {
			return Ocupaciones.ocupaciones.stream()
					.filter(ocp->ocp.vuelo().ciudadDestino().equals(destino) && ocp.vuelo().numPlazas() > ocp.numPasajeros())
					.filter(ocp->ocp.fecha().isAfter(LocalDate.now()))
					.min(Comparator.comparing(Ocupacion::fecha))
					.get()
					.vuelo()
					.codigo();
					
		}
		
		// Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad destino la media de los precios de los vuelos a ese destino.
		
		public Map<String,Double> precioMedio() {
			return Ocupaciones.ocupaciones.stream()
					.filter(ocp->ocp.numPasajeros().equals(ocp.vuelo().numPlazas()))
					.collect(Collectors.groupingBy(
							ocp->ocp.vuelo().ciudadDestino(),Collectors.averagingDouble(ocp->ocp.vuelo().precio())));
		}
		
		//Devuelve un Map tal que dado un entero n haga corresponder 
		//a cada fecha la lista de los n destinos con los vuelos de mayor duración.
		
		public Map<LocalDate,List<String>>  destinosConMayorDuracion(Integer n) {
			return Ocupaciones.ocupaciones.stream()
					.collect(Collectors.groupingBy(Ocupacion::fecha,
							Collectors.collectingAndThen(
									Collectors.toList(),
									g->g.stream().sorted(Comparator.comparing(ocp->ocp.vuelo().duracion()))
												 .map(ocp->ocp.vuelo().ciudadDestino())
												 .collect(Collectors.toList())
							))); 	
		}
		
		// Dada una fecha f devuelve el precio medio de los vuelos con salida posterior a f. Si no hubiera vuelos devuelve 0.0
		
		public Double precioMedio(LocalDate f) {
			return Ocupaciones.ocupaciones.stream()
					.filter(ocp->ocp.fecha().isAfter(f))
					.mapToDouble(ocp->ocp.vuelo().precio())
					.average()
					.orElse(0.0);
		}
		
		// Devuelve un Map que haga corresponder a cada destino un conjunto con las fechas de los vuelos a ese destino.
		
		public Map<String,Set<LocalDate>> fechasADestino() {
			return Ocupaciones.ocupaciones.stream()
					.collect(Collectors.groupingBy(ocp->ocp.vuelo().ciudadDestino(),Collectors.mapping(Ocupacion::fecha,Collectors.toSet())));		
		}
		
		// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas distintas en las que hay vuelos a ese destino.
		
		public Map<String,Integer> fechasDistintas() {
			return Ocupaciones.ocupaciones.stream()
					.collect(Collectors.groupingBy(ocp->ocp.vuelo().ciudadDestino(), 
							Collectors.mapping(Ocupacion::fecha,
									Collectors.collectingAndThen(Collectors.toSet(),s->s.size()))));
		}
		
		// Otra forma de resolver el anterior
		
		public Map<String,Integer> fechasDistintas2() {
			return Ocupaciones.ocupaciones.stream()
					.collect(Collectors.groupingBy(ocp->ocp.vuelo().ciudadDestino(), 
									Collectors.collectingAndThen(Collectors.toList(),
											g->(int) g.stream().map(Ocupacion::fecha).distinct().count()
									)
							 ));
		}

}
