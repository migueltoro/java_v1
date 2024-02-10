package us.lsi.biblioteca;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import us.lsi.ejemplos_b1_tipos.Direccion;
import us.lsi.ejemplos_b1_tipos.Persona;

public class Usuario extends Persona {

	private LocalDate fechaAlta;
    
	private Usuario(String apellidos, String nombre, LocalDateTime fechaNacimiento,  String dni,
			String telefono,Direccion direccion, LocalDate fechaAlta) {
		super(apellidos, nombre, fechaNacimiento, dni, telefono,direccion);
	    this.fechaAlta = fechaAlta;	
	}

	public static Usuario of(Persona p,LocalDate fechaAlta) { 
        return new Usuario(p.apellidos(),p.nombre(),p.fechaDeNacimiento(),p.dni(),
        		p.telefono(),p.direccion(),fechaAlta);
	}
    
    public static Usuario parse(String text) { 
        String[] ls = text.split(",");
        LocalDate fechaAlta = LocalDate.parse(ls[ls.length-1].strip(),
        		DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Persona p = Persona.parseList(Arrays.asList(ls).subList(0,ls.length-1));
        return Usuario.of(p,fechaAlta);
    }

	public LocalDate fechaAlta() {
		return fechaAlta;
	}
}
