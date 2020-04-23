package us.lsi.biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import us.lsi.tools.Preconditions;

public class Biblioteca {
	
	public static Biblioteca of(String nombre, String codigPostal, String email) {
		return new Biblioteca(nombre, codigPostal, email);
	}


	private String nombre;
	private String codigoPostal;
	private String email; 
	private List<Libro> libros;
	
	
	private Biblioteca(String nombre, String codigoPostal, String email) {
		super();
		Preconditions.checkNotNull(nombre);
		Preconditions.checkArgument(compruebaCodigoPostal(codigoPostal),String.format("El codigo postal debe contener 5 dígitos y es %s",codigoPostal));
		Preconditions.checkArgument(compruebaEmail(email),String.format("El email debe tener un solo @ y un solo . y es %s",email));
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.libros = new ArrayList<>();
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

	public List<Libro> getLibros() {
		return libros;
	}
	
	public void añadeLibro(Libro libro) {
		if(libro != null)
			this.libros.add(libro);
	}
	
	public void eliminaLibro(Libro libro) {
//		Preconditions.checkArgument(libros.contains(libro),String.format("El libro %s no existe en la bilbioteca",libro));
//		this.libros.remove(libro);
		if(!libros.contains(libro)) {
			throw new IllegalArgumentException(String.format("El libro %s no existe en la bilbioteca",libro));
		} else {
			this.libros.remove(libro);
		}
		
	}
	
	
	@Override
	public String toString() {
		return String.format("%s (%s)",this.nombre,this.codigoPostal);
	}
	
	

}
