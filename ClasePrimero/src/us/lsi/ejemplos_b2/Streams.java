package us.lsi.ejemplos_b2;

import java.util.List;
import java.util.Locale;

public class Streams {
	
	public static void ejemplo1() {
		List<Double> temperaturas = List.of(4.5,-3.4,5.6,-56.,56.,89.5);
		Long bajoCero = 0L;
		for (Double t : temperaturas) {
			if (t < 0) {
				bajoCero++;
			}
		}
		System.out.println(String.format("Hay %d temperaturas bajo cero", bajoCero));
		bajoCero = temperaturas.stream().filter(x -> x < 0) .count(); 
		System.out.println(String.format("Hay %d temperaturas bajo cero", bajoCero));
	}
	
	public static void ejemplo2() {
		List<Double> temperaturas = List.of(4.5,-3.4,5.6,-56.,56.,89.5);
		Double s = 0.;
		for (Double t : temperaturas) {
			if (t > 0) {
				s = s+ t*t;
			}
		}
		System.out.println(String.format("La suma de los cuadrados las positivas es %.2f", s));
		s = temperaturas.stream()
				.filter(x -> x > 0)
				.mapToDouble(x->x*x)
				.sum(); 
		System.out.println(String.format("La suma de los cuadrados de las positivas es %.2f", s));
	}
	
	public static void ejemplo3() {
		List<Double> temperaturas = List.of(4.5,-3.4,5.6,-56.,56.,89.5);
		Boolean algunaPorEncima = false;
		for (Double t : temperaturas) {
				algunaPorEncima = t>40;
				if(algunaPorEncima) break;
		}
		System.out.println(String.format("Hay alguna mayor de 40º %s",algunaPorEncima));
		algunaPorEncima = temperaturas.stream().anyMatch(x -> x > 40); 
		System.out.println(String.format("Hay alguna mayor de 40º %s",algunaPorEncima)); 
	}
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en","US"));
		ejemplo1();
		ejemplo2();
		ejemplo3();
	}

}
