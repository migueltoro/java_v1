package us.lsi.ejemplos_b3;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.ejemplos_b1_tipos.Persona;
import us.lsi.tools.Map2;
import us.lsi.tools.Stream2;

public class Streams {
	
	public static record Vuelo(
			String codigoAerolinea,
			String numero,
			String codigoDestino,
			String codigoOrigen,
			Double precio,
			Integer numPlazas,	
			Duration duracion,
			LocalTime hora,
			DayOfWeek diaSemana,
			List<Persona> pasajeros
			) {
		
	}
	
	public static List<Vuelo> ordena(List<Vuelo> ls) {
		return ls.stream()
				.sorted(Comparator.comparing(Vuelo::duracion))
				.toList();
	}
	
	public static Integer numPlazas(List<Vuelo> ls, DayOfWeek diaSemana) {
		return ls.stream()
		.filter(x -> x.diaSemana().equals(diaSemana))
		.mapToInt(x -> x.numPlazas())
		.sum();
	}
	
	public static Boolean hayAlgunoMayorDe(List<Vuelo> ls, String destino, Integer edad) {
		return ls.stream()
				.filter(x -> x.codigoDestino().equals(destino))
				.flatMap(x -> x.pasajeros().stream())
				.anyMatch(p -> p.edad() > 40);
	}
	
	public static Vuelo vueloDestinoConMenosPasajeros(List<Vuelo> ls,String destino) {
		return ls.stream()
		.filter(x -> x.codigoDestino().equals(destino))
		.min(Comparator.comparing(v->v.pasajeros().size()))
		.get();
	}
	
	public static Map<String, Vuelo> masBaratoADestino(List<Vuelo> ls) {
		return ls.stream().collect(Collectors.groupingBy(Vuelo::codigoDestino, 
				Collectors.collectingAndThen(
						Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(Vuelo::precio))), 
								e -> e.get())));
	}
	
	public static Map<String, Vuelo> masBaratoADestino_2(List<Vuelo> ls) {
		Stream<Vuelo> st = ls.stream();
	
		return Stream2.groupingReduce(st,Vuelo::codigoDestino,
				BinaryOperator.minBy(Comparator.comparing(Vuelo::precio)));
	
	}
	
	public static Double preM(List<Vuelo> ls) {
		return ls.stream()
				.mapToDouble(v -> v.precio())
				.average()
				.getAsDouble();
	}

	public static Map<String, Double> precioMedio(List<Vuelo> ls) {
		return ls.stream().filter(v -> v.pasajeros().size() == v.numPlazas())
				.collect(Collectors.groupingBy(v -> v.codigoDestino(), 
						Collectors.collectingAndThen(Collectors.toList(), g -> preM(g))));
	}
	
	public static Map<String, Double> precioMedio_2(List<Vuelo> ls) {
		Stream<Vuelo> st = ls.stream()
				.filter(v -> v.pasajeros().size() == v.numPlazas());
		
		Map<String, List<Vuelo>> g = Stream2.groupingList(st, v -> v.codigoDestino());
		
		Map<String, Double> r = new HashMap<>();
		for(Entry<String, List<Vuelo>> e: g.entrySet()) {
			r.put(e.getKey(),preM(e.getValue()));
		}	
		return r;				
				
	} 
	
	public static Map<String, Double> precioMedio_3(List<Vuelo> ls) {
		Stream<Vuelo> st = ls.stream()
				.filter(v -> v.pasajeros().size() == v.numPlazas());
		
		Map<String, List<Vuelo>> g = Stream2.groupingList(st, v -> v.codigoDestino());
		
		return g.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->preM(e.getValue())));
	} 
	
	public static Map<String, Double> precioMedio_4(List<Vuelo> ls) {
		Stream<Vuelo> st = ls.stream()
				.filter(v -> v.pasajeros().size() == v.numPlazas());
		
		Map<String, List<Vuelo>> g = Stream2.groupingList(st, v -> v.codigoDestino());
		
		return Map2.map(g,v->preM(v));
	} 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
