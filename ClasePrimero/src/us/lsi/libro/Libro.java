package us.lsi.libro;


import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import us.lsi.tools.CollectionsTools;
import us.lsi.tools.FileTools;
import us.lsi.tools.StreamTools;

public class Libro {
	
//	Integer numeroDeLineas(String file); 
//	Integer numeroDePalabrasDistintasNoHuecas(String file); 
//	Set<String> palabrasDistintasNoHuecas(String file); 
//	Double longitudMediaDeLineas(String file); 
//	Integer numeroDeLineasVacias(String file);
//	String lineaMasLarga(String file); 
//	Integer primeraLineaConPalabra(String file, String palabra); 
//	String lineaNumero(String file,Integer n); 
//	SortedMap<String,Integer> frecuenciasDePalabras(String file);
//	SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String file);
//	SortedMap<String,Set<Integer>> lineasDePalabra(String file);
	
	public static String separadores = "[- ,;.\n()?¿!¡:\"]";
	
	public static Integer numeroDeLineas(String file) {
		return (int) FileTools.streamFromFile(file).count();
	}
	
	public static Integer numeroDeLineas2(String file) {
		List<String> ls = FileTools.lineasFromFile(file);
		return ls.size();
	}
	
	public static Set<String> palabrasHuecas(String file) {
		return FileTools.streamFromFile(file).collect(Collectors.toSet());
	}
	
	public static Set<String> palabrasHuecas2(String file) {
		Set<String> s = new HashSet<>();
		for(String p: FileTools.lineasFromFile(file)) {
			s.add(p);
		}
		return s;
	}
	
	public static Integer numeroDePalabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
		return (int) FileTools.streamFromFile(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(Libro.separadores)))
				.filter(p->!palabrasHuecas.contains(p))
				.distinct()
				.count();		
	}
	
	public static Integer numeroDePalabrasDistintasNoHuecas2(String file) {
		Set<String> palabrasHuecas = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
		List<String> lineas = FileTools.lineasFromFile(file);
		Set<String> palabrasDistintas = new HashSet<>();
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				for(String p: linea.split(separadores)) {
					if(!palabrasHuecas.contains(p)) {
						palabrasDistintas.add(p);
					}
				}
			}
		}
		return palabrasDistintas.size();
	}
	
	public static Set<String> palabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
		return FileTools.streamFromFile(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(Libro.separadores)))
