package us.lsi.libro;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import us.lsi.tools.Enumerate;
import us.lsi.tools.FileTools;
import us.lsi.tools.StreamTools;

public class Libro {
	
	public static Function<String,Stream<String>> f = linea->Arrays.stream(linea.split("[ ,;.\n()?¿!¡:\"]"));
	
	public static Integer numeroDeLineas(String fichero) {
		return (int) FileTools.streamFromFile(fichero).count();
	}
	
	public static Integer numeroDePalabrasDistintas(String fichero) {
		return (int) FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split("[ ,;.\n()?¿!¡:\"]")))
				.distinct()
				.count();
	}
	
	// https://github.com/migueltoro/clase_primero_2020
	
	public static Integer numeroDePalabrasDistintasNoHuecas(String fichero) {
		Set<String> palabrasHuecas = FileTools.streamFromFile("ficheros/palabras_huecas.txt").collect(Collectors.toSet());
		return (int) FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split("[ ,;.\n()?¿!¡:\"]")))
				.distinct()
				.filter(p->!palabrasHuecas.contains(p))
				.count();
	}
	
	public static Integer numeroDeLineasVacias(String fichero) {
		return (int) FileTools.streamFromFile(fichero)
						.filter(linea->linea.length()==0)
						.count();
	}
	
	public static Double longitudMediaDeLineas(String fichero) {
		return FileTools.streamFromFile(fichero)
				.mapToInt(linea->linea.length())
				.average()
				.getAsDouble();
	}
	
	public static String primeraLineaConPalabra(String fichero, String palabra) {
		return FileTools.streamFromFile(fichero)
				.filter(linea->linea.contains(palabra))
				.findFirst()
				.get();			
	}
	
	
	public static Integer primeraNumeroLineaConPalabra(String fichero, String palabra) {
		Stream<String> lineas = FileTools.streamFromFile(fichero);
		Stream<Enumerate<String>> lineasNumeros = StreamTools.enumerate(lineas);
		return lineasNumeros.filter(e->e.value.contains(palabra))
				.findFirst()
				.get()
				.counter;
	}
	
	public static String lineaNumero(String fichero, Integer n) {
		Stream<String> lineas = FileTools.streamFromFile(fichero);
		Stream<Enumerate<String>> lineasNumeros = StreamTools.enumerate(lineas);
		return lineasNumeros.filter(e->e.counter.equals(n))
				.findFirst()
				.get()
				.value;
	}
	
	public static Map<String,Long> frecuenciasDePalabras1(String fichero) {
		return FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split("[ ,;.\n()?¿!¡:\"]")))
				.collect(Collectors.groupingBy(
						x->x,
						Collectors.counting()));				
	}
	
	public static Map<String,Integer> frecuenciasDePalabras2(String fichero) {
		return FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split("[ ,;.\n()?¿!¡:\"]")))
				.filter(p->p.length() >0)
				.collect(Collectors.groupingBy(
						x->x,
						Collectors.collectingAndThen(Collectors.counting(),x->x.intValue())));				
	}
	
	public static SortedMap<String,Integer> frecuenciasDePalabras3(String fichero) {
		return FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split("[ ,;.\n()?¿!¡:\"]")))
				.filter(p->p.length() >0)
				.collect(Collectors.groupingBy(
						x->x,
						()->new TreeMap<>(),
						Collectors.collectingAndThen(Collectors.counting(),x->x.intValue())));				
	}
	
	public static SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String fichero) {
		SortedMap<String,Integer> m = frecuenciasDePalabras3(fichero);
		return m.keySet().stream()
				.collect(Collectors.groupingBy(
						x->m.get(x),
						()->new TreeMap<>(),
						Collectors.toSet()));	
	}
	
	public static SortedMap<String,Set<Integer>> lineasDePalabra(String fichero) {
		Stream<String> lineas = FileTools.streamFromFile(fichero);
		Stream<Enumerate<String>> lineasNumeros = StreamTools.enumerate(lineas);
		return lineasNumeros.
				.flatMap(numeroLinea->Arrays.stream(numeroLinea.value.split("[ ,;.\n()?¿!¡:\"]"))
						.map(p->Enumerate.of(numeroLinea.counter,p)))
				.filter(p->p.length() >0)
				.collect(Collectors.groupingBy(
						x->x,
						()->new TreeMap<>(),
						Collectors.collectingAndThen(Collectors.counting(),x->x.intValue()))
	}

}
