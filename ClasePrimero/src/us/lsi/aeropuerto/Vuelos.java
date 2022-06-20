package us.lsi.aeropuerto;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

import static us.lsi.tools.StreamTools.*;

public class Vuelos {
	
	private static Vuelos fvuelos = null;
	
	public static Vuelos get() {
		return Vuelos.fvuelos;
	}
	public static Vuelos random(Integer numVuelos) {
		List<Vuelo> vuelos = toList(IntStream.range(0,numVuelos).boxed().map(e->Vuelo.random()));
		return new Vuelos(vuelos);
	}

	public static Vuelos leeFicheroVuelos(String fichero) {
		List<Vuelo>  vuelos = FileTools.streamFromFile(fichero)
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toList());
		return new Vuelos(vuelos);
	}
	
	private List<Vuelo> vuelos;
	private Integer numVuelos;
	private Map<String, Vuelo> codigosVuelos = null;
	
	public Vuelos(List<Vuelo> vuelos) {
		super();
		this.vuelos = vuelos;
		this.numVuelos =  this.vuelos.size();
	}

	public Stream<Vuelo> stream() {
		return this.vuelos.stream();
	}

	
	public Vuelo vuelo(String codigo) {
		if(this.codigosVuelos == null)
			this.codigosVuelos = this.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
		return this.codigosVuelos.get(codigo);
	}
	
	public Vuelo get(Integer index) {
		return this.vuelos.get(index);
	}

	
	public Integer size() {
		return this.numVuelos;
	}

	public void addVuelo(Vuelo v) {
		this.codigosVuelos = null;
		this.vuelos.add(v);
		this.numVuelos +=1;
	}
	
	public void removeVuelo(Vuelo v) {
		this.codigosVuelos = null;
		this.vuelos.remove(v);
		this.numVuelos -=1;
	}

	
	public static void main(String[] args) {
		Aeropuertos.leeAeropuertos("ficheros/aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/aerolineas.csv");
		Vuelos.leeFicheroVuelos("ficheros/vuelos.csv");
	}
	
}
