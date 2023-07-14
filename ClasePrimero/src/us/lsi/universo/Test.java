package us.lsi.universo;

import java.awt.Color;
import java.util.Locale;

import us.lsi.geometria.Punto2D;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Universo2D universo = Universo2D.empty("Universo", 1200, 600, Color.BLUE);
		Estrella sol = Estrella.of("Sol", Punto2D.of(100., 100.), 30, universo);
		Estrella polar = Estrella.of("Polar", Punto2D.of(1100., 500.), 40, universo);
		Planeta tierra = Planeta.of("Tierra", sol);
		System.out.println(sol);
		System.out.println(polar);
		System.out.println(tierra);
	}

}
