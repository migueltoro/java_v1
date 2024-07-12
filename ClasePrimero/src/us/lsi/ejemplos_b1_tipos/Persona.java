package us.lsi.ejemplos_b1_tipos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import us.lsi.tools.Preconditions;

public class Persona implements Comparable<Persona> {
	
	public enum Horoscope {
	    ARIES,
	    TAURUS,
	    GEMINI,
	    CANCER,
	    LEO,
	    VIRGO,
	    LIBRA,
	    SCORPIO,
	    SAGITTARIUS,
	    CAPRICORN,
	    AQUARIUS,
	    PISCES
	}
	
	public static Persona of(String apellidos, String nombre, LocalDateTime fechaDeNacimiento, String dni,
			String telefono, Direccion direccion) {
		Preconditions.checkArgument(apellidos.strip().length() > 0, String.format("Los apellidos no pueden estar en blanco"));
        Preconditions.checkArgument(nombre.strip().length() > 0, String.format("El nombre no puede estar en blanco"));
        Preconditions.checkArgument(fechaDeNacimiento.isBefore(LocalDateTime.now()), String.format("La fecha debe estar en el pasado"));
        Preconditions.checkArgument(Persona.checkDni(dni), String.format("El dni no es correcto"));
		return new Persona(apellidos, nombre, fechaDeNacimiento, dni, telefono, direccion);
	}
	
	public static Persona parse(String text) {
    	DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	return Persona.parse(text,ft);
    }

//Plaza Rivero,Apolinar,72842943B,1999-01-17 19:14,+34664759382,Callejón Virginia Collado 21;Lugo;37687
	
	public static Persona parse(String text, DateTimeFormatter ft) {
		String[] partes = text.split(",");
		String dni = partes[2].strip();       
		LocalDateTime ld = LocalDateTime.parse(partes[3], ft);
		String telefono = partes[4].strip();
		Direccion direccion = Direccion.parse(partes[5].strip());
		return Persona.of(partes[0],partes[1],ld,dni,telefono,direccion);
	}
	
	public static Persona parseList(List<String> partes) {
    	DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	return Persona.parseList(partes,ft);
    }
	
	public static Persona parseList(List<String> partes, DateTimeFormatter ft) {
		String dni = partes.get(2).strip();       
		LocalDateTime ld = LocalDateTime.parse(partes.get(3), ft);
		String telefono = partes.get(4).strip();
		Direccion direccion = Direccion.parse(partes.get(5).strip());
		return Persona.of(partes.get(0).strip(),partes.get(1).strip(),ld,dni,telefono,direccion);
	}
	
	private String apellidos;
	private String nombre;
	private LocalDateTime fechaDeNacimiento;
	private String dni;
	private String telefono;
	private Direccion direccion;
	
	protected Persona(String apellidos, String nombre, LocalDateTime fechaDeNacimiento, String dni, String telefono,
			Direccion direccion) {
		super();
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.dni = dni;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	private static Boolean checkDni(String text) {
        List<Character> ls = Arrays.asList('T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E');
        String pn = text.substring(0,text.length()-1);
        Character lt = text.charAt(text.length()-1);
        Integer n = Integer.parseInt(pn) % 23;
        return ls.get(n) == lt;
    }
	
	public String apellidos() {
		return apellidos;
	}

	public String nombre() {
		return nombre;
	}

	public LocalDateTime fechaDeNacimiento() {
		return fechaDeNacimiento;
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
		Period p = Period.between(this.fechaDeNacimiento.toLocalDate(), now.toLocalDate());
		return p.getYears();
	}

	public String nombreCompleto() {
		return String.format("%s %s", this.nombre(), this.apellidos());
	}

	public LocalDate siguienteCumple() {
		Integer edad = this.edad();
		LocalDateTime nc = this.fechaDeNacimiento().plusYears(edad + 1);
		return nc.toLocalDate();
	}
	
	public DayOfWeek diaSemanaSiguienteCumple() {
		return siguienteCumple().getDayOfWeek();
	}
	
	public Horoscope horoscopo() {
	    int month = this.fechaDeNacimiento().getMonthValue();
	    int day = this.fechaDeNacimiento().getDayOfMonth();

	    if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
	        return Horoscope.AQUARIUS;
	    } else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
	        return Horoscope.PISCES;
	    } else if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
	        return Horoscope.ARIES;
	    } else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
	        return Horoscope.TAURUS;
	    } else if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) {
	        return Horoscope.GEMINI;
	    } else if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) {
	        return Horoscope.CANCER;
	    } else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
	        return Horoscope.LEO;
	    } else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
	        return Horoscope.VIRGO;
	    } else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
	        return Horoscope.LIBRA;
	    } else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
	        return Horoscope.SCORPIO;
	    } else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
	        return Horoscope.SAGITTARIUS;
	    } else {
	        return Horoscope.CAPRICORN;
	    }
	}

	public String toString() {
		String fs = this.fechaDeNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hs = this.fechaDeNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
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
		return Objects.hash(apellidos, direccion, dni, fechaDeNacimiento, nombre, telefono);
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
				&& Objects.equals(dni, other.dni) && Objects.equals(fechaDeNacimiento, other.fechaDeNacimiento)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}

	public static void main(String[] args) {
		Persona p = Persona.parse("Casares Amador,Ramiro,00895902Y,2003-06-14 10:02,+34721510926,Ronda de Samanta Cobos 392;Málaga;29316");
		System.out.println(p);
		System.out.println(String.format("- La fecha de nacimiento de %s es %s",p.nombre(),
				p.fechaDeNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
	    System.out.println(String.format("- La edad de %s es %d", p.nombre(),p.edad()));
	    System.out.println(String.format("- Su próximo cumpleaños será un %s",p.diaSemanaSiguienteCumple()));
	}
	

}
