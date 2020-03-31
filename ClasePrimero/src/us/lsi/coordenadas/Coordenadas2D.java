package us.lsi.coordenadas;

import java.util.List;

public class Coordenadas2D {
	

	public static Coordenadas2D of(Double latitud, Double longitud) {
		return new Coordenadas2D(latitud, longitud);
	}

	private Double latitud;
	private Double longitud;
	
	private Coordenadas2D(Double latitud, Double longitud) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public Double getLongitud() {
		return longitud;
	}
	
	public Coordenadas2D toRadians() {
		Double latitud = Math.toRadians(this.latitud);
		Double longitud = Math.toRadians(this.longitud);
		return Coordenadas2D.of(latitud, longitud);
	}
	
	public Double distancia(Coordenadas2D c) {
		return distancia(this,c);
	}
	
	public static Double distancia(Coordenadas2D c1, Coordenadas2D c2) {
		Double radio_tierra = 6373.0;
		Coordenadas2D c1R = c1.toRadians();
		Coordenadas2D c2R = c2.toRadians();		
		Double incLat  = c2R.latitud-c1R.latitud;
		Double incLong = c2R.longitud-c1R.longitud;
		Double a = Math.pow(Math.sin(incLat/2),2)+
				Math.cos(c1R.latitud)*Math.cos(c2R.latitud)*Math.pow(Math.sin(incLong/2),2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return radio_tierra*c;
	}
	
	public static Coordenadas2D center(List<Coordenadas2D> coordenadas) {
		Double averageLat = coordenadas.stream().mapToDouble(x->x.latitud).average().getAsDouble();
		Double averageLng = coordenadas.stream().mapToDouble(x->x.longitud).average().getAsDouble();
		return Coordenadas2D.of(averageLat,averageLng);
	}

	@Override
	public String toString() {
		return String.format("(%.2f,%.2f)",this.latitud,this.longitud);
	}
	
}
