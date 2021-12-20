package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class Aeropuertos {
	
	
	public static void leeAeropuertos(String fichero) {
		List<Aeropuerto> aeropuertos = FileTools.streamFromFile(fichero)
				.map(x -> Aeropuerto.parse(x))
				.collect(Collectors.toList());	
		Aeropuertos.aeropuertos = aeropuertos;
	}
	
	private static List<Aeropuerto> aeropuertos;
	
	
	public static String string() {
		return String.format("Aeropuertos\n\t%s",Aeropuertos.aeropuertos.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}
	
	
	public static void addAeropuerto(Aeropuerto a) {
		Aeropuertos.aeropuertosEnCiudad = null;
		Aeropuertos.ciudadDeAeropuerto = null;
		Aeropuertos.codigosAeropuertos = null;
		Aeropuertos.aeropuertos.add(a);
	}
	
	public static void removeAeropuerto(Aeropuerto a) {
		Aeropuertos.aeropuertosEnCiudad = null;
		Aeropuertos.ciudadDeAeropuerto = null;
		Aeropuertos.codigosAeropuertos = null;
		Aeropuertos.aeropuertos.remove(a);
	}
	
	private static Map<String,Aeropuerto> codigosAeropuertos = null;
	
	public static Aeropuerto aeropuerto(String codigo) { //codigoAropueto, Aeropuerto
		if(codigosAeropuertos == null)
			codigosAeropuertos = Aeropuertos.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
		return codigosAeropuertos.get(codigo);
	}
	
	private static Map<String,String> ciudadDeAeropuerto = null;
	
	public static String ciudadDeAeropuerto(String codigo) {  //codigoAeropuerto, ciudad
		if(ciudadDeAeropuerto == null)
			ciudadDeAeropuerto = Aeropuertos.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a.ciudad()));
		return ciudadDeAeropuerto.get(codigo);
	}
	
	private static Map<String,Set<Aeropuerto>> aeropuertosEnCiudad= null;
	
	public static Set<Aeropuerto> aeropuertosEnCiudad(String ciudad) { //ciudad, {codigosAeropuerto, ...} 
		if(aeropuertosEnCiudad == null)
			aeropuertosEnCiudad = Aeropuertos.aeropuertos.stream()
			       .collect(Collectors.groupingBy(a->a.ciudad(),Collectors.toSet()));
		return aeropuertosEnCiudad.get(ciudad);
	}
	
	public static Integer size() {
		return Aeropuertos.aeropuertos.size();
	}

	public static Stream<Aeropuerto> stream() {
		return aeropuertos.stream();
	}
	
	public static Aeropuerto get(Integer i) {
		return aeropuertos.get(i);
	}


}
