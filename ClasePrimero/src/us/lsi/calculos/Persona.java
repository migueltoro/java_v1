package us.lsi.calculos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record Persona(String nombre, String apellidos, LocalDateTime fechaNacimiento) {
	
	public static Persona of(String nombre, String apellidos,LocalDateTime fechaNacimiento) {
		return new Persona(nombre,apellidos,fechaNacimiento);
	}
	
	public static Persona parse(String text) {
		String[] partes = text.split(",");
		String nc = partes[0];
		String[] partes_nc = nc.split(" ");
		LocalDate ld = LocalDate.parse(partes[1],DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalTime lt = LocalTime.parse(partes[2],DateTimeFormatter.ofPattern("HH:mm"));
		return Persona.of(partes_nc[0],partes_nc[1],LocalDateTime.of(ld, lt));
	}
	
	public Integer edad() {
		LocalDateTime now = LocalDateTime.now();
		Period p = Period.between(fechaNacimiento.toLocalDate(),now.toLocalDate());
		return p.getYears();
	}
	
	public String nombre_completo() {
		return String.format("%s %s",this.nombre(),this.apellidos());
	}
	
	public String toString() {
		String fs = fechaNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hs = fechaNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		return String.format("%s, de %d, nacido el %s a las %s",nombre_completo(),edad(),fs,hs);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("es", "Es"));
		Persona p = Persona.of("Antonio","Ramirez",LocalDateTime.of(1980,Month.APRIL,14,14,20));
		Persona p2 = Persona.parse("Juan Balmez,10-03-1991,02:31");
		System.out.println(p);
		System.out.println(p.edad());
		System.out.println(p2);
		System.out.println(p2.edad());
	}

}
