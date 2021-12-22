package us.lsi.aeropuerto;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

import static us.lsi.tools.StreamTools.*;

public class Vuelos {
	

	public static void random(Integer numVuelos) {
		List<Vuelo> vuelos = toList(IntStream.range(0,numVuelos).boxed().map(e->Vuelo.random()));
		Vuelos.vuelos = vuelos;
		Vuelos.numVuelos = Vuelos.vuelos.size();
	}

	public static void leeFicheroVuelos(String fichero) {
		List<Vuelo>  vuelos = FileTools.streamFromFile(fichero)
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toList());
		Vuelos.vuelos = vuelos;
		Vuelos.numVuelos = Vuelos.vuelos.size();
	}
	
	private static List<Vuelo> vuelos;

	public static Stream<Vuelo> stream() {
		return vuelos.stream();
	}

	private static Map<String, Vuelo> codigosVuelos;
	
	public static Vuelo vuelo(String codigo) {
		if(codigosVuelos == null)
			Vuelos.codigosVuelos = Vuelos.vuelos.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
		return codigosVuelos.get(codigo);
	}
	
	public static Vuelo get(Integer index) {
		return vuelos.get(index);
	}

	private static Integer numVuelos;
	public static Integer size() {
		return numVuelos;
	}

	public void addVuelo(Vuelo v) {
		Vuelos.codigosVuelos = null;
		Vuelos.vuelos.add(v);
		Vuelos.numVuelos +=1;
	}
	
	public void removeVuelo(Vuelo v) {
		Vuelos.codigosVuelos = null;
		Vuelos.vuelos.remove(v);
		Vuelos.numVuelos -=1;
	}

	
	public static void main(String[] args) {
		Aeropuertos.leeAeropuertos("ficheros/aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/aerolineas.csv");
		Vuelos.leeFicheroVuelos("ficheros/vuelos.csv");
	}
	
}
