package us.lsi.aeropuerto;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;

import static us.lsi.tools.StreamTools.*;

public class Vuelos {
	
	private static Vuelos datos;
	
	public static Vuelos datos() {
		return datos;
	}

	public static void random(Integer numVuelos) {
		List<Vuelo> vuelos = toList(IntStream.range(0,numVuelos).boxed().map(e->Vuelo.random()));
		Vuelos.datos = new Vuelos(vuelos);
	}

	public static void leeFicheroVuelos(String fichero) {
		List<Vuelo>  vuelos = FileTools.streamFromFile(fichero)
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toList());
		Vuelos.datos = new Vuelos(vuelos);
	}
	
	private List<Vuelo> vuelos;
	private Map<String, Vuelo> codigosVuelos;
	private Integer numVuelos;
	
	private Vuelos(List<Vuelo> vuelos) {
		super();
		this.vuelos = vuelos;
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
		this.numVuelos = this.codigosVuelos.size();
	}

	public List<Vuelo> vuelos() {
		return vuelos;
	}

	public Map<String, Vuelo> codigosVuelos() {
		return codigosVuelos;
	}

	public Integer numVuelos() {
		return numVuelos;
	}

	public void addVuelo(Vuelo v) {
		Preconditions.checkArgument(!this.codigosVuelos.containsKey(v.codigoAerolinea()),
				String.format("La aerolina %s ya existe",v.toString()));
		this.vuelos.add(v);
		this.codigosVuelos.put(v.codigoAerolinea(),v);
		this.numVuelos +=1;
	}
	
	public void removeVuelo(Vuelo v) {
		this.vuelos.remove(v);
	}

	
	public static void main(String[] args) {
		Aeropuertos.leeAeropuertos("ficheros/aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/aerolineas.csv");
		Vuelos.leeFicheroVuelos("ficheros/vuelos.csv");
	}
	
}
