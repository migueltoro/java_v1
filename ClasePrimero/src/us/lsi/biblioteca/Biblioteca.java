package us.lsi.biblioteca;


import java.util.stream.IntStream;

import us.lsi.tools.Preconditions;

public class Biblioteca {
	
	public static Biblioteca of(String root) {
		return of("Reina Mercedes", "41012", "lsi@us.es", 
				root+"/"+"biblioteca/usuarios.txt", 
				root+"/"+"biblioteca/libros.txt", 
				root+"/"+"biblioteca/ejemplares.txt", 
				root+"/"+"biblioteca/prestamos.txt");
	}
	
	public static Biblioteca of(String nombre, String codigoPostal, String email) {
		return of(nombre, codigoPostal, email, "biblioteca/usuarios.txt", "biblioteca/libros.txt", "biblioteca/ejemplares.txt", "biblioteca/prestamos.txt");
	}

	public static Biblioteca of(String nombre, String codigoPostal, String email, String fusuarios, String flibros,
			String fejemplares, String fprestamos) {
		return new Biblioteca(nombre, codigoPostal, email, fusuarios, flibros, fejemplares, fprestamos);
	}

	private String nombre;
	private String codigoPostal;
	private String email; 
	private Usuarios usuarios;
	private Libros libros;
	private Ejemplares ejemplares;
	private Prestamos prestamos;
	
	private Biblioteca(String nombre, String codigoPostal, String email, 
			String fusuarios,
			String flibros, String fejemplares, String fprestamos) {
		super();
		Preconditions.checkNotNull(nombre);
		Preconditions.checkArgument(compruebaCodigoPostal(codigoPostal),String.format("El codigo postal debe contener 5 d�gitos y es %s",codigoPostal));
		Preconditions.checkArgument(compruebaEmail(email),String.format("El email debe tener un solo @ y un solo . y es %s",email));
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.usuarios = Usuarios.of(fusuarios);  
		this.libros = Libros.of(flibros);
		this.ejemplares = Ejemplares.of(fejemplares);
		this.prestamos = Prestamos.of(fprestamos);		
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
