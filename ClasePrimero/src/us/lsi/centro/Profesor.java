package us.lsi.centro;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Profesor extends Persona {
    
	private Titulo titulo;

    private Profesor(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono,
			Direccion direccion,Titulo titulo) {
		super(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
		this.titulo = titulo;
	}

	public static Profesor of(Persona p,Titulo titulo) { 
        return new Profesor(p.apellidos(),p.nombre(),p.dni(),p.fechaDeNacimiento(),
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
