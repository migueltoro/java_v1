package us.lsi.aeropuerto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record Vuelo(String codigoVueloProgramado, LocalDateTime fecha, Integer numPasajeros) {
	
	public static record Ocv(String codigoVuelo, LocalDateTime fecha) {
		public static Ocv of(String codigoVuelo, LocalDateTime fecha) {
			return new Ocv(codigoVuelo, fecha);
		}
	}
	
	public static Vuelo parse(String text) {
		String[] campos = text.split(",");
		String codeVuelo = campos[0];
		LocalDateTime fecha = 
				LocalDateTime.parse(campos[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Integer numPasajeros = Integer.parseInt(campos[2]);		
		return Vuelo.of(codeVuelo,fecha,numPasajeros);
	}
	
	public static Vuelo of(String codeVuelo,
			LocalDateTime fecha,
			Integer numPasajeros) {
		return new Vuelo(codeVuelo,fecha,numPasajeros);	
	}
	
	public Ocv key() {
		return Ocv.of(this.codigoVueloProgramado,this.fecha);
	}
	
	public VueloProgramado vueloProgramado() {
		Optional<VueloProgramado> ov = VuelosProgramados.of().vuelo(this.codigoVueloProgramado);
		VueloProgramado v = ov.orElse(null);
		return v;
	}
	
	public LocalDateTime llegada() {
		Optional<VueloProgramado> ov = VuelosProgramados.of().vuelo(this.codigoVueloProgramado);
		VueloProgramado v = ov.orElse(null);
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
		return String.format("%s,%s,%d",codigoVueloProgramado,fecha.format(formatter),numPasajeros);
	}
	
}
