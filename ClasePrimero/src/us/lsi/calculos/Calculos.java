package us.lsi.calculos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static us.lsi.tools.StreamTools.*;

import us.lsi.tools.Preconditions;

public class Calculos {

	// Integer, Float, Double, Boolean, String, int, float, double,

	public static void ejemplo1() {
		Integer a = 23;
		Double r = 45.;
		r = r * a;
		Integer b = a % 2;
		Integer d = a + b;
		a = 2 * b + d;
		Boolean c = !(a >= b && a < 2 || r < 3);
		System.out.println(c);
		System.out.println(b);
		Integer n = a / b;
		System.out.println(n);
		System.out.println(a / r);
		Double h = 2 / 3.;
		System.out.println(h);
		r = Math.cos(0.7) * 3 * Math.pow(2., 3.);
		String cd = "Hola Antonio" + " de nuevo"
				+ String.format("El radio debe ser mayor o igual a cero y es %.2f y el otro %d y %s", r, a, "Hola");
		System.out.println(cd);
	}

	public static Double areaCirculo(Double radio) {
		Preconditions.checkArgument(radio >= 0,
				String.format("El radio debe ser mayor o igual a cero y es %.2f'", radio));
		return Math.PI * radio * radio;
	}

	public static Double longitud_circunferencia(Double radio) {
		Preconditions.checkArgument(radio >= 0,
				String.format("El radio debe ser mayor o igual a cero y es %.2f'", radio));
		return 2 * Math.PI * radio;
	}

	public static Double sol_ecuacion_primer_grado(Double a, Double b) {
		Preconditions.checkArgument(a > 0, String.format("El coeficiente a debe ser distinto de cero y es %.2f", a));
		return -b / a;
	}

	public static Solucion2G solEcuacionSegundoGrado(Double a, Double b, Double c) {
		Preconditions.checkArgument(a > 0, String.format("El coeficiente a debe ser distinto de cero y es %.2", a));
		Double disc = b * b - 4 * a * c;
		if (disc >= 0) {
			Double r1 = -b / (2 * a);
			Double r2 = Math.sqrt(disc) / (2 * a);
			Double s1 = r1 + r2;
			Double s2 = r1 - r2;
			return new Solucion2G(Complex2.of(s1), Complex2.of(s2));
		} else {
			Double re = -b / (2 * a);
			Double im = Math.sqrt(-disc) / (2 * a);
			return new Solucion2G(Complex2.of(re, im), Complex2.of(re, -im));
		}

	}

	public static void ejemploWhile() {
		Integer n = 10;
		Integer suma = 0;
		int i = 1;
		while (i <= n) {
			suma = suma + i;
			i++;
		}
	}

	public static void ejemploSwitch() {
		String r = "Lunes";
		Character s = switch (r) {
		case "Lunes" -> r.charAt(0);
		case "Martes" -> r.charAt(1);
		default -> throw new IllegalArgumentException("Unexpected value: " + r);
		};
		System.out.println(s);
	}

	public static void ejemplosScanner() {
		Scanner in = new Scanner(System.in);
		System.out.printf("Ejemplos 4");
		System.out.printf("Introduzca una cadena\n");
		String s = in.nextLine();
		in.close();
		Double r = 1.1;
		Integer a = 3;
		System.out.printf("%s\n", s);
		System.out.printf("(%.2f,%d)\n", r, a);
	}

	public static void fractions() {
		Fraction2 f1 = Fraction2.of(6, 8);
		Fraction2 f2 = Fraction2.of(1, 4);
		System.out.println(f1);
		System.out.println(f1.subtract(f2));
		System.out.println(f1.multiply(f2));
	}

	// List<E>

	public static void ejemploListas() {
		List<Integer> ls = new ArrayList<>();
		ls.add(23);
		ls.add(567);
		ls.add(65);
		System.out.println(ls);
		System.out.println(ls.get(2));
		List<Integer> ls2 = Arrays.asList(23, 567, 65, 65);
		System.out.println(ls2);
		List<Integer> ls3 = List.of(23, 567, 65);
		System.out.println(ls3);
		Set<Integer> s1 = new HashSet<>();
		s1.add(23);
		s1.add(567);
		s1.add(65);
		System.out.println(s1);
		Set<Integer> s2 = Set.of(23, 567, 65);
		System.out.println(s2);
		Set<Integer> s3 = new HashSet<>(Set.of(23, 567, 65));
		s3.add(56);
		s3.remove(567);
		System.out.println(s3);
		Integer sm = 0;
		for (Integer e : ls) {
			sm = sm + e;
		}
		System.out.println(sm);
		Integer sm2 = 0;
		for (Integer e : s1) {
			sm2 = sm2 + e;
		}
		System.out.println(sm2);
		Integer[] a = { 23, 567, 65 };
		Integer sm3 = 0;
		for (Integer e : a) {
			sm3 = sm3 + e;
		}
		System.out.println(a[2]);
		a[1] = -23;
		System.out.println(Arrays.asList(a));
		Set<Integer> s4 = new HashSet<>(ls2);
		System.out.println(ls2);
		System.out.println(s4);
	}

	public static int mcd(int a, int b) {
		Tmcd t = Tmcd.of(a, b);
		while (t.b() != 0) {
			t = Tmcd.of(t.b(), t.a() % t.b());
		}
		return t.a();
	}

	// ls = (e for e in range(a,b) if (e-a)%c==0)

	public static Stream<Integer> secuenciaAritmetica(Integer a, Integer b, Integer c) {
		return IntStream.range(a, b).boxed().filter(e -> (e - a) % c == 0);
	}

	public static Double media(Stream<Integer> st) {
		Tmedia ac = new Tmedia(0, 0);
		for (Integer e : toIterable(st)) {
			ac = new Tmedia(ac.sum + e, ac.n + 1);
		}
		Preconditions.checkArgument(ac.n > 0, String.format("El iterador esta vacio"));
		return (1.0 * ac.sum) / ac.n;
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(Calculos.solEcuacionSegundoGrado(4., 1., 1.));
		System.out.println(toList(Calculos.secuenciaAritmetica(5, 20, 3)));
		System.out.println(Calculos.media(Calculos.secuenciaAritmetica(5, 20, 3)));
		fractions();
		System.out.println(mcd(79, 11));
	}

	public static record Tmcd(Integer a, Integer b) {
		public static Tmcd of(Integer a, Integer b) {
			return new Tmcd(a, b);
		}
	}

	public static record Tmedia(Integer sum, Integer n) {
	}

	public static record Solucion2G(Complex2 a, Complex2 b) {

		public static Solucion2G of(Complex2 a, Complex2 b) {
			return new Solucion2G(a, b);
		}

		public String toString() {
			return String.format("(%s,%s)", this.a, this.b);
		}
	}

}
