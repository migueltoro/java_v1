package us.lsi.libro;

import java.util.function.Function;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
	
	public static String separadores = "[- ,;.\n()?¿!¡:\"]";
	
	public static Function<String,Stream<String>> f = linea->Arrays.stream(linea.split(Libro.separadores));
	
	public static Integer numeroDeLineas(String fichero) {
		return (int) FileTools.streamFromFile(fichero).count();
	}
	
	public static Integer numeroDeLineas2(String fichero) {
		Integer a = 0;
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(@SuppressWarnings("unused") String linea:lineas) {
			a = a+1;
		}
		return a;
	}
	
	public static Integer numeroDePalabrasDistintas(String fichero) {
		return (int) FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split(Libro.separadores)))
				.distinct()
				.count();
	}
	
	public static Integer numeroDePalabrasDistintas2(String fichero) {
		Set<String> a = new HashSet<>();
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			String[] palabras = linea.split(Libro.separadores);
			for(String palabra: palabras) {
				a.add(palabra);
			}
		}
		return a.size();
	}
	
	// https://github.com/migueltoro/clase_primero_2020
	
	public static Integer numeroDePalabrasDistintasNoHuecas(String fichero) {
		Set<String> palabrasHuecas = FileTools.streamFromFile("ficheros/palabras_huecas.txt").collect(Collectors.toSet());
		return (int) FileTools.streamFromFile(fichero)
				.flatMap(linea->Arrays.stream(linea.split(Libro.separadores)))
				.distinct()
				.filter(p->!palabrasHuecas.contains(p))
				.count();
	}
	
	public static Integer numeroDePalabrasDistintasNoHuecas2(String fichero) {
		Set<String> palabrasHuecas = new HashSet<>();
		List<String> lineasPalabrasHuecas = FileTools.lineasFromFile("ficheros/palabras_huecas.txt");
		for(String palabra:lineasPalabrasHuecas) {
			palabrasHuecas.add(palabra);
		}
		Set<String> a = new HashSet<>();
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			String[] palabras = linea.split(Libro.separadores);
			for(String palabra: palabras) {
				if (!palabrasHuecas.contains(palabra)) {
					a.add(palabra);
				}
			}
		}
		return a.size();	
	}
	
	
	public static Integer numeroDeLineasVacias(String fichero) {
		return (int) FileTools.streamFromFile(fichero)
						.filter(linea->linea.length()==0)
						.count();
	}
	
	public static Integer numeroDeLineasVacias2(String fichero) {
		Integer a = 0;
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			if(linea.length()==0) {
				a = a +1;
			}
		}
		return a;
	}
	
	public static Double longitudMediaDeLineas(String fichero) {
		return FileTools.streamFromFile(fichero)
				.mapToInt(linea->linea.length())
				.average()
				.getAsDouble();
	}
	
	public static Double longitudMediaDeLineas2(String fichero) {
		Integer s = 0;
		Integer c = 0;
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea: lineas) {
			s = s+linea.length();
			c = c+1;
		}
		return ((double)s)/c;
	}
	
	public static String primeraLineaConPalabra(String fichero, String palabra) {
		return FileTools.streamFromFile(fichero)
				.filter(linea->linea.contains(palabra))
				.findFirst()
				.get();			
	}
	
	
	public static String primeraLineaConPalabra2(String fichero, String palabra) {
		String a = null;
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			if(linea.contains(palabra)) {
				a = linea;
				break;
			}
		}
		return a;
	}
	
	public static Integer primeraNumeroLineaConPalabra(String fichero, String palabra) {
		Stream<String> lineas = FileTools.streamFromFile(fichero);
		Stream<Enumerate<String>> lineasNumeros = StreamTools.enumerate(lineas);
		return lineasNumeros.filter(e->e.value.contains(palabra))
				.findFirst()
				.get()
				.counter;
	}
	
	public static Integer primeraNumeroLineaConPalabra2(String fichero, String palabra) {
		Integer a = -1;
		Integer c = 0;
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			c = c+1;
			if(linea.contains(palabra)) {
				a = c;
				break;
			}
		}
		return a;
	}
	
	public static String lineaNumero(String fichero, Integer n) {
		Stream<String> lineas = FileTools.streamFromFile(fichero);
		Stream<Enumerate<String>> lineasNumeros = StreamTools.enumerate(lineas);
		return lineasNumeros.filter(e->e.counter.equals(n))
				.findFirst()
				.get()
				.value;
	}
	
	public static String lineaNumero2(String fichero, Integer n) {
		String a = null;
		Integer c = 0;
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			c = c+1;
			if(c == n) {
				a = linea;
				break;
			}
		}
		return a;
	}
	
	private static Stream<String> lineasAPalabras(String nl){
		return Arrays.stream(nl.split(Libro.separadores));
	}
	
	public static Map<String,Long> frecuenciasDePalabras1(String fichero) {
		return FileTools.streamFromFile(fichero)
				.flatMap(Libro::lineasAPalabras)
				.collect(Collectors.groupingBy(
						x->x,
						Collectors.counting()));				
	}
	
	
	
	public static Map<String,Integer> frecuenciasDePalabras2(String fichero) {
		return FileTools.streamFromFile(fichero)
				.flatMap(Libro::lineasAPalabras)
				.filter(p->p.length() >0)
				.collect(Collectors.groupingBy(
						x->x,
						Collectors.collectingAndThen(Collectors.counting(),x->x.intValue())));				
	}
	
	public static Map<String,Integer> frecuenciasDePalabras2_1(String fichero){
		Map<String,Integer> a = new HashMap<>();
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			String[] palabras = linea.split(Libro.separadores);
			for(String palabra: palabras) {
				if(palabra.length() >0) {
					String key = palabra;
					Integer f = 0;
					if(a.containsKey(key)) {
						f = f+ a.get(key);
					}
					a.put(key, f);
				}
			}
		}
		return a;
	}
	
	
	
	public static SortedMap<String,Integer> frecuenciasDePalabras3(String fichero) {
		return FileTools.streamFromFile(fichero)
				.flatMap(Libro::lineasAPalabras)
				.filter(p->p.length() >0)
				.collect(Collectors.groupingBy(
						x->x,
						()->new TreeMap<>(),
						Collectors.collectingAndThen(Collectors.counting(),x->x.intValue())));				
	}
	
	public static SortedMap<String,Integer> frecuenciasDePalabras3_1(String fichero){
		SortedMap<String,Integer> a = new TreeMap<>();
		List<String> lineas = FileTools.lineasFromFile(fichero);
		for(String linea:lineas) {
			String[] palabras = linea.split(Libro.separadores);
			for(String palabra: palabras) {
				if(palabra.length() >0) {
					String key = palabra;
					Integer f = 0;
					if(a.containsKey(key)) {
						f = f+ a.get(key);
					}
					a.put(key, f);
				}
			}
		}
		return a;
	}
	
	public static SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String fichero) {
		SortedMap<String,Integer> m = frecuenciasDePalabras3(fichero);
		return m.keySet().stream()
				.collect(Collectors.groupingBy(
						x->m.get(x),
						()->new TreeMap<>(),
						Collectors.toSet()));	
	}
	
	public static SortedMap<Integer,Set<String>> palabrasPorFrecuencias2(String fichero){
		SortedMap<String,Integer> m = frecuenciasDePalabras3_1(fichero);
		SortedMap<Integer,Set<String>> a = new TreeMap<>();
		for(String palabra: m.keySet()) {
			Integer key = m.get(palabra);
			if(a.containsKey(key)) {
				a.get(key).add(palabra);
			} else {
				Set<String> p = new HashSet<>();
				p.add(palabra);
				a.put(key,p);
			}
		}
		return a;
	}
	
	private static Stream<Enumerate<String>> lineasAPalabras2(Enumerate<String> nl){
		return Arrays.stream(nl.value.split(Libro.separadores))
				.map(p->Enumerate.of(nl.counter,p));
	}
	
	public static SortedMap<String,Set<Integer>> lineasDePalabra(String fichero) {
		Stream<String> lineas = FileTools.streamFromFile(fichero);
		Stream<Enumerate<String>> lineasNumeros = StreamTools.enumerate(lineas);
		return lineasNumeros
				.flatMap(Libro::lineasAPalabras2)
				.filter(np->np.value.length() >0)
				.collect(Collectors.groupingBy(
						np->np.value,
						()->new TreeMap<String,Set<Integer>>(),
						Collectors.mapping(np->np.counter,Collectors.toSet())));
	}
	
	
	public static SortedMap<String,Set<Integer>> lineasDePalabra_2(String fichero)  {
		SortedMap<String,Set<Integer>> a = new TreeMap<>();
		Integer c = 0;
		for(String linea: FileTools.lineasFromFile(fichero)) {
			for(String palabra: linea.split(Libro.separadores)) {
				String key = palabra;
				if(a.containsKey(key)) {
					a.get(key).add(c);
				} else {
					Set<Integer> s = new HashSet<>();
					s.add(c);
					a.put(key,s);
				}			
			}
			c = c+1;
		}
		return a;
	}
	

}
