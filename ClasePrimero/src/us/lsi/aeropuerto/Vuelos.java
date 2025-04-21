package us.lsi.aeropuerto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.aeropuerto.Vuelo.Ocv;
import us.lsi.tools.File2;

public class Vuelos {
	
	static Vuelos getorOcupacionesVuelos = null;
	
	public static Vuelos of() {
		return Vuelos.of("aeropuertos/ocupacionesVuelos.csv");
	}
	
	public static Vuelos of(String file) {
		if(Vuelos.getorOcupacionesVuelos == null)
			Vuelos.getorOcupacionesVuelos = new Vuelos(file);
		return Vuelos.getorOcupacionesVuelos;
	}

	private Set<Vuelo> ocupaciones;
	private Map<Ocv,Vuelo> map;
	
	public Vuelos(Set<Vuelo> ocupaciones) {
		super();
		this.ocupaciones = ocupaciones;
		this.map = this.ocupaciones.stream().collect(Collectors.toMap(ov->ov.key(),ov->ov));
	}

	private Vuelos(String file) {
		super();
		this.ocupaciones = File2.streamDeFichero(file,"Windows-1252")
				.map(x -> Vuelo.parse(x))
				.collect(Collectors.toSet());
		this.map = this.ocupaciones.stream().collect(Collectors.toMap(ov->ov.key(),ov->ov));
	}

	public Set<Vuelo> todas() {
		return this.ocupaciones;
	}
	
	public Vuelo get(Integer i) {
		return this.ocupaciones.stream().toList().get(i);
	}
	
	public Optional<Vuelo> ocupacionVuelo(String codigoVuelo, LocalDateTime fecha) {
		return Optional.ofNullable(this.map.get(Ocv.of(codigoVuelo,fecha)));
	}
	
	
	public Integer size() {
		return this.ocupaciones.size();
	}
	
}
