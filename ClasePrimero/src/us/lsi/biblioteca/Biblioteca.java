package us.lsi.biblioteca;


import java.util.stream.IntStream;

import us.lsi.tools.Preconditions;

public class Biblioteca {
	
	public static Biblioteca of(String nombre, String codigoPostal, String email) {
		return Biblioteca.of("", nombre, codigoPostal, email);
	}
	
	public static Biblioteca of(String root, String nombre, String codigoPostal, String email) {
		return new Biblioteca(nombre, codigoPostal, email,
				Usuarios.of(root), 
				Libros.of(root), 
				Ejemplares.of(root), 
				Prestamos.of(root));
	}
	
	public static Biblioteca parse(String nombre, String codigoPostal, String email, String usuarios,
			String libros, String ejemplares, String prestamos) {
		return new Biblioteca(nombre, codigoPostal, email, 
				Usuarios.parse(usuarios), 
				Libros.parse(libros), 
				Ejemplares.parse(ejemplares), 
				Prestamos.parse(prestamos));
	}

	private String nombre;
	private String codigoPostal;
	private String email; 
	private Usuarios usuarios;
	private Libros libros;
	private Ejemplares ejemplares;
	private Prestamos prestamos;
	
	private Biblioteca(String nombre, String codigoPostal, String email, 
			Usuarios usuarios,
			Libros libros,
			Ejemplares ejemplares,
			Prestamos prestamos) {
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

	public String codigoPostal() {
		return codigoPostal;
	}

	public String email() {
		return email;
	}

	public String nombre() {
		return this.nombre;
	}

	public Usuarios usuarios() {
		return usuarios;
	}

	public Libros libros() {
		return this.libros;
	}
	
	public Ejemplares ejemplares() {
		return this.ejemplares;
	}
	
	public Prestamos prestamos() {
		return prestamos;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)",this.nombre,this.codigoPostal);
	}
}
