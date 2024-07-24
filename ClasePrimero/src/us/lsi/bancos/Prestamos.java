package us.lsi.bancos;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Prestamos {

	private static Prestamos gestorDePrestamos = null;
	private Set<Prestamo> prestamos;
	private Map<Integer, Prestamo> prestamosNid;

	private Prestamos(String file) {
		super();
		this.prestamos = File2.streamDeFichero(file, "UTF-8")
				.map(ln -> Prestamo.parse(ln))
				.collect(Collectors.toSet());
		this.prestamosNid = this.prestamos.stream().collect(Collectors.toMap(p -> p.nid(), p -> p));
	}
	
	public static Prestamos of() {
		return Prestamos.of("bancos/prestamos.txt");
	}

	public static Prestamos of(String file) {
		if (Prestamos.gestorDePrestamos == null)
			Prestamos.gestorDePrestamos = new Prestamos(file);
		return Prestamos.gestorDePrestamos;
	}

	public Set<Prestamo> todos() {
		return this.prestamos;
	}

	public Optional<Prestamo> prestamoNid(Integer nid) {
		return Optional.ofNullable(this.prestamosNid.getOrDefault(nid, null));
	}

	public Integer size() {
		return this.prestamos.size();
	}

	public Prestamo prestamoIndex(Integer index) {
		return this.prestamos.stream().toList().get(index);
	}

	public String toString() {
		return this.prestamos.stream().map(p -> p.toString()).collect(Collectors.joining("\n\t", "Prestamos\n\t", ""));
	}

	public static void main(String[] args) {
		Prestamos prestamos = Prestamos.of();
		System.out.println(prestamos);
		System.out.println("______________");
		System.out.println(prestamos.prestamoNid(94));
		Optional<Prestamo> op = prestamos.prestamoNid(94);
		if (!op.isEmpty()) {
			Prestamo p = op.get();
			System.out.println(Personas.of().personaDni(p.dniEmpleado()));
			System.out.println(p.fechaComienzo());
			System.out.println(p.fechaVencimiento());
		}
	}
}
