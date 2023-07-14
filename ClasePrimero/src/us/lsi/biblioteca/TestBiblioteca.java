package us.lsi.biblioteca;

public class TestBiblioteca {

	public static void main(String[] args) {
		
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		System.out.println(biblioteca);
		
		Ejemplar ej1 = biblioteca.ejemplares().get(0);
		Ejemplar ej2 = biblioteca.ejemplares().get(1);
		Usuario us1 = biblioteca.usuarios().get(0);
		Usuario us2 = biblioteca.usuarios().get(1);
		
//		Ejemplar ej5 = biblioteca.addEjemplar(libro4, LocalDate.of(2020, 3, 7));
		System.out.println(biblioteca.libros());
		System.out.println(biblioteca.ejemplares());
		System.out.println(biblioteca.libros().librosDeAutor("Juan Ruiz"));
		Prestamo p = biblioteca.prestamos().get(1);
		System.out.println(p.fechaEntrega());
		System.out.println(ej1);
		System.out.println(ej2);
		System.out.println(us1);
		System.out.println(us2);
		System.out.println(ej2);
		System.out.println(p);
	}

}
