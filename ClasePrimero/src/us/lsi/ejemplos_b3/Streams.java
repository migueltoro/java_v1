package us.lsi.ejemplos_b3;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import us.lsi.ejemplos_b1.Persona;

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
