package us.lsi.biblioteca;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import us.lsi.tools.Preconditions;

public record Libro(String isbn, String titulo, String autor, Integer numeroDePaginas, Double precio,
		LocalDate fechaPublicacion, Integer estimacionDeVentas) {
	
	private static Integer LIMITE_BEST_SELLER = 500000;

	public static Libro of(String ISBN, String titulo, String autor, Integer numeroDePaginas, Double precio,
			LocalDate fechaPublicacion, Integer estimacionDeVentas) {

		Preconditions.checkArgument(numeroDePaginas > 0,
				String.format("El numero de paginas debe ser positivo y es %d", numeroDePaginas));
		Preconditions.checkArgument(precio > 0, String.format("El precio debe ser positivo y es %.2f", precio));
		Preconditions.checkArgument(estimacionDeVentas > 0,
				String.format("La estimacion de ventas debe ser positiva y es %d", estimacionDeVentas));
		String ISBN2 = ISBN.replaceAll("-", "");
		Preconditions.checkArgument(ISBN2.length() == 10 || ISBN2.length() == 13,
				String.format("El ISBN -- %s -- debe tener 10 o 13 caracteres y tiene %d, ", ISBN2 ,ISBN2.length()));
		
		return new Libro(ISBN, titulo, autor, numeroDePaginas, precio, fechaPublicacion, estimacionDeVentas);
	}
	
	public static Libro parse(String text) {
		 String[] partes = text.split(",");
	     String isbn = partes[0];
		 String	titulo = partes[1];
		 String	autor = partes[2];
	     Integer numeroPaginas = Integer.parseInt(partes[3]);
		 Double	precio = Double.parseDouble(partes[4]);
	     LocalDate fechaPublicacion = LocalDate.parse(partes[5],DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 Integer estimacionVentas = Integer.parseInt(partes[6]);
	     return Libro.of(isbn, titulo, autor, numeroPaginas, precio, fechaPublicacion, estimacionVentas);
	}

	public Boolean isBestSeller() {
		return this.estimacionDeVentas > LIMITE_BEST_SELLER;
	}
	
}
