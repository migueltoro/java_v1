package us.lsi.libro;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(Libro.numeroDeLineas("ficheros/quijote.txt"));
//		System.out.println(Libro.numeroDePalabrasDistintas("ficheros/quijote.txt"));
//		System.out.println(Libro.numeroDePalabrasDistintasNoHuecas("ficheros/quijote.txt"));
//		System.out.println(Libro.numeroDeLineasVacias("ficheros/quijote.txt"));
//		System.out.println(String.format("%.2f",Libro.longitudMediaDeLineas("ficheros/quijote.txt")));
//		System.out.println(String.format("%s",Libro.primeraLineaConPalabra("ficheros/quijote.txt","padre")));	
//		System.out.println(String.format("%s",Libro.primeraNumeroLineaConPalabra("ficheros/quijote.txt","padre")));	
//		System.out.println(String.format("%s",Libro.lineaNumero("ficheros/quijote.txt",300)));	
		SortedMap<Integer, Set<String>> r = Libro.palabrasPorFrecuencias("ficheros/quijote.txt");
//		System.out.println(String.format("(%s,%s)",1,r.get(1)));
		System.out.println(r.keySet().stream().map(k->String.format("(%s,%s)",k,r.get(k))).collect(Collectors.joining("\n")));

	}

}
