package us.lsi.aeropuerto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record OcupacionVuelo(String codigoVuelo, LocalDateTime fecha, Integer numPasajeros) {
	
	public static record Ocv(String codigoVuelo, LocalDateTime fecha) {
		public static Ocv of(String codigoVuelo, LocalDateTime fecha) {
			return new Ocv(codigoVuelo, fecha);
		}
	}
	
	public static OcupacionVuelo parse(String text) {
		String[] campos = text.split(",");
		String codeVuelo = campos[0];
		Optional<Vuelo> ov = Vuelos.of().vuelo(codeVuelo);
		Vuelo v = ov.orElse(null);
		LocalTime t = v.hora();
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
	
	public Ocv key() {
		return Ocv.of(this.codigoVuelo,this.fecha);
	}
	
	public Vuelo vuelo() {
		Optional<Vuelo> ov = Vuelos.of().vuelo(this.codigoVuelo);
		Vuelo v = ov.orElse(null);
		return v;
	}
	
	public LocalDateTime llegada() {
		Optional<Vuelo> ov = Vuelos.of().vuelo(this.codigoVuelo);
		Vuelo v = ov.orElse(null);
		return LocalDateTime.of(fecha.toLocalDate(),v.hora()).plus(v.duracion());
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
