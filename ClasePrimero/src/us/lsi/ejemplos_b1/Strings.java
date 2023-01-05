package us.lsi.ejemplos_b1;

import java.util.Arrays;

public class Strings {
	
	public static void ejemplos() {
		
	    Integer a = 2;
	    Double b = 4.567;
	    Integer i = 5;
	    System.out.println(String.format("El resultado de %d multiplicado por %.1f es %.1f",a, b, a*b));
	    System.out.println(String.format("%d %2d %3d",i, i*i, i*i*i));
	    System.out.println(String.format("%02d %02d %03d",i, i*i, i*i*i));
	    String nombre = "Juan";
	    Integer telefono = 678900123;
	    Double altura = 182.3;
	    System.out.println(String.format("%10s ==> %10d  => %.2f",nombre,telefono,altura));
	    System.out.println("  Juan  ".strip());
	    System.out.println("juan");
	    System.out.println("juan".toUpperCase());
	    String cd = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme";
	    System.out.println(cd.charAt(3));
	    System.out.println(cd.contains("lugar de"));
	    System.out.println(cd.length());
	    System.out.println(Arrays.asList(cd.split("[, ]")));
	}

	public static void main(String[] args) {
		ejemplos();

	}

}
