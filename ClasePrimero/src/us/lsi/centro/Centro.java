package us.lsi.centro;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Centro {

	public static Centro of() {
		return Centro.of("centro/alumnos.txt",
					"centro/profesores.txt",
					"centro/asignaturas.txt",
					"centro/matriculas.txt",
					"centro/asignaciones.txt");
	}
	
	public static Centro of(String root) {
		return Centro.of(root+"/"+"centro/alumnos.txt",
				root+"/"+"centro/profesores.txt",
				root+"/"+"centro/asignaturas.txt",
				root+"/"+"centro/matriculas.txt",
				root+"/"+"centro/asignaciones.txt");
	}

	public static Centro of(String falumnos, String fprofesores, String fasignaturas, String fmatriculas,
			String fasignaciones) {
		if (Centro.centro == null)
			Centro.centro = new Centro(falumnos, fprofesores, fasignaturas, fmatriculas,fasignaciones);
		return Centro.centro;
	}

	private static Centro centro = null;

	private Alumnos alumnos;
	private Profesores profesores;
	private Asignaturas asignaturas;
	private Matriculas matriculas;
	private Asignaciones asignaciones;
	private Grupos grupos;

	private Centro(String falumnos, String fprofesores, String fasignaturas, String fmatriculas,
			String fasignaciones) {
		super();
		this.alumnos = Alumnos.of(falumnos);
		this.profesores = Profesores.of(fprofesores);
		this.asignaturas = Asignaturas.of(fasignaturas);
		this.matriculas = Matriculas.of(fmatriculas);
		this.asignaciones = Asignaciones.of(fasignaciones);
		this.grupos = null;
	}

	public Alumnos alumnos() {
		return alumnos;
	}

	public Profesores profesores() {
		return profesores;
	}

	public Asignaturas asignaturas() {
		return asignaturas;
	}

	public Matriculas matriculas() {
		return matriculas;
	}

	public Asignaciones asignaciones() {
		return asignaciones;
	}

	public Grupos grupos() {
		if (this.grupos == null)
			this.grupos = Grupos.of(this);
		return grupos;
	}

	public Map<Grupo, Set<Profesor>> profesoresDeGrupo() {
		return null;
	}

	public Set<Profesor> profesoresDeGrupo(Integer ida, Integer idg) {
		return null;
	}

	public Set<Profesor> profesores(Alumno a) {
		return null;
	}

	public Set<Profesor> profesores(Alumno a, Integer edad) {
		return null;
	}
	
	public Map<String,List<String>> alumnosDeProfesores() {
		return null;
	}
	
	public Map<String,Integer> numAlumnosDeProfesor() {
		return null;
	}

	public Map<Integer, Integer> numProfesoresPorEdad() {
		return null;
	}

	public Map<Integer, Integer> numGruposPorAsignatura() {
		return null;

	}

	public List<String> nombresAlumnosTop(Integer n) {
		return null;
	}

	public Set<Asignatura> obtenerAsignaturasComunes(List<Alumno> alumnos) {		
		return null;
	}

	public Map<Integer, Double> porcentajeAlumnosPorEdad() {
		return null;
	}


	public static void main(String[] args) {
		Centro c = Centro.of();
		System.out.println(String.format("- Hay %d alumnos en el centro", c.alumnos().size()));
		Alumno a = c.alumnos().todos().stream().limit(1).toList().get(0);
		System.out.println(a);
	}

}
