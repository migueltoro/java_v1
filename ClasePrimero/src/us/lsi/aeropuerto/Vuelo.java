package us.lsi.aeropuerto;

import java.time.Duration;

public record Vuelo(
		String codigo,
		String codeDestino,
		String codeOrigen,
		Double precio,
		Integer numPlazas,	
		Duration duracion
		) {
	
	public String ciudadDestino() {
		return Aeropuertos.ciudades.get(this.codeDestino);
	}
	
	public String CiudadOrigen() {
		return Aeropuertos.ciudades.get(this.codeOrigen);
	}
}
