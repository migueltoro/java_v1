package us.lsi.biblioteca;

import java.time.LocalDate;

public record Prestamo(Integer codigo, Ejemplar ejemplar, LocalDate fechaPrestamo, TipoPrestamo tipo) {
	
	public static Prestamo of(Integer codigo ,Ejemplar ejemplar, LocalDate fecha, TipoPrestamo tipo) {
		return new Prestamo(codigo,ejemplar, fecha, tipo);
	}
	
	public static Integer diasDePrestamo(TipoPrestamo tipo) {
		return switch (tipo) {
		case DIARIO -> 1;
		case SEMANAL -> 7;
		case MENSUAL -> 30;
		};
	}
	
	public LocalDate fechaEntrega() {
		return fechaPrestamo.plusDays(diasDePrestamo(tipo));
	}

}
