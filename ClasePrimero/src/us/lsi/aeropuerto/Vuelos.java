package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Vuelos {
	
	private static Vuelos gestorVuelos = null;
	
	public static Vuelos of() {
		return Vuelos.of("aeropuertos/vuelos.csv");
	}
	
	public static Vuelos of(String file) {
		if(Vuelos.gestorVuelos == null) 
			Vuelos.gestorVuelos = new Vuelos(file);
		return Vuelos.gestorVuelos;
	}
	
	private Set<Vuelo> vuelos;
	private Map<String, Vuelo> codigosVuelos;
	
	public Vuelos(Set<Vuelo> vuelos) {
		this.vuelos = vuelos;
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
	}
	
	private Vuelos(String file) {
		super();
		this.vuelos = File2.streamDeFichero(file,"Windows-1252")
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toSet());
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(Vuelo::codigo,x->x));
	}

	public Set<Vuelo> todos() {
		return this.vuelos;
	}

	public Optional<Vuelo> vuelo(String codigo) {
		return Optional.ofNullable(this.codigosVuelos.get(codigo));
	}
	
	public Vuelo get(Integer index) {
		return this.vuelos.stream().toList().get(index);
	}

	public Integer size() {
		return this.vuelos.size();
	}

	
	public static void main(String[] args) {
		Aeropuertos.of();
		Aerolineas.of();
		Vuelos.of();
	}
	
}
