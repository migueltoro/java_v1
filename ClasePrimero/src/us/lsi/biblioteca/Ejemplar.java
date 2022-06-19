package us.lsi.biblioteca;

import java.time.LocalDate;

public record Ejemplar(String isbn, Integer codigo, LocalDate fechaDeAdquisicion) {
	
	public static Ejemplar of(String isbn, Integer num, LocalDate fechaDeAdquisicion) {
		return new Ejemplar(isbn,num,fechaDeAdquisicion);
	}
}
