package us.lsi.calculos;



import java.util.Locale;
import java.util.Scanner;

public class Ejemplos {

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

	
	public static int mcd(int a, int b) {
		Tmcd t = Tmcd.of(a, b);
		while (t.b() != 0) {
			t = Tmcd.of(t.b(), t.a() % t.b());
		}
		return t.a();
	}

	// ls = (e for e in range(a,b) if (e-a)%c==0)

	public static record Tmcd(Integer a, Integer b) {
		public static Tmcd of(Integer a, Integer b) {
			return new Tmcd(a, b);
		}
	}
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		fractions();
		System.out.println(mcd(79, 11));
	}

	

	

}
