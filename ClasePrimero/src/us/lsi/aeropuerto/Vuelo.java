package us.lsi.aeropuerto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;

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

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return String.format(new Locale("en", "US"),"%s,%s,%s,%s,%.2f,%d,%d,%s,%s",
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
		return String.format(new Locale("en", "US"),"%s,%s,%s,%s,%.2f,%d,%d,%s,%d",codigoAerolinea,numero,codigoDestino,codigoOrigen,precio,
				numPlazas,duracion.toMinutes(),hora.format(formatter),diaSemana.getValue());
	}
	
	// Vuelo.of(codigo,numero,codeDestino,codeOrigen,precio,numPlazas,duracion,hora,diaSemana);
	
	private static Random rnd = new Random(System.nanoTime());
	
	public static Vuelo random() {
		Integer e = rnd.nextInt(Aerolineas.size());
		String codigo = Aerolineas.get(e).codigo();
		String numero = String.format("%04d",rnd.nextInt(1000));
		Integer ad = rnd.nextInt(Aeropuertos.size());
		String codeDestino = Aeropuertos.get(ad).codigo();
		Integer ao;
		do {
			ao = rnd.nextInt(Aeropuertos.size());
		} while (ao == ad);
		String codeOrigen = Aeropuertos.get(ao).codigo();
		Double precio = 1000*rnd.nextDouble();
		Integer numPlazas = rnd.nextInt(300);
		Duration duracion = Duration.of(rnd.nextInt(360), ChronoUnit.MINUTES);
		LocalTime hora = LocalTime.of(rnd.nextInt(24),rnd.nextInt(60));
		DayOfWeek diaSemana = DayOfWeek.of(1+rnd.nextInt(7));
		return new Vuelo(codigo,numero,codeDestino,codeOrigen,precio,numPlazas,duracion,hora,diaSemana);
	}
	
	public String ciudadDestino() {
		return Aeropuertos.ciudadDeAeropuerto(this.codigoDestino);
	}
	
	public String ciudadOrigen() {
		return Aeropuertos.ciudadDeAeropuerto(this.codigoOrigen);
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
		Aeropuertos.leeAeropuertos("ficheros/aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/aerolineas.csv");
//		Vuelo v3 = Vuelo.random();
		System.out.println(v2);
	}

}
