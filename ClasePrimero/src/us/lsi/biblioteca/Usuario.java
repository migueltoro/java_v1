package us.lsi.biblioteca;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Usuario extends Persona {

	private LocalDate fechaAlta;
    
	private Usuario(String apellidos, String nombre, String dni,LocalDateTime fechaNacimiento,  
			String telefono,Direccion direccion, LocalDate fechaAlta) {
		super(apellidos, nombre, dni, fechaNacimiento, telefono,direccion);
	    this.fechaAlta = fechaAlta;	
	}

	public static Usuario ofPersona(Persona p,LocalDate fechaAlta) { 
        return new Usuario(p.getApellidos(),p.getNombre(),p.getDni(),p.getFechaNacimiento(),
        		p.getTelefono(),p.getDireccion(),fechaAlta);
	}
    
    public static Usuario parse(String text) { 
        String[] ls = text.split(",");
        LocalDate fechaAlta = LocalDate.parse(ls[ls.length-1].strip(),
        		DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Persona p = Persona.parseList(Arrays.asList(ls).subList(0,ls.length-1));
        return Usuario.ofPersona(p,fechaAlta);
    }

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
}
