package us.lsi.biblioteca;

import java.time.LocalDate;

import us.lsi.tools.Preconditions;

public class AudioLibro extends Libro {
	
	public static AudioLibro of(String iSBN, String titulo, String autor, Integer numeroDePaginas,
			LocalDate fechaDeAdquisicion, Double precio, Integer estimacionDeVentas, TipoPrestamo tipoDePrestamo,
			FormatoAudio formato, String urlDescarga, Integer tamanyo) {
		return new AudioLibro(iSBN, titulo, autor, numeroDePaginas, fechaDeAdquisicion, precio, estimacionDeVentas,
				tipoDePrestamo, formato, urlDescarga, tamanyo);
	}


	private FormatoAudio formato;
	private String urlDescarga;
	private Integer tamanyo;
	
	
	private AudioLibro(String iSBN, 
			String titulo, 
			String autor, 
			Integer numeroDePaginas, 
			LocalDate fechaDeAdquisicion,
			Double precio, 
			Integer estimacionDeVentas, 
			TipoPrestamo tipoDePrestamo,
			FormatoAudio formato,
			String urlDescarga,
			Integer tamanyo) {
		super(iSBN, titulo, autor, numeroDePaginas, fechaDeAdquisicion, precio, estimacionDeVentas, tipoDePrestamo);
		Preconditions.checkArgument(urlDescarga.startsWith("http:"), String.format("La url debe comenzar por http: y es %s",urlDescarga));
		Preconditions.checkArgument(tamanyo>=0, String.format("El tamaño debe ser mayor o igual a cero y es %d",tamanyo));
		this.formato = formato;
		this.urlDescarga = urlDescarga;
		this.tamanyo = tamanyo;
	}


	@Override
	public String toString() {
		return super.toString() + "Formato: "+this.formato;
	}


	public FormatoAudio getFormato() {
		return formato;
	}


	public String getUrlDescarga() {
		return urlDescarga;
	}


	public Integer getTamanyo() {
		return tamanyo;
	}
	

}
