package us.lsi.aeropuerto1;

import java.time.Duration;
import java.time.LocalDate;

public record Vuelo(String destino,
		Double precio,
		Integer numPlazas,
		Integer numPasajeros,
		String codigo,
		LocalDate fecha,
		Duration duracion) {

}
