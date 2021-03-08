package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;
import us.lsi.tools.StreamTools;

public class Aeropuertos {

	public static List<Aeropuerto> aeropuertos;
	public static Map<String,Aeropuerto> codigosAeropuertos;
	public static Map<String,String> ciudadDeAeropuerto;  //codigoAeropuerto, ciudad
	public static Map<String,Set<Aeropuerto>> aeropuertosEnCiudad; //ciudad, {codigosAeropuerto, ...} 
	public static Integer numAeropuertos;

	public static void leeAeropuertos(String fichero) {
		Aeropuertos.aeropuertos = FileTools.streamFromFile(fichero)
				.map(x -> Aeropuerto.parse(x))
				.collect(Collectors.toList());
		try {
			Aeropuertos.codigosAeropuertos = Aeropuertos.aeropuertos.stream()
					.collect(Collectors.toMap(Aeropuerto::codigo,x->x));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		try {
			Aeropuertos.ciudadDeAeropuerto = Aeropuertos.aeropuertos.stream()
					.collect(Collectors.toMap(Aeropuerto::codigo, Aeropuerto::ciudad));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		Aeropuertos.aeropuertosEnCiudad = StreamTools.groupingSet(Aeropuertos.aeropuertos.stream(),Aeropuerto::ciudad);
		Aeropuertos.numAeropuertos = Aeropuertos.ciudadDeAeropuerto.size();
	}
	
	public static void addAeropuerto(Aeropuerto v) {
		Aeropuertos.aeropuertos.add(v);
	}
	
	public static void removeAeropuerto(Aeropuerto v) {
		Aeropuertos.aeropuertos.remove(v);
	}

}
