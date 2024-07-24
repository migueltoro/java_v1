package us.lsi.aeropuerto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public record Vuelo(
		String codigoAerolinea,
		String numero,
		String codigoDestino,
		String codigoOrigen,
		Double precio,
		Integer numPlazas,	
		Duration duracion,
		LocalTime hora,
		DayOfWeek diaSemana
		) {

	// TP,0705,BER,KTW,294,170,287,14:50,FRIDAY
	
	public static Vuelo parse(String text) {
		String[] campos = text.split(",");
		String codigo = campos[0];
		String numero = campos[1];
		String codeDestino = campos[2];
		String codeOrigen = campos[3];
		Double precio = Double.parseDouble(campos[4]);
		Integer numPlazas = Integer.parseInt(campos[5]);
		Duration duracion = Duration.of(Integer.parseInt(campos[6]), ChronoUnit.MINUTES);
		LocalTime hora = LocalTime.parse(campos[7], DateTimeFormatter.ofPattern("HH:mm"));
//		DayOfWeek diaSemana = DayOfWeek.of(Integer.parseInt(campos[8])); // ???
		DayOfWeek diaSemana = DayOfWeek.valueOf(campos[8]);
		return Vuelo.of(codigo,numero,codeDestino,codeOrigen,precio,numPlazas,duracion,hora,diaSemana);
	}
	
	public static Vuelo of(String codigo,
			String numero,
			String codeDestino,
			String codeOrigen,
			Double precio,
			Integer numPlazas,	
			Duration duracion,
			LocalTime hora,
			DayOfWeek diaSemana) {
		return new Vuelo(codigo,numero,codeDestino,codeOrigen,precio,numPlazas,duracion,hora,diaSemana);	
	}
	
	public Vuelo {
		if (codigoAerolinea == null || numero == null || codigoDestino == null || codigoOrigen == null || precio == null
				|| numPlazas == null || duracion == null || hora == null || diaSemana == null)
			throw new IllegalArgumentException("Vuelo no puede ser null");
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return String.format(Locale.of("en", "US"),"%s,%s,%s,%s,%.2f,%d,%d,%s,%s",
				codigoAerolinea,
				numero,
				codigoDestino,
				codigoOrigen,
				precio,
				numPlazas,
				duracion.toMinutes(),
				hora.format(formatter),
				diaSemana.toString());
	}
	
	public String toString2() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return String.format(Locale.of("en", "US"),"%s,%s,%s,%s,%.2f,%d,%d,%s,%d",codigoAerolinea,numero,codigoDestino,codigoOrigen,precio,
				numPlazas,duracion.toMinutes(),hora.format(formatter),diaSemana.getValue());
	}
	
	// Vuelo.of(codigo,numero,codeDestino,codeOrigen,precio,numPlazas,duracion,hora,diaSemana);
	
	
	
	public String ciudadDestino() {
		return Aeropuertos.of().ciudadDeAeropuerto(this.codigoDestino).orElse("Aeropuerto no existente");
	}
	
	public String ciudadOrigen() {
		return Aeropuertos.of().ciudadDeAeropuerto(this.codigoOrigen).orElse("Aeropuerto no existente");
	}	
	
	public String codigo() {
		return this.codigoAerolinea+this.numero;
	}
	
	public static void main(String[] args) {
//		Locale.setDefault(new Locale("en", "US"));
		Vuelo v1 = Vuelo.parse("TP,0705,BER,KTW,294,170,287,14:50,FRIDAY");
		String s = v1.toString();
		System.out.println(s);
//		System.out.println(v1.toString2());
		Vuelo v2 = Vuelo.parse(s);
		Aeropuertos.of();
		Aerolineas.of();
//		Vuelo v3 = Vuelo.random();
		System.out.println(v2);
	}

}
