package us.lsi.sevici;

import java.util.Locale;

public class TestEstacion {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Estacion e = Estacion.parse("149_CALLE ARROYO,-20,11,9,37.397829929383,-5.97567172039552");
		System.out.println(e);
	}

}
