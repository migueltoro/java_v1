package us.lsi.centro;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import us.lsi.tools.Preconditions;

public class Alumno extends Persona {
    
	private Double nota;

    private Alumno(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono,
			Direccion direccion, Double nota) {
		super(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
		this.nota = nota;
	}
	
    public Double nota() {
		return this.nota;
	}

	public static Alumno of(Persona p, Double nota) {
		Preconditions.checkArgument(0 <= nota && nota <= 14,
				String.format("La nota debe estar comprendida entre 0 y 14 y es %.2f", nota));
		return new Alumno(p.apellidos(), p.nombre(), p.dni(), p.fechaDeNacimiento(), p.telefono(),
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
