package us.lsi.ejemplos_b2;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import us.lsi.tools.File2;

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
			if (t < 0) {
				s = s+ t*t*t;
			}
		}
		System.out.println(String.format("La suma de los cuadrados las positivas es %.2f", s));
		s = temperaturas.stream()
				.filter(x -> x < 0)
				.mapToDouble(x->x*x*x)
				.sum(); 
		System.out.println(String.format("La suma de los cuadrados de las positivas es %.2f", s));
	}
	
	public static void ejemplo4() {
		List<Double> temperaturas = List.of(4.5,-3.4,5.6,-56.,56.,89.5);
		Double s = 0.;
		Integer n = 0;
		for (Double t : temperaturas) {
			if (t > -10) {
				s = s+ t*t;
				n = n+1;
			}
		}
		Double m = s/n;
		System.out.println(String.format("La media de los cuadrados mayores que -10 es %.2f", m));
		m = temperaturas.stream()
				.filter(x -> x > -10)
				.mapToDouble(x->x*x)
				.average()
				.getAsDouble(); 
		System.out.println(String.format("La media de los cuadrados mayores que -10 es %.2f", m));
	}
	
	public static void ejemplo5() {
		List<String> temperaturas = File2.lineasDeFichero("ficheros/numeros.txt");
		Double s = 0.;
		Integer n = 0;
		for (String t : temperaturas) {
			Integer e = Integer.parseInt(t);
			if (e > 5) {
				s = s+ e*e;
				n = n+1;
			}
		}
		Double m = s/n;
		System.out.println(String.format("La media de los cuadrados mayores que 5 es %.2f", m));
		m = temperaturas.stream()
				.map(x->Integer.parseInt(x))
				.filter(x -> x > 5)
				.mapToDouble(x->x*x)
				.average()
				.getAsDouble(); 
		System.out.println(String.format("La media de los cuadrados mayores que 5 es %.2f", m));
	}
	
	public static void ejemplo6() {
		List<String> temperaturas = File2.lineasDeFichero("ficheros/numeros_2.txt");
		Double s = 0.;
		Integer n = 0;
		for (String linea : temperaturas) {
			for (String t : linea.split("[ ,]")) {
				if (!t.isEmpty()) {
					Integer e = Integer.parseInt(t);
					if (e > 5) {
						s = s + e * e;
						n = n + 1;
					}
				}
			}
		}
		Double m = s / n;
		System.out.println(String.format("La media de los cuadrados mayores que 5 es %.2f", m));
		m = temperaturas.stream()
				.flatMap(ln->Arrays.stream(ln.split("[ ,]")))
				.filter(x->!x.isEmpty())
				.map(x -> Integer.parseInt(x))			
				.filter(x -> x > 5)			
				.mapToDouble(x -> x * x)
				.average()
				.getAsDouble();
		System.out.println(String.format("La media de los cuadrados mayores que 5 es %.2f", m));
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
//		ejemplo1();
		ejemplo6();
//		ejemplo3();
	}

}
