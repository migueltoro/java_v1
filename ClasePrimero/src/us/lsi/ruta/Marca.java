package us.lsi.ruta;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import us.lsi.coordenadas.Coordenadas3D;


public class Marca {
	
	public static Marca parse(String text) {
		String[] campos = text.split(",");
		LocalTime time = LocalTime.parse(campos[0],DateTimeFormatter.ofPattern("HH:mm:ss"));
		Coordenadas3D coordenadas = Coordenadas3D.of(Double.parseDouble(campos[1]), Double.parseDouble(campos[2]),Double.parseDouble(campos[3])/1000);
		return Marca.of(time, coordenadas);
	}

	public static Marca of(LocalTime time, Coordenadas3D coordenadas) {
		return new Marca(time, coordenadas);
	}

	private LocalTime time;
	private Coordenadas3D coordenadas;
	
	private Marca(LocalTime time, Coordenadas3D coordenadas) {
		super();
		this.time = time;
		this.coordenadas = coordenadas;
	}

	public LocalTime getTime() {
		return time;
	}

	public Coordenadas3D getCoordenadas() {
		return coordenadas;
	}

	@Override
	public String toString() {
		return String.format("(%s,%.2f,%.2f,%.2f)",time,coordenadas.getLatitud(),coordenadas.getLongitud(),coordenadas.getAltitud());
	}

}
