package us.lsi.biblioteca;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.Par;
import us.lsi.tools.Preconditions;
import us.lsi.biblioteca.Prestamo.TipoPrestamo;
import us.lsi.tools.FileTools;

public class Biblioteca {
	
	public static Biblioteca of(String nombre, String codigoPostal, String email) {
		List<Usuario> usuarios = FileTools.streamFromFile("ficheros_biblioteca/usuarios.txt")
				.map(line->Usuario.parse(line)).toList();
		List<Libro> libros = FileTools.streamFromFile("ficheros_biblioteca/libros.txt")
				.map(line->Libro.parse(line)).toList(); 
		List<Ejemplar> ejemplares = FileTools.streamFromFile("ficheros_biblioteca/ejemplares.txt")
				.map(line->Ejemplar.parse(line)).toList();
		List<Prestamo> prestamos = FileTools.streamFromFile("ficheros_biblioteca/prestamos.txt")
				.map(line->Prestamo.parse(line)).toList();
		return new Biblioteca(nombre, codigoPostal, email, usuarios, libros, ejemplares, prestamos);
	}
	
	public static Biblioteca of(String nombre, String codigoPostal, String email, List<Usuario> usuarios,
			List<Libro> libros, List<Ejemplar> ejemplares, List<Prestamo> prestamos) {
		return new Biblioteca(nombre, codigoPostal, email, usuarios, libros, ejemplares, prestamos);
	}

	private String nombre;
	private String codigoPostal;
	private String email; 
	private List<Usuario> usuarios;
	private List<Libro> libros;
	private List<Ejemplar> ejemplares;
	private List<Prestamo> prestamos;
	private Map<String,Libro> libroId;
	private Map<Par<String,Integer>,Ejemplar> ejemplarId;
	private Map<Integer,Prestamo> prestamoId;
	
	
	private Biblioteca(String nombre, String codigoPostal, String email, 
			List<Usuario> usuarios,
			List<Libro> libros,
			List<Ejemplar> ejemplares,
			List<Prestamo> prestamos) {
		super();
		Preconditions.checkNotNull(nombre);
		Preconditions.checkArgument(compruebaCodigoPostal(codigoPostal),String.format("El codigo postal debe contener 5 d�gitos y es %s",codigoPostal));
		Preconditions.checkArgument(compruebaEmail(email),String.format("El email debe tener un solo @ y un solo . y es %s",email));
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.usuarios = usuarios;
		this.libros = libros;
		this.ejemplares = ejemplares;
		this.prestamos = prestamos;
		this.libroId = this.libros.stream().collect(Collectors.toMap(lb->lb.isbn(),lb->lb));
		this.ejemplarId = this.ejemplares.stream().collect(Collectors.toMap(ej->Par.of(ej.isbn(),ej.codigo()),ej->ej));
		this.prestamoId = this.prestamos.stream().collect(Collectors.toMap(pt->pt.codigo(),pt->pt));
		
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Libro> getLibros() {
		return this.libros;
	}
	
	public List<Ejemplar> getEjemplares() {
		return this.ejemplares;
	}
	
	public Integer getNumEjemplares(Libro libro) {
		return this.ejemplares.size();
	}
	
	public List<Ejemplar> getEjemplares(Libro libro) {
		return this.ejemplares.stream().filter(ej->ej.isbn().equals(libro.isbn())).toList();
	}
	
	public Integer codigoPrestamoLibre() {
		return Stream.iterate(1,n->n+1)
				.filter(i->!this.prestamoId.keySet().contains(i))			
				.findFirst()
				.get();
	}
	
	private Integer codigoEjemplarLibre(Libro libro) {
		return Stream.iterate(1,n->n+1)
				.map(i->Par.of(libro.isbn(),i))
				.filter(lb->!this.ejemplarId.keySet().contains(lb))
				.findFirst()
				.get()
				.b();
	}
	
	public Prestamo prestamo(Ejemplar ejemplar, Usuario usuario, LocalDate fecha, TipoPrestamo tipo) {
		Integer codigo = codigoPrestamoLibre();
		Prestamo p = Prestamo.of(codigo,ejemplar.isbn(),ejemplar.codigo(), usuario.getDni(), fecha, tipo);
		this.prestamoId.put(codigo,p);
		return p;
	}
	
	public Libro devuelveLibro(Prestamo p) {
		this.prestamos.remove(p);
		return this.libroId.get(p.isbn());
	}
	
	public Ejemplar addEjemplar(Libro libro, LocalDate fecha) {
		Preconditions.checkNotNull(libro);
		if (!this.libroId.keySet().contains(libro.isbn()))
			this.libroId.put(libro.isbn(), libro);
		Preconditions.checkArgument(fecha.isBefore(LocalDate.now()),
				String.format("La fecha debe ser anterior a hoy y s %s", fecha.toString()));
		Integer n = this.codigoEjemplarLibre(libro);
		Ejemplar ejemplar = Ejemplar.of(libro.isbn(), n, fecha);
		Preconditions.checkArgument(this.libroId.get(libro.isbn()).equals(libro),
				String.format("Los datos del libro con ISBN = %s no corresponden con el ISBN",libro.isbn()));
		this.ejemplarId.put(Par.of(libro.isbn(), n), ejemplar);
		return ejemplar;
	}
	
	public Boolean algunEjemplarPrestado(Libro libro) {
		return this.prestamoId.values().stream()
		.anyMatch(p->this.libroId.get(p.isbn()).equals(libro));
	}
	
	public Libro eliminaLibro(Libro libro) {
		this.libros.remove(libro);
		Preconditions.checkArgument(!this.algunEjemplarPrestado(libro));
		Set<Ejemplar> s = this.getEjemplares(libro).stream()
				.map(ej -> Par.of(ej.isbn(), ej.codigo()))
				.map(p -> this.ejemplarId.get(p))
				.collect(Collectors.toSet());
		s.stream().forEach(p -> this.ejemplares.remove(p));
		return libro;
	}
	
	public Ejemplar eliminaEjemplar(Ejemplar ejemplar) {	
		this.ejemplares.remove(ejemplar);
		return ejemplar;
	}
	
	public Set<Libro> getLibrosDeAutor(String autor) {
		return this.libros.stream()
				.filter(lb->lb.autor().equals(autor))
				.collect(Collectors.toSet());
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)",this.nombre,this.codigoPostal);
	}
	
	private String ejemplaresFormat(Libro lb) {
		return this.ejemplares.stream().filter(ej->ej.isbn().equals(lb.isbn()))
				.map(ej -> ej.fechaDeAdquisicion().toString())
				.collect(Collectors.joining(","));
	}
	
	public String librosFormat() {
		return this.getLibros().stream()
				.map(lb -> String.format("%-30s  %3d  %s", lb.titulo(), getNumEjemplares(lb), ejemplaresFormat(lb)))
				.collect(Collectors.joining("\n"));
	}

}
