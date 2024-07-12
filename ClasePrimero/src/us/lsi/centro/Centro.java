package us.lsi.centro;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Centro {

	public static Centro of() {
		if (Centro.centro == null)
			Centro.centro = Centro.parse("centro/alumnos.txt",
					"centro/profesores.txt",
					"centro/asignaturas.txt",
					"centro/matriculas.txt",
					"centro/asignaciones.txt");
		return Centro.centro;
	}

	public static Centro parse(String alumnos, String profesores, String asignaturas, String matriculas,
			String asignaciones) {

		Centro.centro = new Centro(Alumnos.parse(alumnos), Profesores.parse(profesores), Asignaturas.parse(asignaturas),
				Matriculas.parse(matriculas), Asignaciones.parse(asignaciones));
		return Centro.centro;
	}

	public static Centro of(Alumnos alumnos, Profesores profesores, Asignaturas asignaturas, Matriculas matriculas,
			Asignaciones asignaciones) {
		Centro.centro = new Centro(alumnos, profesores, asignaturas, matriculas, asignaciones);
		return Centro.centro;
	}

	public static Centro centro = null;

	private Alumnos alumnos;
	private Profesores profesores;
	private Asignaturas asignaturas;
	private Matriculas matriculas;
	private Asignaciones asignaciones;
	private Grupos grupos;

	private Centro(Alumnos alumnos, Profesores profesores, Asignaturas asignaturas, Matriculas matriculas,
			Asignaciones asignaciones) {
		super();
		this.alumnos = alumnos;
		this.profesores = profesores;
		this.asignaturas = asignaturas;
		this.matriculas = matriculas;
		this.asignaciones = asignaciones;
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
	
	//// Examen


	public static void main(String[] args) {
		Centro c = Centro.of();
		System.out.println(String.format("- Hay %d alumnos en el centro", c.alumnos().size()));
		Alumno a = c.alumnos().todos().stream().limit(1).toList().get(0);
		System.out.println(c.profesores(a, 20).stream().map(p -> p.dni()).toList());
		System.out.println(c.numProfesoresPorEdad().entrySet().stream().map(x -> x.toString())
				.collect(Collectors.joining(",", "{", "}")));
		System.out.println("_____________________");
		System.out.println(c.numGruposPorAsignatura().entrySet().stream().map(x -> x.toString())
				.collect(Collectors.joining(",", "{", "}")));
		System.out.println(Grupo.of(4, 2));
		System.out.println(c.grupos().gruposSinProfesor());
		System.out.println(c.grupos().size());
		System.out.println(c.numAlumnosDeProfesor());
	}

}
