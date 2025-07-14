package us.lsi.ruta;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import us.lsi.coordenadas.Coordenadas3D;

public record Marca(LocalTime time, Coordenadas3D coordenadas) {
	
	public static Marca parse(String text) {
		String[] campos = text.split(",");
		LocalTime time = LocalTime.parse(campos[0],DateTimeFormatter.ofPattern("HH:mm:ss"));
		Coordenadas3D coordenadas = Coordenadas3D.of(Double.parseDouble(campos[1]), Double.parseDouble(campos[2]),Double.parseDouble(campos[3])/1000);
		return Marca.of(time, coordenadas);
	}
	
	public static Marca parseCsv(List<String> campos) {
		LocalTime time = LocalTime.parse(campos.get(0),DateTimeFormatter.ofPattern("HH:mm:ss"));
		Coordenadas3D coordenadas = Coordenadas3D.of(Double.parseDouble(campos.get(1)), 
				Double.parseDouble(campos.get(2)),Double.parseDouble(campos.get(3))/1000);
		return Marca.of(time, coordenadas);
	}

	public static Marca of(LocalTime time, Coordenadas3D coordenadas) {
		return new Marca(time, coordenadas);
	}
	
	public Marca {
		assert time != null || coordenadas != null : "Marca no puede ser null";
	}

	@Override
	public String toString() {
		return String.format("(%s,%.2f,%.2f,%.2f)",time,coordenadas.latitud(),coordenadas.longitud(),coordenadas.altitud());
	}

}
