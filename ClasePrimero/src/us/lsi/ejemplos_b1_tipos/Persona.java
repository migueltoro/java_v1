package us.lsi.ejemplos_b1_tipos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import us.lsi.tools.Preconditions;

public class Persona implements Comparable<Persona> {
	
	public static Persona of(String apellidos, String nombre, LocalDateTime fechaNacimiento, String dni,
			String telefono, Direccion direccion) {
		Preconditions.checkArgument(Persona.dniCorrecto(dni),String.format("El dni %s no es correcto",dni));
		return new Persona(apellidos, nombre, fechaNacimiento, dni, telefono, direccion);
	}

//Plaza Rivero,Apolinar,72842943B,1999-01-17 19:14,+34664759382,Callejón Virginia Collado 21;Lugo;37687
	public static Persona parse(String text) {
		String[] partes = text.split(",");
		String dni = partes[2].strip();       
		LocalDateTime ld = LocalDateTime.parse(partes[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		String telefono = partes[4].strip();
		Direccion direccion = Direccion.parse(partes[5].strip());
		return Persona.of(partes[0],partes[1],ld,dni,telefono,direccion);
	}
	
	public static Persona parseList(List<String> partes) {
		String dni = partes.get(2).strip();       
		LocalDateTime ld = LocalDateTime.parse(partes.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		String telefono = partes.get(4).strip();
		Direccion direccion = Direccion.parse(partes.get(5).strip());
		return Persona.of(partes.get(0).strip(),partes.get(1).strip(),ld,dni,telefono,direccion);
	}
	
	private String apellidos;
	private String nombre;
	private LocalDateTime fechaNacimiento;
	private String dni;
	private String telefono;
	private Direccion direccion;
	
	protected Persona(String apellidos, String nombre, LocalDateTime fechaNacimiento, String dni, String telefono,
			Direccion direccion) {
		super();
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public String apellidos() {
		return apellidos;
	}

	public String nombre() {
		return nombre;
	}

	public LocalDateTime fechaNacimiento() {
		return fechaNacimiento;
	}

	public String dni() {
		return dni;
	}

	public String telefono() {
		return telefono;
	}

	public Direccion direccion() {
		return direccion;
	}

	public Integer edad() {
		LocalDateTime now = LocalDateTime.now();
		Period p = Period.between(this.fechaNacimiento.toLocalDate(), now.toLocalDate());
		return p.getYears();
	}

	public String nombreCompleto() {
		return String.format("%s %s", this.nombre(), this.apellidos());
	}

	public LocalDate siguienteCumple() {
		Integer edad = this.edad();
		LocalDateTime nc = this.fechaNacimiento().plusYears(edad + 1);
		return nc.toLocalDate();
	}

	public DayOfWeek diaSemanaSiguienteCumple() {
		return siguienteCumple().getDayOfWeek();
	}

	public String toString() {
		String fs = this.fechaNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hs = this.fechaNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		return String.format("%s, de %d, nacido el %s a las %s", this.nombreCompleto(), this.edad(), fs, hs);
	}

	@Override
	public int compareTo(Persona other) {
		int r = this.apellidos().compareTo(other.apellidos());
		if (r == 0) r = this.nombre().compareTo(other.nombre());
		return r;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, direccion, dni, fechaNacimiento, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(direccion, other.direccion)
				&& Objects.equals(dni, other.dni) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}
	
	private static Boolean dniCorrecto(String dni) {
		return null;
	}

	public static void main(String[] args) {
//		Locale.setDefault(new Locale("es", "Es"));
//		Persona p = Persona.of("Antonio","Ramirez",LocalDateTime.of(1980,Month.APRIL,14,14,20));
		Persona p2 = Persona.parse("Plaza Rivero,Apolinar,72842943B,1999-01-17 19:14,+34664759382,Callejón Virginia Collado 21;Lugo;37687");
		System.out.println(p2.dni());
//		System.out.println(p.edad());
//		System.out.println(p2);
//		System.out.println(p2.edad());
//		System.out.println(p2.diaSemanaSiguienteCumple());
//		System.out.println(p2.siguienteCumple().getDayOfMonth());
	}
	

}
