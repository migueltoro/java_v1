package us.lsi.aeropuerto;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class Vuelos {
	
	private static Vuelos fvuelos = null;
	
	public static Vuelos of() {
		if(Vuelos.fvuelos == null)
			Vuelos.fvuelos = Vuelos.of("ficheros_aeropuertos/vuelos.csv");
		return Vuelos.fvuelos;
	}
	public static Vuelos of(String fichero) {
		List<Vuelo>  vuelos = FileTools.streamDeFichero(fichero,"Windows-1252")
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toList());
		return new Vuelos(vuelos);
	}
	
	private List<Vuelo> vuelos;
	private Integer numVuelos;
	private Map<String, Vuelo> codigosVuelos;
	
	public Vuelos(List<Vuelo> vuelos) {
		super();
		this.vuelos = vuelos;
		this.numVuelos =  this.vuelos.size();
		this.codigosVuelos = this.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
	}

	public Stream<Vuelo> stream() {
		return this.vuelos.stream();
	}

	
	public Vuelo vuelo(String codigo) {
		return this.codigosVuelos.get(codigo);
	}
	
	public Vuelo get(Integer index) {
		return this.vuelos.get(index);
	}

	public Integer size() {
		return this.numVuelos;
	}

	
	public static void main(String[] args) {
		Aeropuertos.of("ficheros/aeropuertos.csv");
		Aerolineas.of("ficheros/aerolineas.csv");
		Vuelos.of("ficheros/vuelos.csv");
	}
	
}
