package us.lsi.centro;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.File2;
import us.lsi.tools.Pair;
import us.lsi.tools.Preconditions;
import us.lsi.tools.Stream2;

public class Centro {

	public static Centro of() {
		return Centro.of("");
	}

	public static Centro of(String root) {
		if (Centro.centro == null)
			Centro.centro = Centro.parse(File2.absolute_path("centro/alumnos.txt", root),
					File2.absolute_path("centro/profesores.txt", root),
					File2.absolute_path("centro/asignaturas.txt", root),
					File2.absolute_path("centro/matriculas.txt", root),
					File2.absolute_path("centro/asignaciones.txt", root));
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
		return this.asignaciones().todas().stream().collect(Collectors.groupingBy(a -> Grupo.of(a.ida(), a.idg()),
				Collectors.mapping(a -> this.profesores().profesor(a.dni()), Collectors.toSet())));
	}

	public Set<Profesor> profesoresDeGrupo(Integer ida, Integer idg) {
		return this.asignaciones().todas().stream().filter(a -> a.ida().equals(ida)).filter(a -> a.idg().equals(idg))
				.map(a -> this.profesores().profesor(a.dni())).collect(Collectors.toSet());
	}

	public Set<Profesor> profesores(Alumno a) {
		Set<Grupo> ga = this.matriculas().todas().stream().filter(m -> m.dni().equals(a.dni()))
				.map(m -> Grupo.of(m.ida(), m.idg())).collect(Collectors.toSet());
		return this.asignaciones().todas().stream().filter(ap -> ga.contains(Grupo.of(ap.ida(), ap.idg())))
				.map(ap -> this.profesores().profesor(ap.dni())).collect(Collectors.toSet());
	}

	public Set<Profesor> profesores(Alumno a, Integer edad) {
		Set<Grupo> ga = this.matriculas().todas().stream().filter(m -> m.dni().equals(a.dni()))
				.map(m -> Grupo.of(m.ida(), m.idg())).collect(Collectors.toSet());
		return this.asignaciones().todas().stream().filter(ap -> ga.contains(Grupo.of(ap.ida(), ap.idg())))
				.map(ap -> this.profesores().profesor(ap.dni())).filter(p -> p.edad() >= edad)
				.collect(Collectors.toSet());
	}
	
	public Map<String,List<String>> alumnosDeProfesores() {
		Stream<Asignacion> as = this.asignaciones().todas().stream();
		Stream<Matricula> ms = this.matriculas().todas().stream();
		Stream<Pair<String, String>> ppa = 
				Stream2.join(as,ms,a->a.grupo(),m->m.grupo())
					.map(p->Pair.of(p.first().dni(),p.second().dni()));
		return Stream2.groupingList(
				ppa,
				p->p.first(),
				p->p.second());
	}
	
	public Map<String,Integer> numAlumnosDeProfesor() {
		Stream<Asignacion> as = this.asignaciones().todas().stream();
		Stream<Matricula> ms = this.matriculas().todas().stream();
		Stream<Pair<String, String>> ppa = 
				Stream2.join(as,ms,a->a.grupo(),m->m.grupo())
					.map(p->Pair.of(p.first().dni(),p.second().dni()));
		return Stream2.groupingSize(ppa,p->p.first());
	}

	public Map<Integer, Integer> numProfesoresPorEdad() {
		return this.asignaciones().todas().stream().map(ap -> this.profesores().profesor(ap.dni()))
				.collect(Collectors.groupingBy(p -> p.edad(), Collectors.reducing(0, p -> 1, (x, y) -> x + y)));
	}

	public Map<Integer, Integer> numGruposPorAsignatura() {
		return this.matriculas().todas().stream().map(m -> Grupo.of(m.ida(), m.idg())).distinct()
				.collect(Collectors.groupingBy(g -> g.ida(), Collectors.reducing(0, g -> 1, (x, y) -> x + y)));

	}

	public List<String> nombresAlumnosTop(Integer n) {
		return this.alumnos().todos().stream().filter(a -> a.fechaDeNacimiento().getYear() > 2000)
				.sorted(Comparator.comparing(Alumno::nota).reversed()).limit(n).map(Alumno::nombre)
				.collect(Collectors.toList());
	}

	public Set<Asignatura> obtenerAsignaturasComunes(List<Alumno> alumnos) {
		Preconditions.checkArgument(alumnos.size() >= 2);
		Set<String> dnis = alumnos.stream().map(a -> a.dni()).collect(Collectors.toSet());
		Map<Alumno, Set<Asignatura>> as = this.matriculas().todas().stream().filter(m -> dnis.contains(m.dni()))
				.collect(Collectors.groupingBy(m1 -> this.alumnos().alumno(m1.dni()),
						Collectors.mapping(m2 -> this.asignaturas().asignatura(m2.ida()), Collectors.toSet())));
		Set<Asignatura> s = as.get(alumnos.get(0));
		as.keySet().stream().forEach(a -> s.retainAll(as.get(a)));
		return s;
	}

	public Map<Integer, Double> porcentajeAlumnosPorEdad() {
		Integer total = this.alumnos().size();
		return this.alumnos().todos().stream().collect(Collectors.groupingBy(a -> a.edad(),
				Collectors.collectingAndThen(Collectors.counting(), n -> n * 100.0 / total)));
	}
	
	

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
