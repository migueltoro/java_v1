package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aeropuertos {

	public static List<Aeropuerto> aeropuertos;
	public static Map<String,String> ciudades;
	public static Map<String,Set<Aeropuerto>> aeropuertosEnCiudad;

	
	public static void addAeropuerto(Aeropuerto v) {
		Aeropuertos.aeropuertos.add(v);
	}
	
	public static void removeAeropuerto(Aeropuerto v) {
		Aeropuertos.aeropuertos.remove(v);
	}

}
