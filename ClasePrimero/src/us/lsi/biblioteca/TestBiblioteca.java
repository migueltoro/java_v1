package us.lsi.biblioteca;

import java.time.LocalDate;

import us.lsi.biblioteca.Prestamo.TipoPrestamo;

public class TestBiblioteca {

	public static void main(String[] args) {
		
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		System.out.println(biblioteca);
		
		Ejemplar ej1 = biblioteca.getEjemplares().get(0);
		Ejemplar ej2 = biblioteca.getEjemplares().get(1);
		Usuario us1 = biblioteca.getUsuarios().get(0);
		Usuario us2 = biblioteca.getUsuarios().get(1);
		
//		Ejemplar ej5 = biblioteca.addEjemplar(libro4, LocalDate.of(2020, 3, 7));
		System.out.println(biblioteca.librosFormat());
		System.out.println(biblioteca.getEjemplares());
		System.out.println(biblioteca.getLibrosDeAutor("Juan Ruiz"));
		Prestamo p = biblioteca.prestamo(ej1,us1,LocalDate.of(2022, 2, 2), TipoPrestamo.MENSUAL);
		System.out.println(p.fechaEntrega());
		System.out.println(ej1);
		System.out.println(ej2);
		System.out.println(us1);
		System.out.println(us2);
		System.out.println(ej2);
		System.out.println(p);
	}

}
