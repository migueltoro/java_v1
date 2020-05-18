package us.lsi.vuelos;

import java.time.Duration;
import java.time.LocalDate;

public class Vuelo {
	
	private String destino;
	private Double precio;
	private Integer numPlazas;
	private Integer numPasajeros;
	private String codigo;
	private LocalDate fecha;
	private Duration duracion;
	
	
	public String getDestino() {
		return destino;
	}
	public Double getPrecio() {
		return precio;
	}
	public Integer getNumPlazas() {
		return numPlazas;
	}
	public Integer getNumPasajeros() {
		return numPasajeros;
	}
	public String getCodigo() {
		return codigo;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public Duration getDuracion() {
		return duracion;
	}
	
	

}
