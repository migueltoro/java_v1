package us.lsi.aeropuerto;

import java.time.LocalDate;

public record Ocupacion(String codeVuelo, LocalDate fecha, Integer numPasajeros) {
	
	public Vuelo vuelo() {
		return Vuelos.datosVuelos.get(this.codeVuelo);
	}
	
	
}
