package us.lsi.biblioteca;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.Par;
import us.lsi.tools.Preconditions;

public class Biblioteca {
	
	public static Biblioteca of(String nombre, String codigoPostal, String email) {
		return new Biblioteca(nombre, codigoPostal, email);
	}

	private String nombre;
	private String codigoPostal;
	private String email; 
	private Map<String,Libro> libros;
	private Map<Par<String,Integer>,Ejemplar> ejemplares;
	private Map<Integer,Prestamo> prestamos;
	
	
	private Biblioteca(String nombre, String codigoPostal, String email) {
		super();
		Preconditions.checkNotNull(nombre);
		Preconditions.checkArgument(compruebaCodigoPostal(codigoPostal),String.format("El codigo postal debe contener 5 d�gitos y es %s",codigoPostal));
		Preconditions.checkArgument(compruebaEmail(email),String.format("El email debe tener un solo @ y un solo . y es %s",email));
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.libros = new HashMap<>();
		this.ejemplares = new HashMap<>();
		this.prestamos = new HashMap<>();
	}

	private Boolean compruebaEmail(String email) {
		Boolean r1 = IntStream.range(0,email.length()).map(i->email.charAt(i)).filter(c->c == '@').count() == 1;
		Boolean r2 = IntStream.range(0,email.length()).map(i->email.charAt(i)).filter(c->c == '.').count() == 1;
		return r1 && r2;
	}

	private Boolean compruebaCodigoPostal(String codigoPostal) {
		Boolean r1 = codigoPostal.length() == 5;
		Boolean r2 = IntStream.range(0,codigoPostal.length()).map(i->codigoPostal.charAt(i)).allMatch(c->Character.isDigit(c));
		return r1 && r2;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public String getEmail() {
		return email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Set<Libro> getLibros() {
		return this.libros.values().stream().collect(Collectors.toSet());
	}
	
	public Set<Ejemplar> getEjemplares() {
		return this.ejemplares.values().stream().collect(Collectors.toSet());
	}
	
	public Integer getNumEjemplares(Libro libro) {
		return (int) this.ejemplares.keySet().stream().filter(p->p.a().equals(libro.isbn()))
				.count();
	}
	
	public Set<Ejemplar> getEjemplares(Libro libro) {
		return this.ejemplares.values().stream().filter(ej->ej.isbn().equals(libro.isbn()))
				.collect(Collectors.toSet());
	}
	
	private Integer codigoPrestamoLibre() {
		return Stream.iterate(1,n->n+1)
				.filter(i->!this.prestamos.keySet().contains(i))			
				.findFirst()
				.get();
	}
	
	private Integer codigoEjemplarLibre(Libro libro) {
		return Stream.iterate(1,n->n+1)
				.map(i->Par.of(libro.isbn(),i))
				.filter(i->!this.ejemplares.keySet().contains(i))
				.findFirst()
				.get()
				.b();
	}
	
	
	public Prestamo prestamo(Ejemplar ejemplar, LocalDate fecha, TipoPrestamo tipo) {
		Integer codigo = codigoPrestamoLibre();
		Prestamo p = Prestamo.of(codigo,ejemplar, fecha, tipo);
		this.prestamos.put(codigo,p);
		return p;
	}
	
	public Libro devuelveLibro(Prestamo p) {
		this.prestamos.remove(p.codigo());
		return this.libros.get(p.ejemplar().isbn());
	}
	
	public Ejemplar addEjemplar(Libro libro, LocalDate fecha) {
		Preconditions.checkNotNull(libro);
		if (!this.libros.keySet().contains(libro.isbn()))
			this.libros.put(libro.isbn(), libro);
		Preconditions.checkArgument(fecha.isBefore(LocalDate.now()),
				String.format("La fecha debe ser anterior a hoy y s %s", fecha.toString()));
		Integer n = this.codigoEjemplarLibre(libro);
		Ejemplar ejemplar = Ejemplar.of(libro.isbn(), n, fecha);
		Preconditions.checkArgument(this.libros.get(libro.isbn()).equals(libro),
				String.format("Los datos del libro con ISBN = %s no corresponden con el ISBN",libro.isbn()));
		this.ejemplares.put(Par.of(libro.isbn(), n), ejemplar);
		return ejemplar;
	}
	
	public Boolean algunEjemplarPrestado(Libro libro) {
		return this.prestamos.values().stream()
		.anyMatch(p->this.libros.get(p.ejemplar().isbn()).equals(libro));
	}
	
	public Libro eliminaLibro(Libro libro) {	
		this.libros.remove(libro.isbn());
		Preconditions.checkArgument(!this.algunEjemplarPrestado(libro));
		Set<Par<String,Integer>> s = 
				this.getEjemplares(libro).stream().map(ej->Par.of(ej.isbn(),ej.codigo()))
				.collect(Collectors.toSet());
		s.stream().forEach(p->this.ejemplares.remove(p));
		return libro;
	}
	
	public Ejemplar eliminaEjemplar(Ejemplar ejemplar) {	
		this.ejemplares.remove(Par.of(ejemplar.isbn(), ejemplar.codigo()));
		return ejemplar;
	}
	
	public Set<Libro> getLibrosDeAutor(String autor) {
		return this.libros.values().stream()
				.filter(lb->lb.autor().equals(autor))
				.collect(Collectors.toSet());
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)",this.nombre,this.codigoPostal);
	}
	
	private String ejemplaresFormat(Libro lb) {
		return this.ejemplares.values().stream().filter(ej->ej.isbn().equals(lb.isbn()))
				.map(ej -> ej.fechaDeAdquisicion().toString())
				.collect(Collectors.joining(","));
	}
	
	public String librosFormat() {
		return this.getLibros().stream()
				.map(lb -> String.format("%-30s  %3d  %s", lb.titulo(), getNumEjemplares(lb), ejemplaresFormat(lb)))
				.collect(Collectors.joining("\n"));
	}

}
