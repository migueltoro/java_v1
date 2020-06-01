package us.lsi.libro;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.tools.CollectionsTools;
import us.lsi.tools.Enumerate;
import us.lsi.tools.IntPair;
import us.lsi.tools.StreamTools;

@SuppressWarnings("unused")
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
		System.out.println(CollectionsTools.mapToString(Libro.lineasDePalabra("ficheros/quijote.txt")));	
//		SortedMap<Integer, Set<String>> r = Libro.palabrasPorFrecuencias("ficheros/quijote.txt");
//		System.out.println(String.format("(%s,%s)",1,r.get(1)));
//		System.out.println(CollectionsTools.mapToString(r));
//		Map<String, Integer> r1 = Libro.frecuenciasDePalabras2("ficheros/quijote.txt");
//		System.out.println(String.format("(%s,%s)",1,r.get(1)));
//		System.out.println(CollectionsTools.mapToString(r1));
//		List<Integer> ls1 = List.of(93,45,67,-1,45,931,453,67,-1,45,567,103,93,45,67,-1,45);
//		List<Integer> ls2 = List.of(931,453,67,-1,45,567,103,93,45,67,-1,45);
//		List<IntPair> r = StreamTools.zip(ls1.stream(),ls2.stream(),(a,b)->IntPair.of(a,b)).collect(Collectors.toList());
//		System.out.println(r);
//		List<Enumerate<Integer>> r2 = StreamTools.enumerate(ls1.stream()).collect(Collectors.toList());
//		System.out.println(r2);
	}

}
