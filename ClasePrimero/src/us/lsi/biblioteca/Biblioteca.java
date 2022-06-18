package us.lsi.biblioteca;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.Preconditions;

public class Biblioteca {
	
	public static Biblioteca of(String nombre, String codigPostal, String email) {
		return new Biblioteca(nombre, codigPostal, email);
	}

	private String nombre;
	private String codigoPostal;
	private String email; 
	private Map<String,Libro> libros;
	private Map<String,Integer> numCopias;
	
	
	private Biblioteca(String nombre, String codigoPostal, String email) {
		super();
		Preconditions.checkNotNull(nombre);
		Preconditions.checkArgument(compruebaCodigoPostal(codigoPostal),String.format("El codigo postal debe contener 5 d�gitos y es %s",codigoPostal));
		Preconditions.checkArgument(compruebaEmail(email),String.format("El email debe tener un solo @ y un solo . y es %s",email));
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.libros = new HashMap<>();
		this.numCopias = new HashMap<>();
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

	public void setCodigPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public Set<Libro> getLibros() {
		return libros.values().stream().collect(Collectors.toSet());
	}
	
	public void add(Libro libro) {
		Preconditions.checkNotNull(libro);
		if(this.libros.containsKey(libro.ISBN())) {
			this.numCopias.put(libro.ISBN(),this.numCopias.get(libro.ISBN())+1);
		} else {
			this.libros.put(libro.ISBN(),libro);
			this.numCopias.put(libro.ISBN(),1);
		}
	}
	
	public void eliminaLibro(Libro libro) {	
		this.libros.remove(libro.ISBN());
	}
	
	public void eliminaCopiaLibro(Libro libro) {
		this.libros.remove(libro.ISBN());
	}
	
	public Set<Libro> librosDeAutor(String autor) {
		return this.libros.values().stream()
				.filter(lb->lb.autor().equals(autor))
				.collect(Collectors.toSet());
	}
	
	
	
	@Override
	public String toString() {
		return String.format("%s (%s)",this.nombre,this.codigoPostal);
	}
	
	

}
