package us.lsi.centro;

import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Matriculas {

	public static Matriculas gestorDeMatriculas = null;
	
	public static Matriculas of() {
        return Matriculas.of("centro/matriculas.txt");
	}

	public static Matriculas of(String file) {
		if (Matriculas.gestorDeMatriculas == null)
			Matriculas.gestorDeMatriculas = new Matriculas(file);
		return Matriculas.gestorDeMatriculas;
	}

	public static Matriculas parse(String file) {
		Set<Matricula> matriculas = File2.streamDeFichero(file, "utf-8").map(ln -> Matricula.parse(ln))
				.collect(Collectors.toSet());
		Matriculas.gestorDeMatriculas = new Matriculas(matriculas);
		return Matriculas.gestorDeMatriculas;
	}

	public static Matriculas of(Set<Matricula> matriculas) {
		return new Matriculas(matriculas);
	}

	private Set<Matricula> matriculas;
	
	private Matriculas(Set<Matricula> matriculas) {
		super();
		this.matriculas = matriculas;
	}

	private Matriculas(String file) {
		super();
		this.matriculas = File2.streamDeFichero(file, "utf-8").map(ln -> Matricula.parse(ln))
				.collect(Collectors.toSet());;
	}

	public Integer size() {
		return this.matriculas.size();
	}

	public void addMatricula(Alumno alumno, Asignatura asignatura, Integer grupo) {
		Matricula m = Matricula.of(alumno.dni(), asignatura.ida(), grupo);
		this.matriculas.add(m);
	}

	public void removeMatricula(Alumno alumno, Asignatura asignatura, Integer grupo) {
		Matricula m = Matricula.of(alumno.dni(), asignatura.ida(), grupo);
		this.matriculas.remove(m);
	}

	public Set<Matricula> todas() {
		return this.matriculas;
	}

	public Matricula get(Integer index) {
		return this.matriculas.stream().toList().get(index);
	}

}
