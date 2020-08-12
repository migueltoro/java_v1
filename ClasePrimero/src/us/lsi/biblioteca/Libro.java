package us.lsi.biblioteca;

import java.time.LocalDate;

import us.lsi.tools.Preconditions;

public record Libro(String ISBN, String titulo, String autor, Integer numeroDePaginas, LocalDate fechaDeAdquisicion,
		Double precio, Integer estimacionDeVentas, TipoPrestamo tipoDePrestamo) implements Comparable<Libro> {

	private static Integer LIMITE_BEST_SELLER = 500000;

	public static Libro of(String ISBN, String titulo, String autor, Integer numeroDePaginas,
			LocalDate fechaDeAdquisicion, Double precio, Integer estimacionDeVentas, TipoPrestamo tipoDePrestamo) {
		
		return new Libro(ISBN, titulo, autor, numeroDePaginas, fechaDeAdquisicion, precio, estimacionDeVentas,
				tipoDePrestamo);
	}
	
	public Libro {
		Preconditions.checkArgument(numeroDePaginas > 0,
				String.format("El número de páginas debe ser positivo y es %d", numeroDePaginas));
		Preconditions.checkArgument(precio > 0, String.format("El precio debe ser positivo y es %.2f", precio));
		Preconditions.checkArgument(estimacionDeVentas > 0,
				String.format("La estimacion de ventas debe ser positiva y es %d", estimacionDeVentas));
		Preconditions.checkArgument(ISBN.length() == 10 || ISBN.length() == 13,
				String.format("El ISBN debe tener 10 o 13 caracteres y tiene %d", ISBN.length()));
		Preconditions.checkArgument(fechaDeAdquisicion.isBefore(LocalDate.now()),
				String.format("La fecha debe ser anterior a hoy y s %s", fechaDeAdquisicion.toString()));
	}

	public Boolean isBestSeller() {
		return this.estimacionDeVentas > LIMITE_BEST_SELLER;
	}

	public Integer diasDePrestamo() {
		Integer r = null;
		switch (this.tipoDePrestamo) {
		case DIARIO:r = 1;break;
		case SEMANAL:r = 7;break;
		case MENSUAL:r = 30;break;
		default:break;
		}
		return r;
	}

	@Override
	public int compareTo(Libro libro) {
		return this.ISBN.compareTo(libro.ISBN());
	}
}
