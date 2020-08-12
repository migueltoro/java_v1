package us.lsi.biblioteca;

import java.time.LocalDate;

public class TestBiblioteca {

	public static void main(String[] args) {
		
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		System.out.println(biblioteca);
		Libro libro1 = Libro.of("9780385536516","El reino de las tinieblas", "Juan Ruiz",125, 
				LocalDate.of(2020, 2, 2), 24.,200,TipoPrestamo.SEMANAL);
		System.out.println(libro1);
		Libro libro2 = Libro.of("9780385537716","El país de los lagos", "Manuel Zimerman",700, 
				LocalDate.of(2000, 1, 14), 30.,600,TipoPrestamo.MENSUAL);
		System.out.println(libro2);
		Libro libro3 = Libro.of("9711385537716","La nueva esperanza", "Adela Ríos",200, 
				LocalDate.of(2020, 3, 7), 30.,600,TipoPrestamo.SEMANAL);
		System.out.println(libro3);
//		LibroElectronico libro4 = LibroElectronico.of("9711385667716","La nueva sociedad", "Fermin Cuesta",200, 
//				LocalDate.of(2020, 2, 4), 30.,600,TipoPrestamo.SEMANAL,FomatoLibroElectronico.PDF);			
//		System.out.println(libro4);
//		AudioLibro libro5 = 
//				AudioLibro.of("9711385667716","La nueva sociedad", "Fermin Cuesta",200, 
//				LocalDate.of(2020, 3, 4), 30.,600,TipoPrestamo.SEMANAL,FormatoAudio.MP3, "http: wwww.lsi.us.es", 20);			
//		System.out.println(libro5);
		biblioteca.añadeLibro(libro1);
		biblioteca.añadeLibro(libro2);
		biblioteca.añadeLibro(libro3);
//		biblioteca.añadeLibro(libro4);
//		biblioteca.añadeLibro(libro5);
		System.out.println(biblioteca.getLibros());
	}

}
