package us.lsi.libro;

import java.util.Locale;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(Libro.numeroDeLineas("ficheros/quijote.txt"));
		System.out.println(Libro.numeroDePalabrasDistintas("ficheros/quijote.txt"));
		System.out.println(Libro.numeroDePalabrasDistintasNoHuecas("ficheros/quijote.txt"));
		System.out.println(Libro.numeroDeLineasVacias("ficheros/quijote.txt"));
		System.out.println(String.format("%.2f",Libro.longitudMediaDeLineas("ficheros/quijote.txt")));
	}

}
