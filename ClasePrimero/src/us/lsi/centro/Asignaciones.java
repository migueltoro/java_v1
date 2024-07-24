package us.lsi.centro;

import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Asignaciones {
	
	public static Asignaciones gestorDeAsignaciones = null;
	
	public static Asignaciones of() {
		return Asignaciones.of("centro/asignaciones.txt");
	}
	
	public static Asignaciones of(String file) {
		if (Asignaciones.gestorDeAsignaciones == null)
			Asignaciones.gestorDeAsignaciones = new Asignaciones(file);
        return Asignaciones.gestorDeAsignaciones;
	}
	
	public static Asignaciones of(Set<Asignacion> Asignaciones) {
		return new Asignaciones(Asignaciones);
	}

	private Set<Asignacion> asignaciones;
	
	private Asignaciones(Set<Asignacion> asignaciones) {
		super();
		this.asignaciones = asignaciones;
	}

	private Asignaciones(String file) {
		super();
		this.asignaciones = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Asignacion.parse(ln)).collect(Collectors.toSet());
	}
    
    public Integer size() { 
        return this.asignaciones.size();
    }
    
    public void addAsignacion(Profesor profesor,Asignatura asignatura,Integer grupo) {
        this.asignaciones.add(Asignacion.of(profesor.dni(),asignatura.ida(),grupo));
    }

	public Set<Asignacion> todas() {
		return asignaciones;
	}
	
	 public Asignacion get(Integer index) { 
	        return this.asignaciones.stream().toList().get(index);
	 }
    
}
