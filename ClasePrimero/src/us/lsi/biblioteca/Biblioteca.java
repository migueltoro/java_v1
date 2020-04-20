package us.lsi.biblioteca;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tools.Preconditions;

public class Biblioteca {
	
	public static Biblioteca of(String nombre, String codigPostal, String email) {
		return new Biblioteca(nombre, codigPostal, email);
	}


	private String nombre;
	private String codigPostal;
	private String email; 
	private List<Libro> libros;
	
	
	private Biblioteca(String nombre, String codigPostal, String email) {
		super();
		Preconditions.checkNotNull(nombre);
		Preconditions.checkArgument(compruebaCodigoPostal(codigPostal),String.format("El codigo postal debe contener 5 dígitos y es %s",codigPostal));
		Preconditions.checkArgument(compruebaEmail(email),String.format("El email debe tener un solo @ y un solo . y es %s",email));
		this.nombre = nombre;
		this.codigPostal = codigPostal;
		this.email = email;
		this.libros = new ArrayList<>();
	}

	private Boolean compruebaEmail(String email) {
		return true;
	}

	private Boolean compruebaCodigoPostal(String codigoPostal) {
		return true;
	}

	public String getCodigPostal() {
		return codigPostal;
	}


	public void setCodigPostal(String codigPostal) {
		this.codigPostal = codigPostal;
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
		return String.format("%s (%s)",this.nombre,this.codigPostal);
	}
	
	

}
