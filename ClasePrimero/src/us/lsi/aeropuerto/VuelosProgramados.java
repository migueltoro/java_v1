package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class VuelosProgramados {
	
	private static VuelosProgramados gestorVuelos = null;
	
	public static VuelosProgramados of() {
		return VuelosProgramados.of("aeropuertos/vuelos.csv");
	}
	
	public static VuelosProgramados of(String file) {
		if(VuelosProgramados.gestorVuelos == null) 
			VuelosProgramados.gestorVuelos = new VuelosProgramados(file);
		return VuelosProgramados.gestorVuelos;
	}
	
	private Set<VueloProgramado> vuelos;
	private Map<String, VueloProgramado> codigosVuelos;
	
	public VuelosProgramados(Set<VueloProgramado> vuelos) {
		this.vuelos = vuelos;
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(VueloProgramado::codigo,x->x));
	}
	
	private VuelosProgramados(String file) {
		super();
		this.vuelos = File2.streamDeFichero(file,"Windows-1252")
				.map(x -> VueloProgramado.parse(x))
				.collect(Collectors.toSet());
		this.codigosVuelos = this.vuelos.stream().collect(Collectors.toMap(VueloProgramado::codigo,x->x));
	}

	public Set<VueloProgramado> todos() {
		return this.vuelos;
	}

	public Optional<VueloProgramado> vuelo(String codigo) {
		return Optional.ofNullable(this.codigosVuelos.get(codigo));
	}
	
	public VueloProgramado get(Integer index) {
		return this.vuelos.stream().toList().get(index);
	}

	public Integer size() {
		return this.vuelos.size();
	}

	
	public static void main(String[] args) {
		Aeropuertos.of();
		Aerolineas.of();
		VuelosProgramados.of();
	}
	
}
