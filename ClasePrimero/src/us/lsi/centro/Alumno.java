package us.lsi.centro;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import us.lsi.ejemplos_b1_tipos.Direccion;
import us.lsi.ejemplos_b1_tipos.Persona;
import us.lsi.tools.Preconditions;

public class Alumno extends Persona {
    
	private Double nota;
	
	// String apellidos, String nombre, LocalDateTime fechaDeNacimiento, String dni, String telefono,
	//Direccion direccion

    private Alumno(String apellidos, String nombre, LocalDateTime fechaDeNacimiento, String dni, String telefono,
			Direccion direccion, Double nota) {
		super(apellidos, nombre, fechaDeNacimiento, dni, telefono, direccion);
		this.nota = nota;
	}
	
    public Double nota() {
		return this.nota;
	}

	public static Alumno of(Persona p, Double nota) {
		Preconditions.checkArgument(0 <= nota && nota <= 14,
				String.format("La nota debe estar comprendida entre 0 y 14 y es %.2f", nota));
		return new Alumno(p.apellidos(), p.nombre(), p.fechaDeNacimiento(), p.dni(), p.telefono(),
				p.direccion(), nota);
	}
    
    public static Alumno parse(String text) {
        List<String> ls = Arrays.asList(text.split(","));
        Double nota = Double.parseDouble(ls.get(ls.size()-1));
        Persona p = Persona.parseList(ls.subList(0, ls.size()-1));
        return Alumno.of(p,nota);
    }
        		
    
    public String toString() {
        return super.toString() + String.format(" con nota de entrada %.2f",this.nota());
    }   		
}
