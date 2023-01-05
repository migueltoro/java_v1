package us.lsi.ejemplos_b1;

import org.apache.commons.math3.fraction.Fraction;

public class Expresiones_1 {
	
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
		
		public static void fractions() {
			Fraction f1 = Fraction.getReducedFraction(6, 8);
			Fraction f2 = Fraction.getReducedFraction(1, 4);
			System.out.println(f1);
			System.out.println(f1.subtract(f2));
			System.out.println(f1.multiply(f2));
		}
		
		public static void exps() {
			System.out.println(3 + 9 > 9 && 8 > 3);
		    Boolean resultado = 5 + Math.sqrt(10 * 10) < 20 - 2;
		    System.out.println(resultado);
		    System.out.println(13.2 * 5);
		    System.out.println(Math.abs(-1.2));
		}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
