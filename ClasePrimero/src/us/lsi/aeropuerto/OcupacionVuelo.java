package us.lsi.aeropuerto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record OcupacionVuelo(String codigoVuelo, LocalDateTime fecha, Integer numPasajeros) {
	
	
	public static OcupacionVuelo parse(String text) {
		String[] campos = text.split(",");
		String codeVuelo = campos[0];
		LocalTime t = Vuelos.of().vuelo(codeVuelo).hora();
		LocalDate d = 
				LocalDateTime.parse(campos[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
				.toLocalDate();
		LocalDateTime fecha = LocalDateTime.of(d, t);
		Integer numPasajeros = Integer.parseInt(campos[2]);		
		return OcupacionVuelo.of(codeVuelo,fecha,numPasajeros);
	}
	
	public static OcupacionVuelo of(String codeVuelo,
			LocalDateTime fecha,
			Integer numPasajeros) {
		return new OcupacionVuelo(codeVuelo,fecha,numPasajeros);	
	}
	
	public Vuelo vuelo() {
		return Vuelos.of().vuelo(this.codigoVuelo);
	}
	
	public LocalDateTime llegada() {
		Vuelo vuelo = Vuelos.of().vuelo(this.codigoVuelo);
		return LocalDateTime.of(fecha.toLocalDate(),vuelo.hora()).plus(vuelo.duracion());
	}
	
	public LocalDate fechaSalida() {
		return this.fecha().toLocalDate();
	}
	
	public LocalTime horaSalida() {
		return this.fecha().toLocalTime();
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return String.format("%s,%s,%d",codigoVuelo,fecha.format(formatter),numPasajeros);
	}
	
	

}
