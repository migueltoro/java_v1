package us.lsi.libro;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

import us.lsi.tools.Enumerate;
import us.lsi.tools.File2;
import us.lsi.tools.Stream2;

public class LibroF implements Libro {
	
	private static LibroF libro= null;
	
	public static LibroF of() {
		if(libro==null) {
			libro = new LibroF();
		}
		return libro;
	}
	
	private LibroF() {
		super();
	}
	
	public Integer numeroDeLineas(String file) {
		return (int) File2.streamDeFichero(file,"WINDOWS-1252").count();
	}
	
	public Set<String> palabrasHuecas(String file) {
		return File2.streamDeFichero(file,"WINDOWS-1252").collect(Collectors.toSet());
	}
	
	public Set<String> palabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = LibroF.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		return File2.streamDeFichero(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(LibroF.separadores)))
				.filter(p->!palabrasHuecas.contains(p))
				.distinct()
				.collect(Collectors.toSet());
	}
	
	public Integer numeroDePalabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = LibroF.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		return (int) File2.streamDeFichero(file,"WINDOWS-1252")
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(LibroF.separadores)))
				.filter(p->!palabrasHuecas.contains(p))
				.distinct()
				.count();
	}
	
	public Double longitudMediaDeLineas(String file) {
		return File2.streamDeFichero(file,"WINDOWS-1252")
				.mapToInt(ln->ln.length())
				.average()
				.getAsDouble();		
	}
	
	public Integer numeroDeLineasVacias(String file) {
		return (int) File2.streamDeFichero(file,"WINDOWS-1252")
				.filter(ln->ln.isEmpty())
				.count();
	}
	
	public String lineaMasLarga(String file) {
		return File2.streamDeFichero(file,"WINDOWS-1252")
				.max(Comparator.comparing((String ln)->ln.length()))
				.get();
	}
	
	public Integer numeroDeLineaConPalabra(String file, String palabra) {
		return Stream2.enumerate(File2.streamDeFichero(file,"WINDOWS-1252"))
				.filter(p->p.value().contains(palabra))
				.findFirst()
				.get()
				.counter();				
	}
	
	@Override
	public String lineaNumero(String file, Integer n) {
		return Stream2.enumerate(File2.streamDeFichero(file,"WINDOWS-1252"))
				.filter(p->p.counter().equals(n))
				.findFirst()
				.get()
				.value();		
	}
	
	public SortedMap<String,Integer> frecuenciasDePalabras(String file){
		return File2.streamDeFichero(file,"WINDOWS-1252")
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(separadores)))
				.collect(Collectors.groupingBy(p->p,
						    ()->new TreeMap<>(),
						    Collectors.collectingAndThen(Collectors.toList(),ls->ls.size())));			
	}
	
	
	public SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String file){
		SortedMap<String,Integer> fq = LibroF.of().frecuenciasDePalabras(file);
		return fq.keySet().stream()
				.collect(Collectors.groupingBy(f->fq.get(f),
						()->new TreeMap<>(),
						Collectors.toSet()));
	}
	
	
	public SortedMap<String,Set<Integer>> lineasDePalabra(String file){
		Set<String> palabrasHuecas = LibroF.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		Stream<Enumerate<String>> se = Stream2.enumerate(File2.streamDeFichero(file)).filter(p->!p.value().isEmpty());
		Stream<Enumerate<String>> se2 = 
				Stream2.<String,String>flatMapEnumerate(se,ln->Arrays.stream(ln.split(separadores)))
				 .filter(p->!palabrasHuecas.contains(p.value()));
	    return	se2.collect(Collectors.groupingBy(p->p.value(),
						 ()->new TreeMap<>(),
						 Collectors.mapping(p->p.counter(),Collectors.toSet())));		 
	}

	@Override
	public Map<Character, String> lineaMasLargaQueComienzaCon(String file) {
		return File2.streamDeFichero(file,"WINDOWS-1252")
				.filter(ln->!ln.isEmpty())
				.collect(Collectors.groupingBy(ln->ln.charAt(0),
						Collectors.reducing("",BinaryOperator.maxBy(Comparator.comparing(ln->ln.length())))));
//						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(ln->ln.length())),
//								r->r.get())));
	}

}
