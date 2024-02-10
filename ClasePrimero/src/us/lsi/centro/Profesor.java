package us.lsi.centro;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import us.lsi.ejemplos_b1_tipos.Direccion;
import us.lsi.ejemplos_b1_tipos.Persona;

public class Profesor extends Persona {
    
	private Titulo titulo;
	
	// String apellidos, String nombre, LocalDateTime fechaDeNacimiento, String dni, String telefono,
	//Direccion direccion

    private Profesor(String apellidos, String nombre,  LocalDateTime fechaDeNacimiento, String dni, String telefono,
			Direccion direccion,Titulo titulo) {
		super(apellidos, nombre, fechaDeNacimiento, dni, telefono, direccion);
		this.titulo = titulo;
	}

	public static Profesor of(Persona p,Titulo titulo) { 
        return new Profesor(p.apellidos(),p.nombre(),p.fechaDeNacimiento(), p.dni(),
        		p.telefono(),p.direccion(),titulo);
    }
    
    public static Profesor parse(String text) {
        String[] partes = text.split(",");
        List<String> ls = Arrays.asList(partes);
        Titulo titulo = Titulo.valueOf(ls.get(ls.size()-1).strip());
        Persona p = Persona.parseList(ls.subList(0,ls.size()-1));
        return Profesor.of(p,titulo);
    }

    public Titulo getTitulo() {
		return titulo;
	}

	public String toString() {
        return super.toString() + String.format(" con titulo %s)",this.getTitulo());
    }
        		
 }
