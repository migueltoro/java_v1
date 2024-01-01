package us.lsi.ejemplos_b1;


import java.util.Locale;

import org.apache.commons.math3.complex.Complex;

import us.lsi.tools.Preconditions;

public class Funciones_2 {
	
	public static Double solEcuacionPrimerGrado(Double a, Double b) {
		Preconditions.checkArgument(a > 0, String.format("El coeficiente a debe ser distinto de cero y es %.2f", a));
		return -b / a;
	}

	public static Solucion2G solEcuacionSegundoGrado(Double a, Double b, Double c) {
		Preconditions.checkArgument(a > 0, String.format("El coeficiente a debe ser distinto de cero y es %.2f", a));
		Double disc = b * b - 4 * a * c;
		if (disc >= 0) {
			Double r1 = -b / (2 * a);
			Double r2 = Math.sqrt(disc) / (2 * a);
			Double s1 = r1 + r2;
			Double s2 = r1 - r2;
			return new Solucion2G(new Complex(s1), new Complex(s2));
		} else {
			Double re = -b / (2 * a);
			Double im = Math.sqrt(-disc) / (2 * a);
			return new Solucion2G(new Complex(re, im), new Complex(re, -im));
		}

	}
	
	public static record Solucion2G(Complex a, Complex b) {
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(Funciones_2.solEcuacionSegundoGrado(4., 1., 1.));
		System.out.println(Funciones_2.solEcuacionPrimerGrado(4., 1.));
	}

}
