package us.lsi.libro;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Set;

import us.lsi.tools.FileTools;

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
	

}
