package us.lsi.aeropuerto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.stream.Stream;

public record OcupacionVuelo(String codigoVuelo, LocalDateTime fecha, Integer numPasajeros) {
	
	
	public static OcupacionVuelo parse(String text) {
		String[] campos = text.split(",");
		String codeVuelo = campos[0];
		LocalTime t = Vuelos.vuelo(codeVuelo).hora();
		LocalDate d = LocalDate.parse(campos[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDateTime fecha = LocalDateTime.of(d, t);
		Integer numPasajeros = Integer.parseInt(campos[2]);		
		return OcupacionVuelo.of(codeVuelo,fecha,numPasajeros);
	}
	
	public static OcupacionVuelo of(String codeVuelo,
			LocalDateTime fecha,
			Integer numPasajeros) {
		return new OcupacionVuelo(codeVuelo,fecha,numPasajeros);	
	}
	
	private static Random rnd = new Random(System.nanoTime());
	
	
	public static OcupacionVuelo random(Vuelo v, Integer anyo) {
		String codeVuelo = v.codigo();
		Integer np = Vuelos.vuelo(codeVuelo).numPlazas();
		LocalTime t = Vuelos.vuelo(codeVuelo).hora();
		DayOfWeek dw = Vuelos.vuelo(codeVuelo).diaSemana();
		LocalDate d = Stream.iterate(LocalDate.of(anyo,1,1),dt->dt.plus(1,ChronoUnit.DAYS))
				.filter(dt->dt.getDayOfWeek().equals(dw))
				.findFirst()
				.get();
		d = d.plus(7*rnd.nextInt(53),ChronoUnit.DAYS); //53 semanas en un año
		LocalDateTime fecha = LocalDateTime.of(d, t);
	    Integer numPasajeros = np>0?rnd.nextInt(np):0;
		return new OcupacionVuelo(codeVuelo,fecha,numPasajeros);
	}
	
	public Vuelo vuelo() {
		return Vuelos.vuelo(this.codigoVuelo);
	}
	
	public LocalDateTime llegada() {
		Vuelo vuelo = Vuelos.vuelo(this.codigoVuelo);
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
