package us.lsi.centro;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import us.lsi.tools.Preconditions;

public class Persona implements Comparable<Persona>{
	
	    private String apellidos;
	    private String nombre;
	    private String dni;
	    private LocalDateTime fechaDeNacimiento;
	    private String telefono;
	    private Direccion direccion;
	    
	    protected Persona(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono,
				Direccion direccion) {
			super();
			this.apellidos = apellidos;
			this.nombre = nombre;
			this.dni = dni;
			this.fechaDeNacimiento = fechaDeNacimiento;
			this.telefono = telefono;
			this.direccion = direccion;
		}

		public static Persona of(String apellidos, String nombre, String dni, 
	    		LocalDateTime fechaDeNacimiento, String telefono, Direccion direccion) {
	        Preconditions.checkArgument(apellidos.strip().length() > 0, String.format("Los apellidos no pueden estar en blanco"));
	        Preconditions.checkArgument(nombre.strip().length() > 0, String.format("El nombre no puede estar en blanco"));
	        Preconditions.checkArgument(fechaDeNacimiento.isBefore(LocalDateTime.now()), String.format("La fecha debe estar en el pasado"));
	        Preconditions.checkArgument(Persona.checkDni(dni), String.format("El dni no es correcto"));
	        return new Persona(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
	    }
	    
	    public static Persona parse(String text) {
	    	DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    	return Persona.parse(text,ft);
	    }
	    
	    public static Persona parse(String text, DateTimeFormatter ft) {
	        String[] partes = text.split(",");
	        String apellidos =  partes[0].strip();
	        String nombre =  partes[1].strip();
	        String dni = partes[2].strip();
	        LocalDateTime fecha_de_nacimiento = LocalDateTime.parse(partes[3].strip(), ft);
	        String telefono = partes[4].strip();
	        Direccion direccion = Direccion.parse(partes[5].strip());
	        return Persona.of(apellidos, nombre, dni, fecha_de_nacimiento, telefono, direccion);
	    }
	    
	    public static Persona parseList(List<String> partes) {
	    	DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    	return Persona.parseList(partes,ft);
	    }
	    
	    public static Persona parseList(List<String> partes, DateTimeFormatter ft) {	        
	        String apellidos =  partes.get(0).strip();
	        String nombre =  partes.get(1).strip();
	        String dni = partes.get(2).strip();
	        LocalDateTime fechaDeNacimiento = LocalDateTime.parse(partes.get(3).strip(), ft);
	        String telefono = partes.get(4).strip();
	        Direccion direccion = Direccion.parse(partes.get(5).strip());
	        return Persona.of(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
	    }
	    
	    
	    private static Boolean checkDni(String text) {
	        List<Character> ls = Arrays.asList('T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E');
	        String pn = text.substring(0,text.length()-1);
	        Character lt = text.charAt(text.length()-1);
	        Integer n = Integer.parseInt(pn) % 23;
	        return ls.get(n) == lt;
	    }
	    
	    public String apellidos() {
			return this.apellidos;
		}

		public String nombre() {
			return this.nombre;
		}

		public String dni() {
			return dni;
		}

		public LocalDateTime fechaDeNacimiento() {
			return this.fechaDeNacimiento;
		}

		public String telefono() {
			return this.telefono;
		}

		public Direccion direccion() {
			return this.direccion;
		}

		public Integer edad() {
	        LocalDateTime nw = LocalDateTime.now();
	        return Period.between(this.fechaDeNacimiento.toLocalDate(),nw.toLocalDate()).getYears();
	    }
	   
		public LocalDate siguienteCumple() {
			Integer edad = this.edad();
			LocalDateTime nc = this.fechaDeNacimiento().plusYears(edad + 1);
			return nc.toLocalDate();
		}

		public DayOfWeek diaSemanaSiguienteCumple() {
			return this.siguienteCumple().getDayOfWeek();
		}
		
		public String nombreCompleto() {
			return String.format("%s %s",this.nombre(),this.apellidos());
		}
		
		@Override
		public int compareTo(Persona other) {
			int r = this.apellidos().compareTo(other.apellidos());
			if(r==0) r = this.nombre().compareTo(other.nombre());
			return r;
		}

		public String toString() {
			String fs = this.fechaDeNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String hs = this.fechaDeNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
			return String.format("%s, de %d, nacido el %s a las %s", this.nombreCompleto(), 
					this.edad(), fs, hs);
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
