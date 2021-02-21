package us.lsi.whatsapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.lsi.tools.Preconditions;

public record Mensaje(LocalDate fecha, LocalTime hora, String usuario,String texto) {
	
	public static Mensaje of(LocalDate fecha, LocalTime hora, String usuario, String texto) {
		return new Mensaje(fecha, hora, usuario, texto);
	}

	public static Mensaje parse(String mensaje) {
		Matcher m = Mensaje.pattern.matcher(mensaje);
		
		Preconditions.checkArgument(m.find() && m.groupCount() == 4, 
				String.format("Formato incorrecto en grupos = %d, mensaje = %s", m.groupCount(), mensaje));
		String[] pf = m.group("fecha").split("/");
		LocalDate fecha = LocalDate.of(Integer.parseInt("20"+pf[2]),Integer.parseInt(pf[1]),Integer.parseInt(pf[0]));
		String[] ph =  m.group("hora").split(":");
		LocalTime hora = LocalTime.of(Integer.parseInt(ph[0]),Integer.parseInt(ph[1]));
		String usuario = m.group("usuario");
		String texto = m.group("texto");
		return new Mensaje(fecha,hora,usuario,texto);
	}

	private static String RE = "(?<fecha>\\d\\d?/\\d\\d?/\\d\\d?) (?<hora>\\d\\d?:\\d\\d) - (?<usuario>[^:]+): (?<texto>.+)";
	private static Pattern pattern = Pattern.compile(RE);

	
	@Override
	public String toString() {
		return String.format("%s,%s,%10s,%s",fecha.toString(),hora.toString(),usuario,texto);
	}


}
