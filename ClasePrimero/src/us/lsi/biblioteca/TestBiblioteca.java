package us.lsi.biblioteca;

import java.time.LocalDate;

public class TestBiblioteca {

	public static void main(String[] args) {
		
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		System.out.println(biblioteca);
		Libro libro1 = Libro.of("9780385536516", "El reino de las tinieblas", "Juan Ruiz", 125, 24.,
				LocalDate.of(2020, 2, 2), 200);
		Libro libro2 = Libro.of("9780385537716", "El pais de los lagos", "Manuel Zimerman", 700, 30.,
				LocalDate.of(2020, 2, 2), 600);
		Libro libro3 = Libro.of("9711385537716", "La nueva esperanza", "Adela Rios", 200, 30., LocalDate.of(2020, 3, 7),
				600);
		Libro libro4 = Libro.of("9711385537716", "La nueva esperanza", "Adela", 200, 30., LocalDate.of(2020, 3, 7),
				600);
		Ejemplar ej1 = biblioteca.addEjemplar(libro1, LocalDate.of(2020, 2, 2));
		Ejemplar ej11 = biblioteca.addEjemplar(libro1, LocalDate.of(2020, 2, 2));
		Ejemplar ej12 = biblioteca.addEjemplar(libro1, LocalDate.of(2020, 2, 2));
		Ejemplar ej13 = biblioteca.addEjemplar(libro1, LocalDate.of(2020, 2, 2));
		Ejemplar ej2 = biblioteca.addEjemplar(libro2, LocalDate.of(2000, 1, 14));
		Ejemplar ej3 = biblioteca.addEjemplar(libro3, LocalDate.of(2020, 3, 7));
//		Ejemplar ej5 = biblioteca.addEjemplar(libro4, LocalDate.of(2020, 3, 7));
		System.out.println(biblioteca.librosFormat());
		System.out.println(biblioteca.getEjemplares());
		System.out.println(biblioteca.getLibrosDeAutor("Juan Ruiz"));
		Prestamo p = biblioteca.prestamo(ej1, LocalDate.of(2022, 2, 2), TipoPrestamo.MENSUAL);
		System.out.println(p.fechaEntrega());
		System.out.println(ej1);
		System.out.println(ej11);
		System.out.println(ej12);
		System.out.println(ej13);
		System.out.println(ej2);
		System.out.println(ej3);
	}

}