//				.filter(p->!Libro.palabrasHuecas("ficheros/palabras_huecas.txt").contains(p))
				.filter(p->!palabrasHuecas.contains(p))
				.distinct()
				.collect(Collectors.toSet());
	}
	
	public static Double longitudMediaDeLineas(String file) {
		return FileTools.streamFromFile(file)
				.mapToInt(ln->ln.length())
				.average()
				.getAsDouble();		
	}
	
	public static Double longitudMediaDeLineas2(String file) {
		Integer numLineas = 0;
		Integer sumTamLineas = 0;
		for(String linea: FileTools.lineasFromFile(file)) {
			Integer ln = linea.length();
			numLineas ++;
			sumTamLineas += ln;
		}
		return ((double)sumTamLineas)/numLineas;
	}
	
	public static Integer numeroDeLineasVacias(String file) {
		return (int) FileTools.streamFromFile(file)
				.filter(ln->ln.isEmpty())
				.count();
	}
	
	public static Integer numeroDeLineasVacias2(String file) {
		Integer n = 0;
		for(String linea: FileTools.lineasFromFile(file)) {
			if(linea.isEmpty()) {
				n++;
			}
		}
		return n;
	}
	
	public static String lineaMasLarga(String file) {
		return FileTools.streamFromFile(file)
				.max(Comparator.comparing((String ln)->ln.length()))
				.get();
	}
	
	public static String lineaMasLarga2(String file) {
		String lineaMaslarga = null;
		Integer lnlineaMaslarga = null;
		for(String linea: FileTools.lineasFromFile(file)) {
			Integer nl = linea.length();
			if(lnlineaMaslarga == null || nl > lnlineaMaslarga) {
				lineaMaslarga = linea;
				lnlineaMaslarga = nl;
			}
		}
		return lineaMaslarga;
	}
	
	public static Integer primeraLineaConPalabra(String file, String palabra) {
		return StreamTools.enumerate(FileTools.streamFromFile(file))
				.filter(p->p.value().contains(palabra))
				.findFirst()
				.get()
				.counter();				
	}
	
	public static Integer primeraLineaConPalabra2(String file, String palabra) {
		Integer n = 0;
		Integer r = -1;
		for(String linea: FileTools.lineasFromFile(file)) {
			if(linea.contains(palabra)) {
				r = n;
				break;
			}
			n++;
		}
		return r;
	}
	
	public static SortedMap<String,Integer> frecuenciasDePalabras(String file){
		return FileTools.streamFromFile(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(separadores)))
				.collect(Collectors.groupingBy(p->p,
						    ()->new TreeMap<>(),
						    Collectors.collectingAndThen(Collectors.toList(),ls->ls.size())));
				
				
	}
	
	public static SortedMap<String,Integer> frecuenciasDePalabras2(String file) {
		Set<String> palabrasHuecas = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
		List<String> lineas = FileTools.lineasFromFile(file);
//		Set<String> palabrasDistintas = new HashSet<>();
		SortedMap<String,Integer> m = new TreeMap<>();
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				for(String p: linea.split(separadores)) {
					if(!palabrasHuecas.contains(p)) {
//						palabrasDistintas.add(p);
						if(!m.containsKey(p)) {
							m.put(p,1);
						} else {
							Integer f = m.get(p);
							m.put(p,f+1);
						}
					}
				}
			}
		}
		return m;
	}
	
	public static SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String file){
		SortedMap<String,Integer> fq = Libro.frecuenciasDePalabras(file);
		return fq.keySet().stream()
				.collect(Collectors.groupingBy(f->fq.get(f),
						()->new TreeMap<>(),
						Collectors.toSet()));
	}
	
	public static SortedMap<Integer,Set<String>> palabrasPorFrecuencias2(String file){
		SortedMap<String,Integer> fq = Libro.frecuenciasDePalabras(file);
		SortedMap<Integer,Set<String>> r = new TreeMap<>();
		for(String p: fq.keySet()) {
			Integer f = fq.get(p);
			if(!r.containsKey(f)) {
				Set<String> s = new HashSet<>();
				s.add(p);
				r.put(f, s);
			} else {
				r.get(f).add(p);
			}
		}
		return r;
	}
	
	public static SortedMap<String,Set<Integer>> lineasDePalabra(String file){
		Set<String> palabrasHuecas = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
		 return StreamTools.enumerate(FileTools.streamFromFile(file))
				 .filter(pp->!pp.value().isEmpty())
				 .flatMap(pp->pp.expand(ln->Arrays.stream(ln.split(separadores))))
				 .filter(pp->!palabrasHuecas.contains(pp.value()))
				 .collect(Collectors.groupingBy(pp->pp.value(),
						 ()->new TreeMap<>(),
						 Collectors.mapping(pp->pp.counter(),Collectors.toSet())));		 
	}
	
	public static SortedMap<String,Set<Integer>> lineasDePalabra2(String file){
		Set<String> palabrasHuecas = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
		List<String> lineas = FileTools.lineasFromFile(file);
		SortedMap<String,Set<Integer>> r = new TreeMap<>();
		Integer nl = 0;
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				for(String p: linea.split(separadores)) {
					if(!palabrasHuecas.contains(p)) {
						if(!r.containsKey(p)) {
							Set<Integer> s = new HashSet<>();
							s.add(nl);
							r.put(p, s);
						} else {
							r.get(p).add(nl);
						}
					}
				}
			}
			nl++;
		}
		return r;
		
		
	}
	
	public static void main(String[] args) {
//		Integer n = Libro.numeroDeLineas2("ficheros/quijote.txt");
//		Set<String> s = Libro.palabrasHuecas("ficheros/palabras_huecas.txt");
//		System.out.println(CollectionsTools.collectionToString(s));
//		Integer n = numeroDePalabrasDistintasNoHuecas("ficheros/quijote.txt");
//		System.out.println(n);
		System.out.println(Libro.primeraLineaConPalabra2("ficheros/quijote.txt","calzas"));
//		System.out.println(linea);
//		System.out.println(CollectionsTools.mapToString(Libro.frecuenciasDePalabras("ficheros/quijote.txt")));
//		System.out.println(Libro.lineaMasLarga("ficheros/quijote.txt"));
//		System.out.println((Libro.lineasDePalabra("ficheros/quijote.txt")).get("calzas"));
		System.out.println(CollectionsTools.mapToString(Libro.lineasDePalabra("ficheros/quijote.txt")));
	}

}
