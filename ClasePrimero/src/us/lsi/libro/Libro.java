package us.lsi.libro;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public interface Libro {
	
	public static enum TipoImplementacion{Imperativa,Funcional}

	public static String separadores = "[- ,;.\n()?�!�:\"]";
	
	public static Libro of(TipoImplementacion tipo) {
		Libro r = null;
		switch(tipo) {
		case Funcional: r = LibroF.of(); break;
		case Imperativa: r = LibroI.of(); break;
		}
		return r;	
	}
	
	Set<String> palabrasHuecas(String file);
	Integer numeroDeLineas(String file); 
	Integer numeroDePalabrasDistintasNoHuecas(String file); 
	Set<String> palabrasDistintasNoHuecas(String file); 
	Double longitudMediaDeLineas(String file); 
	Integer numeroDeLineasVacias(String file);
	String lineaMasLarga(String file); 
	Integer numeroDeLineaConPalabra(String file, String palabra); 
	String lineaNumero(String file,Integer n); 
	SortedMap<String,Integer> frecuenciasDePalabras(String file);
	SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String file);
	SortedMap<String,Set<Integer>> lineasDePalabra(String file);
	Map<Character,String> lineaMasLargaQueComienzaCon(String file);
}
