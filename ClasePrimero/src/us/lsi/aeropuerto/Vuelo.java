package us.lsi.aeropuerto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
		DayOfWeek diaSemana = DayOfWeek.of(Integer.parseInt(campos[8]));
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
	
	private static Random rnd = new Random(System.nanoTime());



	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return String.format("%s,%s,%s,%s,%.2f,%d,%d,%s,%s",codigoAerolinea,numero,codigoDestino,codigoOrigen,precio,
				numPlazas,duracion.toMinutes(),hora.format(formatter),diaSemana.toString());
	}
	
	
	public static Vuelo random() {
		Integer e = rnd.nextInt(Aerolineas.datos().numeroAerolineas);
		String codigo = Aerolineas.datos().aeroLineas.get(e).codigo();
		String numero = String.format("%04d",rnd.nextInt(1000));
		Integer ad = rnd.nextInt(Aeropuertos.datos().numAeropuertos());
		String codeDestino = Aeropuertos.datos().aeropuertos().get(ad).codigo();
		Integer ao;
		do {
			ao = rnd.nextInt(Aeropuertos.datos().numAeropuertos());
		} while (ao == ad);
		String codeOrigen = Aeropuertos.datos().aeropuertos().get(ao).codigo();
		Double precio = 1000*rnd.nextDouble();
		Integer numPlazas = rnd.nextInt(300);
		Duration duracion = Duration.of(rnd.nextInt(360), ChronoUnit.MINUTES);
		LocalTime hora = LocalTime.of(rnd.nextInt(24),rnd.nextInt(60));
		DayOfWeek diaSemana = DayOfWeek.of(1+rnd.nextInt(7));
		return new Vuelo(codigo,numero,codeDestino,codeOrigen,precio,numPlazas,duracion,hora,diaSemana);
	}
	
	public String ciudadDestino() {
		return Aeropuertos.datos().ciudadDeAeropuerto().get(this.codigoDestino);
	}
	
	public String ciudadOrigen() {
		return Aeropuertos.datos().ciudadDeAeropuerto().get(this.codigoOrigen);
	}	
	
	public String codigo() {
		return this.codigoAerolinea+this.numero;
	}
	
	public static void main(String[] args) {
		Vuelo v = Vuelo.parse("234Q,SVQ,LHC,234.56,167,76,22:30,1");
		System.out.println(v);
	}
	
	
	
}
