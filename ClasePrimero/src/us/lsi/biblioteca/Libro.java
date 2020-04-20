package us.lsi.biblioteca;

import java.time.LocalDate;

import us.lsi.tools.Preconditions;

public class Libro implements Comparable<Libro> {

	public static Libro of(String iSBN, String titulo, String autor, Integer numeroDePaginas,
			LocalDate fechaDeAdquisicion, Double precio, Integer estimacionDeVentas, TipoPrestamo tipoDePrestamo) {
		return new Libro(iSBN, titulo, autor, numeroDePaginas, fechaDeAdquisicion, precio, estimacionDeVentas,
				tipoDePrestamo);
	}

	private String ISBN;
	private String titulo;
	private String autor;
	private Integer numeroDePaginas;
	private LocalDate fechaDeAdquisicion;
	private Double precio;
	private Integer estimacionDeVentas;
	private TipoPrestamo tipoDePrestamo;
	private static Integer LIMITE_BEST_SELLER = 500000;
	
	public  Libro(String iSBN, String titulo, String autor, Integer numeroDePaginas, LocalDate fechaDeAdquisicion,
			Double precio, Integer estimacionDeVentas, TipoPrestamo tipoDePrestamo) {
		super();
		Preconditions.checkArgument(numeroDePaginas>0, String.format("El número de páginas debe ser positivo y es %d",numeroDePaginas));
		Preconditions.checkArgument(precio>0, String.format("El precio debe ser positivo y es %.2f",precio));
		Preconditions.checkArgument(estimacionDeVentas>0, String.format("La estimacion de ventas debe ser positiva y es %d",estimacionDeVentas));
		Preconditions.checkArgument(iSBN.length()==10 || iSBN.length()==13,String.format("El ISBN debe tener 10 o 13 caracteres y tiene %d",iSBN.length()));
		Preconditions.checkArgument(fechaDeAdquisicion.isBefore(LocalDate.now()), String.format("La fecha debe ser anterior a hoy y s %s",fechaDeAdquisicion.toString()));
		ISBN = iSBN;
		this.titulo = titulo;
		this.autor = autor;
		this.numeroDePaginas = numeroDePaginas;
		this.fechaDeAdquisicion = fechaDeAdquisicion;
		this.precio = precio;
		this.estimacionDeVentas = estimacionDeVentas;
		this.tipoDePrestamo = tipoDePrestamo;
	}

	public Integer getEstimacionDeVentas() {
		return estimacionDeVentas;
	}

	public void setEstimacionDeVentas(Integer estimacionDeVentas) {
		Preconditions.checkArgument(estimacionDeVentas>0, String.format("La estimacion de ventas debe ser positiva y es %d",estimacionDeVentas));
		this.estimacionDeVentas = estimacionDeVentas;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public Integer getNumeroDePaginas() {
		return numeroDePaginas;
	}

	public LocalDate getFechaDeAdquisicion() {
		return fechaDeAdquisicion;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public TipoPrestamo getTipoDePrestamo() {
		return tipoDePrestamo;
	}


	public Boolean isBestSeller() {
		return this.estimacionDeVentas > Libro.LIMITE_BEST_SELLER;
	}
	
	public Integer getDiasDePrestamo() {
		Integer r = null;
		switch(this.tipoDePrestamo) {
		case DIARIO: r = 1; break;
		case SEMANAL: r = 7; break;
		case MENSUAL: r =30; break;
		default: break;
		}
		return r;
	}
	
	@Override
	public int compareTo(Libro libro) {
		return this.ISBN.compareTo(libro.getISBN());
	}

	@Override
	public String toString() {
		return String.format("%s -- %s",titulo,autor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		return true;
	}
	
	

}
