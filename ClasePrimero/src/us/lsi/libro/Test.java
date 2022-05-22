package us.lsi.libro;


import java.util.Locale;

import us.lsi.libro.Libro.TipoImplementacion;
import us.lsi.tools.Map2;


public class Test {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		TipoImplementacion t = TipoImplementacion.Funcional;
		Integer n = Libro.of(t).numeroDeLineas("ficheros/quijote.txt");
		System.out.println(n);
//		Set<String> s = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
//		System.out.println(CollectionsTools.collectionToString(s));
		n = Libro.of(t).numeroDePalabrasDistintasNoHuecas("ficheros/quijote.txt");
		System.out.println(n);
		Double m1 = Libro.of(t).longitudMediaDeLineas("ficheros/quijote.txt");
		System.out.println(m1);
//		System.out.println(m1+","+m3);
		n = Libro.of(t).numeroDeLineaConPalabra("ficheros/quijote.txt","calzas");
		System.out.println(Libro.of(t).lineaNumero("ficheros/quijote.txt", n));

//		System.out.println(Map2.toString(Libro.of().frecuenciasDePalabras("ficheros/quijote.txt")));
//		System.out.println(Libro.of().lineaMasLarga("ficheros/quijote.txt"));
//		System.out.println((Libro.of().lineasDePalabra("ficheros/quijote.txt")).get("calzas"));
//		System.out.println(Map2.toString(Libro.lineasDePalabra("ficheros/quijote.txt")));
		System.out.println(Map2.toString(Libro.of(t).lineaMasLargaQueComienzaCon("ficheros/quijote.txt")));
	}

}
