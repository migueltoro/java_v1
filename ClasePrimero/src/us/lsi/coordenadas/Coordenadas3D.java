package us.lsi.coordenadas;

import us.lsi.tools.Utils;

public record Coordenadas3D(Double latitud, Double longitud, Double altitud) {
	
	public Coordenadas3D {
		assert Utils.allNotNull(latitud, longitud, altitud) && altitud >= 0.: "Los parámetros no pueden ser nulos.";
	}

	public static Coordenadas3D of(Double latitud, Double longitud, Double altitud) {
		return new Coordenadas3D(latitud, longitud, altitud);
	}

	public Double distancia(Coordenadas3D c) {
		return distancia(this,c);
	}
	
	
	public Coordenadas2D to2D() {
		return Coordenadas2D.of(latitud, longitud);
	}
	
	public static Double distancia(Coordenadas3D c1, Coordenadas3D c2) {
		Coordenadas2D c12D = Coordenadas2D.of(c1.latitud, c1.longitud);
		Coordenadas2D c22D = Coordenadas2D.of(c2.latitud, c2.longitud);
		return Math.sqrt(Math.pow(Coordenadas2D.distancia(c12D,c22D),2)+Math.pow(c1.altitud-c1.altitud,2));
	}
	
	@Override
	public String toString() {
		return String.format("(%f,%f,%f)",latitud,longitud,altitud);
	}
	
	
}
