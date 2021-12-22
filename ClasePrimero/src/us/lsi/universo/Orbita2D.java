package us.lsi.universo;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;

public record Orbita2D(
		Double a,
		Double b,
		Double c,
		Double anguloEjeMayor,
		Double excentricidad,
		Double d,
		Double d1,
		Double d2) {
	
	public static Double valorAleatorioEntre(Double inferior, Double superior) {
    	return Math.random()*(superior-inferior) + inferior;
    }
	
	public static Punto2D puntoAleatorio(Double xmin, Double xmax, Double ymin, Double ymax) {
		return Punto2D.of(valorAleatorioEntre(xmin,xmax), valorAleatorioEntre(ymin,ymax));
	}

	public  static Orbita2D of(Double a, Double excentricidad, Double anguloEjeMayor) {
		Double c = a*excentricidad;
		Double b = Math.sqrt(a*a-c*c);
		Double d = a*(1-excentricidad*excentricidad); // a*(1-c*c/(a*a)) = 
		Double d1 = a - c; // a*(1-c/a) = a - c
		Double d2 = a + c; // a*(1+c/a) = a + c
		return new Orbita2D(a,b,c,anguloEjeMayor,excentricidad,d,d1,d2);
	}
	
	public Vector2D radioVector(Double angulo) {
		Double r = this.d/(1+this.excentricidad*Math.cos(angulo));
		Vector2D v = Vector2D.ofRadianes(r,angulo+this.anguloEjeMayor);
		return v;
	}
	
	@Override
	public String toString() {
		return "Orbita2D [a=" + a + ", b=" + b + ", c=" + c +", anguloEjeMayor=" + 
				anguloEjeMayor + ", excentricidad="
				+ excentricidad + ", d=" + d 
				+ ", d1=" + d1 + ", d2=" + d2 + "]";
	}
	
	
	
}
