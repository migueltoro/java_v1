package us.lsi.libro;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import us.lsi.tools.FileTools;
import us.lsi.tools.StreamTools;

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
		return (int) FileTools.streamFromFile(file).count();
	}
	
	public Set<String> palabrasHuecas(String file) {
		return FileTools.streamFromFile(file).collect(Collectors.toSet());
	}
	
	public Set<String> palabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = LibroF.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		return FileTools.streamFromFile(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(LibroF.separadores)))
				.filter(p->!palabrasHuecas.contains(p))
				.distinct()
				.collect(Collectors.toSet());
	}
	
	public Integer numeroDePalabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = LibroF.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		return (int) FileTools.streamFromFile(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(ln->Arrays.stream(ln.split(LibroF.separadores)))
				.filter(p->!palabrasHuecas.contains(p))
				.distinct()
				.count();
	}
	
	public Double longitudMediaDeLineas(String file) {
		return FileTools.streamFromFile(file)
				.mapToInt(ln->ln.length())
				.average()
				.getAsDouble();		
	}
	
	public Integer numeroDeLineasVacias(String file) {
		return (int) FileTools.streamFromFile(file)
				.filter(ln->ln.isEmpty())
				.count();
	}
	
	public String lineaMasLarga(String file) {
		return FileTools.streamFromFile(file)
				.max(Comparator.comparing((String ln)->ln.length()))
				.get();
	}
	
	public Integer numeroDeLineaConPalabra(String file, String palabra) {
		return StreamTools.enumerate(FileTools.streamFromFile(file))
				.filter(p->p.value().contains(palabra))
				.findFirst()
				.get()
				.counter();				
	}
	
	@Override
	public String lineaNumero(String file, Integer n) {
		return StreamTools.enumerate(FileTools.streamFromFile(file))
				.filter(p->p.counter().equals(n))
				.findFirst()
				.get()
				.value();		
	}
	
	public SortedMap<String,Integer> frecuenciasDePalabras(String file){
		return FileTools.streamFromFile(file)
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
		 return StreamTools.enumerate(FileTools.streamFromFile(file))
				 .filter(pp->!pp.value().isEmpty())
				 .flatMap(pp->pp.expand(ln->Arrays.stream(ln.split(separadores))))
				 .filter(pp->!palabrasHuecas.contains(pp.value()))
				 .collect(Collectors.groupingBy(pp->pp.value(),
						 ()->new TreeMap<>(),
						 Collectors.mapping(pp->pp.counter(),Collectors.toSet())));		 
	}

}
