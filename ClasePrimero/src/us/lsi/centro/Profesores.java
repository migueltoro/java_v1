package us.lsi.centro;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Profesores {
	
	public static Profesores gestorDeProfesores = null;
	
	public static Profesores of() {
        return Profesores.of("centro/profesores.txt");
	}
	
	public static Profesores of(String file) {
		if (Profesores.gestorDeProfesores == null)
			Profesores.gestorDeProfesores = new Profesores(file);
        return Profesores.gestorDeProfesores;
	}
	
	public static Profesores parse(String file) {
		Set<Profesor> profesores = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Profesor.parse(ln)).collect(Collectors.toSet());    	
		Profesores.gestorDeProfesores = new Profesores(profesores);
		return Profesores.gestorDeProfesores;
	}
	
	public static Profesores of(Set<Profesor> profesores) {
		return new Profesores(profesores);
	}

	private Set<Profesor> profesores;
	private Map<String,Profesor> profesoresDni;
	
	private Profesores(Set<Profesor> profesores) {
		super();
		this.profesores = profesores;
		this.profesoresDni = this.profesores.stream().collect(Collectors.toMap(p -> p.dni(), p -> p));
	}

	private Profesores(String file) {
		super();
		this.profesores = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Profesor.parse(ln)).collect(Collectors.toSet()); ;
		this.profesoresDni = this.profesores.stream().collect(Collectors.toMap(p->p.dni(),p->p));
	}
    
    public Profesor profesor(String dni) { 
        return this.profesoresDni.get(dni);
    }
    
    public Set<Profesor> todos() {
		return profesores;
	}
    
    public Profesor get(Integer index) { 
        return this.profesores.stream().toList().get(index);
    }
    
    public Integer size() { 
        return this.profesores.size();
    }
    
    public void addProfesor(Profesor profesor) {
        this.profesores.add(profesor);
    }
    
    public void removeProfesor(Profesor profesor) {
        this.profesores.remove(profesor);
    }

}
