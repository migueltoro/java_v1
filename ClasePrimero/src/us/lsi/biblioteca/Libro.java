package us.lsi.biblioteca;


import java.time.LocalDate;

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
		Preconditions.checkArgument(ISBN.length() == 10 || ISBN.length() == 13,
				String.format("El ISBN debe tener 10 o 13 caracteres y tiene %d", ISBN.length()));
		
		return new Libro(ISBN, titulo, autor, numeroDePaginas, precio, fechaPublicacion, estimacionDeVentas);
	}

	public Boolean isBestSeller() {
		return this.estimacionDeVentas > LIMITE_BEST_SELLER;
	}
	
}
