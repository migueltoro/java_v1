package us.lsi.universo;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;

public class Orbita2D {
	
	public static Orbita2D of(Double a, Double b, Double anguloEjeMayor) {
		Double c = Math.sqrt(a*a-b*b);
		Double excentricidad = c/a;
		return new Orbita2D(a,excentricidad, anguloEjeMayor);
	}
	
	public static Orbita2D ofExcentricidad(Double a, Double excentricidad, Double anguloEjeMayor) {
		return new Orbita2D(a, excentricidad, anguloEjeMayor);
	}
	
	public static Double valorAleatorioEntre(Double inferior, Double superior) {
    	return Math.random()*(superior-inferior) + inferior;
    }
	
	public static Punto2D puntoAleatorio(Double xmin, Double xmax, Double ymin, Double ymax) {
		return Punto2D.of(valorAleatorioEntre(xmin,xmax), valorAleatorioEntre(ymin,ymax));
	}

	private Double a;
	private Double b;
	private Double c;
	private Double anguloEjeMayor;
	private Double excentricidad;
	private Double d;
	private Double d1;
	private Double d2;
	
	private Orbita2D(Double a, Double excentricidad, Double anguloEjeMayor) {
		super();
		this.a = a;
		this.c = a*excentricidad;
		this.b = Math.sqrt(a*a-c*c);
		this.anguloEjeMayor = anguloEjeMayor;
		this.excentricidad = excentricidad;
		this.d = a*(1-this.excentricidad*this.excentricidad);
		this.d1 = a*(1-this.excentricidad);
		this.d2 = a*(1+this.excentricidad);
	}
	
	public Vector2D radioVector(Double angulo) {
		Double r = this.d/(1+this.excentricidad*Math.cos(angulo));
		return Vector2D.ofRadianes(r,angulo+this.anguloEjeMayor);
	}

	public Double a() {
		return a;
	}

	public Double b() {
		return b;
	}

	public Double anguloEjeMayor() {
		return anguloEjeMayor;
	}

	public Double excentricidad() {
		return excentricidad;
	}

	public Double d1() {
		return d1;
	}
	
	public Double d2() {
		return d2;
	}

	@Override
	public String toString() {
		return "Orbita2D [a=" + a + ", b=" + b + ", c=" + c +", anguloEjeMayor=" + anguloEjeMayor + ", excentricidad="
				+ excentricidad + ", d=" + d + ", d1=" + d1 + ", d2=" + d2 + "]";
	}
	
	
	
}
