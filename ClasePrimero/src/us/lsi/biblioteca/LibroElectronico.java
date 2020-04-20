package us.lsi.biblioteca;

import java.time.LocalDate;

public class LibroElectronico extends Libro {
	
	public static LibroElectronico of(String iSBN, String titulo, String autor, Integer numeroDePaginas,
			LocalDate fechaDeAdquisicion, Double precio, Integer estimacionDeVentas, TipoPrestamo tipoDePrestamo,
			FomatoLibroElectronico formato) {
		return new LibroElectronico(iSBN, titulo, autor, numeroDePaginas, fechaDeAdquisicion, precio,
				estimacionDeVentas, tipoDePrestamo, formato);
	}

	private FomatoLibroElectronico formato;

	private LibroElectronico(String iSBN, 
			String titulo,
			String autor, 
			Integer numeroDePaginas,
			LocalDate fechaDeAdquisicion, 
			Double precio, 
			Integer estimacionDeVentas, 
			TipoPrestamo tipoDePrestamo,
			FomatoLibroElectronico formato) {
		super(iSBN, titulo, autor, numeroDePaginas, fechaDeAdquisicion, precio, estimacionDeVentas, tipoDePrestamo);
		this.formato = formato;		
	}

	public FomatoLibroElectronico getFormato() {
		return formato;
	}
	
	@Override
	public String getISBN() {
		return super.getISBN()+"-e";
	}

	@Override
	public String toString() {
		return super.toString() + "-ISBN "+ this.getISBN();
	}
	

}
