package us.lsi.ejemplos_b1;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public record Persona(String nombre, String apellidos, LocalDateTime fechaNacimiento) 
		implements Comparable<Persona> {
	
	public static Persona of(String nombre, String apellidos,LocalDateTime fechaNacimiento) {
		return new Persona(nombre,apellidos,fechaNacimiento);
	}
	
	public static Persona parse(String text) {
		String[] partes = text.split(",");
		String nc = partes[0];
		String[] partesNc = nc.split(" ");
		LocalDate ld = LocalDate.parse(partes[1],DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalTime lt = LocalTime.parse(partes[2],DateTimeFormatter.ofPattern("HH:mm"));
		return Persona.of(partesNc[0],partesNc[1],LocalDateTime.of(ld, lt));
	}
	
	public static Persona parseList(List<String> partes) {
		String nc = partes.get(0);
		String[] partesNc = nc.split(" ");
		LocalDate ld = LocalDate.parse(partes.get(1),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalTime lt = LocalTime.parse(partes.get(2),DateTimeFormatter.ofPattern("HH:mm"));
		return Persona.of(partesNc[0],partesNc[1],LocalDateTime.of(ld, lt));
	}
	
	public Integer edad() {
		LocalDateTime now = LocalDateTime.now();
		Period p = Period.between(fechaNacimiento.toLocalDate(),now.toLocalDate());
		return p.getYears();
	}
	
	public String nombreCompleto() {
		return String.format("%s %s",this.nombre(),this.apellidos());
	}
	
	public LocalDate siguienteCumple() {
		Integer edad = this.edad();
		LocalDateTime nc = this.fechaNacimiento().plusYears(edad+1);
		return nc.toLocalDate();
	}
	
	public DayOfWeek diaSemanaSiguienteCumple() {
		return siguienteCumple().getDayOfWeek();
	}
	
	public String toString() {
		String fs = fechaNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hs = fechaNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		return String.format("%s, de %d, nacido el %s a las %s",nombreCompleto(),edad(),fs,hs);
	}
	
	@Override
	public int compareTo(Persona other) {
		int r = this.apellidos().compareTo(other.apellidos());
		if(r==0) r = this.nombre().compareTo(other.nombre());
		return r;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("es", "Es"));
		Persona p = Persona.of("Antonio","Ramirez",LocalDateTime.of(1980,Month.APRIL,14,14,20));
		Persona p2 = Persona.parse("Juan Balmez,10-03-1991,02:31");
		System.out.println(p);
		System.out.println(p.edad());
		System.out.println(p2);
		System.out.println(p2.edad());
		System.out.println(p2.diaSemanaSiguienteCumple());
		System.out.println(p2.siguienteCumple().getDayOfMonth());
	}

	

	

}
