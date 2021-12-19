package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;
public class Aerolineas {
	
	public static void leeAerolineas(String fichero) {
		List<Aerolinea> datos = FileTools.streamFromFile(fichero)
				.map(x ->Aerolinea.parse(x))
				.toList();
		Aerolineas.aeroLineas = datos;
	}
	
	private static List<Aerolinea> aeroLineas;
	
	private static Map<String,Aerolinea> codigosAerolineas = null;

	public static Aerolinea aerolinea(String codigo) {
		if(codigosAerolineas == null)
			Aerolineas.codigosAerolineas = Aerolineas.aeroLineas.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
		return Aerolineas.codigosAerolineas.get(codigo);
	}
	
	public static Integer size() {
		return Aerolineas.aeroLineas.size();
	}
	
	public static Stream<Aerolinea> stream() {
		return Aerolineas.aeroLineas.stream();
	}
	
	public static Aerolinea get(Integer i) {
		return Aerolineas.aeroLineas.get(i);
	}
	
	public static String string() {
		return String.format("Aerolineas\n\t%s",Aerolineas.aeroLineas.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}

}
