package us.lsi.sevici;

import us.lsi.tools.File2;

public class Test {

	public static void main(String[] args) {
		String r = File2.getFileCharset("ficheros/estaciones.csv");
		System.out.println(r);
	}

}
