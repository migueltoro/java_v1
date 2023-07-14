package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Vuelos {
	
	private static Vuelos gestorVuelos = null;
	
	public static Vuelos of() {
		if(Vuelos.gestorVuelos == null)
			Vuelos.gestorVuelos = Vuelos.of("ficheros_aeropuertos/vuelos.csv");
		return Vuelos.gestorVuelos;
	}
	public static Vuelos of(String fichero) {
		Set<Vuelo>  vuelos = File2.streamDeFichero(fichero,"Windows-1252")
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toSet());
		return new Vuelos(vuelos);
	}
	
	private Set<Vuelo> vuelos;
	private Map<String, Vuelo> codigosVuelos;
	
	public Vuelos(Set<Vuelo> vuelos) {
		super();
		this.vuelos = vuelos;
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
	}

	public Set<Vuelo> todos() {
		return this.vuelos;
	}

	public Vuelo vuelo(String codigo) {
		return this.codigosVuelos.get(codigo);
	}
	
	public Vuelo get(Integer index) {
		return this.vuelos.stream().toList().get(index);
	}

	public Integer size() {
		return this.vuelos.size();
	}

	
	public static void main(String[] args) {
		Aeropuertos.of("ficheros/aeropuertos.csv");
		Aerolineas.of("ficheros/aerolineas.csv");
		Vuelos.of("ficheros/vuelos.csv");
	}
	
}
