package us.lsi.libro;


import java.util.Locale;
import us.lsi.tools.CollectionsTools;


public class Test {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
//		Integer n = Libro.numeroDeLineas2("ficheros/quijote.txt");
//		Set<String> s = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
//		System.out.println(CollectionsTools.collectionToString(s));
		Integer n = Libro.numeroDePalabrasDistintasNoHuecas("ficheros/quijote.txt");
		System.out.println(n);
		System.out.println(Libro.primeraLineaConPalabra2("ficheros/quijote.txt","calzas"));
//		System.out.println(linea);
//		System.out.println(CollectionsTools.mapToString(Libro.frecuenciasDePalabras("ficheros/quijote.txt")));
//		System.out.println(Libro.lineaMasLarga("ficheros/quijote.txt"));
//		System.out.println((Libro.lineasDePalabra("ficheros/quijote.txt")).get("calzas"));
		System.out.println(CollectionsTools.mapToString(Libro.lineasDePalabra("ficheros/quijote.txt")));
	}

}
