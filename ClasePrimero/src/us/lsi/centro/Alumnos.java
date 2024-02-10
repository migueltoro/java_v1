package us.lsi.centro;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Alumnos {

	private static Alumnos gestorDeAlumnos = null;
	
	public static Alumnos of() {
		return Alumnos.of("");
	}
	
	public static Alumnos of(String root) {
		if (Alumnos.gestorDeAlumnos == null)
			Alumnos.gestorDeAlumnos = 
				Alumnos.parse(File2.absolute_path("centro/alumnos.txt",root));
        return Alumnos.gestorDeAlumnos;
	}
	
	public static Alumnos parse(String file) {
		Set<Alumno> alumnos = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Alumno.parse(ln)).collect(Collectors.toSet()); 
		Alumnos gestorDeAlumnos = new Alumnos(alumnos);
		return gestorDeAlumnos;
	}

	public static Alumnos of(Set<Alumno> alumnos) {
		return new Alumnos(alumnos);
	}

	private Set<Alumno> alumnos;
	private Map<String, Alumno> alumnosDni;

	private Alumnos(Set<Alumno> alumnos) {
		super();
		this.alumnos = alumnos;
		this.alumnosDni = this.alumnos.stream().collect(Collectors.toMap(a->a.dni(),a->a));;
	}

	public Alumno alumno(String dni) {
		return this.alumnosDni.get(dni);
	}
	
	public Alumno get(Integer index) { 
	        return this.alumnos.stream().toList().get(index);
	}

	public Set<Alumno> todos() {
		return alumnos;
	}

	public Integer size() {
		return this.alumnos.size();
	}
}
