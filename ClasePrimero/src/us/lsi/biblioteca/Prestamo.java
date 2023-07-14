package us.lsi.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Prestamo(Integer codigo, String isbn, Integer codigoEjemplar, String dni,
		LocalDate fechaPrestamo,TipoPrestamo tipo) {
	
	public static enum TipoPrestamo{DIARIO,SEMANAL,MENSUAL}
	
	private static Integer np = 0;
	
	public static Prestamo of(Integer codigo,String isbn,Integer codigoEjemplar,String dni,
			LocalDate fecha,TipoPrestamo tipo) {
		return new Prestamo(codigo,isbn,codigoEjemplar,dni,fecha, tipo);
	}
	
	public static Prestamo parse(String text) {
		String[] ls = text.split(",");
		LocalDate  fecha = LocalDate.parse(ls[3],DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Integer n = np;
		np++;
		return Prestamo.of(n,ls[0],Integer.parseInt(ls[1]),ls[2],fecha,TipoPrestamo.valueOf(ls[4]));
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
