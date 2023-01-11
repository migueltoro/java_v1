package us.lsi.biblioteca;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Persona implements Comparable<Persona> {
	
	private String apellidos;
	private String nombre; 
	private LocalDateTime fechaNacimiento;
	private String dni;
	private String telefono;
	private Direccion direccion;
	
	public static Persona of(String apellidos, String nombre, String dni, LocalDateTime fechaNacimiento,
			String telefono, Direccion direccion) {
		return new Persona(apellidos, nombre, dni,fechaNacimiento, telefono, direccion);
	}
	
	public static Persona parse(String text) {
		String[] partes = text.split(",");
		String dni = partes[2].strip();       
		LocalDateTime ld = LocalDateTime.parse(partes[3], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		String telefono = partes[4].strip();
		Direccion direccion = Direccion.parse(partes[5].strip());
		return Persona.of(partes[0],partes[1],dni,ld,telefono,direccion);
	}
	
	public static Persona parseList(List<String> partes) {
		String dni = partes.get(2).strip();       
		LocalDateTime ld = LocalDateTime.parse(partes.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		String telefono = partes.get(4).strip();
		Direccion direccion = Direccion.parse(partes.get(5).strip());
		return Persona.of(partes.get(0).strip(),partes.get(1).strip(),dni,ld,telefono,direccion);
	}


	protected Persona(String apellidos, String nombre, String dni, LocalDateTime fechaNacimiento, String telefono,
			Direccion direccion) {
		super();
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public LocalDateTime getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	
	public Integer edad() {
		LocalDateTime now = LocalDateTime.now();
		Period p = Period.between(fechaNacimiento.toLocalDate(), now.toLocalDate());
		return p.getYears();
	}

	public String nombre_completo() {
		return String.format("%s %s", this.getNombre(), this.getApellidos());
	}

	public LocalDate siguienteCumple() {
		Integer edad = this.edad();
		LocalDateTime nc = this.getFechaNacimiento().plusYears(edad + 1);
		return nc.toLocalDate();
	}

	public DayOfWeek diaSemanaSiguienteCumple() {
		return siguienteCumple().getDayOfWeek();
	}

	public String toString() {
		String fs = this.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hs = this.getFechaNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		return String.format("%s, de %d, nacido el %s a las %s", nombre_completo(), edad(), fs, hs);
	}

	@Override
	public int compareTo(Persona other) {
		int r = this.getApellidos().compareTo(other.getApellidos());
		if (r == 0)
			r = this.getNombre().compareTo(other.getNombre());
		return r;
	}

	public static void main(String[] args) {
//		Locale.setDefault(new Locale("es", "Es"));
//		Persona p = Persona.of("Antonio","Ramirez",LocalDateTime.of(1980,Month.APRIL,14,14,20));
//		Persona p2 = Persona.parse("Juan Balmez,10-03-1991,02:31");
//		System.out.println(p);
//		System.out.println(p.edad());
//		System.out.println(p2);
//		System.out.println(p2.edad());
//		System.out.println(p2.diaSemanaSiguienteCumple());
//		System.out.println(p2.siguienteCumple().getDayOfMonth());
	}

}
