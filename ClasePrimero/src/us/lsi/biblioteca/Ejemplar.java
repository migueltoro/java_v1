package us.lsi.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Ejemplar(String isbn, Integer codigo, LocalDate fechaDeAdquisicion) {
	
	public static Ejemplar of(String isbn, Integer num, LocalDate fechaDeAdquisicion) {
		return new Ejemplar(isbn,num,fechaDeAdquisicion);
	}
	
	public static Ejemplar parse(String text) {
	    String[] partes = text.split(",");
	    String isbn = partes[0];
	    Integer codigo = Integer.parseInt(partes[1]);
	    LocalDate fecha_adquision = LocalDate.parse(partes[2],DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    return Ejemplar.of(isbn,codigo,fecha_adquision);
	}
}
