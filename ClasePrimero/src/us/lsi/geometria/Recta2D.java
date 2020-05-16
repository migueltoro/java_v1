package us.lsi.geometria;

import java.awt.Shape;
import java.util.function.Function;

public class Recta2D implements ObjetoGeometrico2D, ShapeDeObjeto, ShowObjeto {
	
	public static Recta2D of(Punto2D punto, Vector2D vector) {
		return new Recta2D(punto, vector);
	}
	
	public static Recta2D of(Punto2D p1, Punto2D p2) {
		return new Recta2D(p1, p2.minus(p1));
	}

	private Punto2D punto;
	private Vector2D vector;
	
	
	private Recta2D(Punto2D punto, Vector2D vector) {
		super();
		this.punto = punto;
		this.vector = vector;
	}

	public Vector2D getVector() {
		return this.vector;
	}

	public Punto2D punto(Double lambda) {
		return this.punto.add(this.vector.multiply(lambda));
	}
	
	@Override
	public Recta2D rota(Punto2D p, Double angulo) {
		Punto2D p1 = this.punto(0.);
		Punto2D p2 = this.punto(1.);
		return Recta2D.of(p1.rota(p, angulo), p2.rota(p, angulo));
	}

	@Override
	public Recta2D traslada(Vector2D v) {
		Punto2D p1 = this.punto(0.);
		Punto2D p2 = this.punto(1.);
		return Recta2D.of(p1.traslada(v), p2.traslada(v));
	}

	@Override
	public Recta2D homotecia(Punto2D p, Double factor) {
		Punto2D p1 = this.punto(0.);
		Punto2D p2 = this.punto(1.);
		return Recta2D.of(p1.homotecia(p2, factor), p2.homotecia(p2, factor));
	}

	@Override
	public Recta2D proyectaSobre(Recta2D r) {
		return r;
	}

	@Override
	public Recta2D simetrico(Recta2D r) {
		Punto2D p1 = this.punto(0.);
		Punto2D p2 = this.punto(1.);
		return Recta2D.of(p1.simetrico(r), p2.simetrico(r));
	}

	@Override
	public Recta2D transform(Function<Double, Double> xt, Function<Double, Double> yt) {
		Punto2D p1 = this.punto(0.);
		Punto2D p2 = this.punto(1.);
		return Recta2D.of(p1.transform(xt, yt), p2.transform(xt, yt));
	}
	
	@Override
	public void show(Ventana v) {
		v.g2.draw(this.shape(Ventana.xt,Ventana.yt));		
	}

	@Override
	public Shape shape(Function<Double, Double> xt, Function<Double, Double> yt) {
		Punto2D p1 = this.punto(-100.);
		Punto2D p2 = this.punto(100.);
		return Segmento2D.of(p1, p2).shape(xt, yt);
	}

	@Override
	public String toString() {
		return Segmento2D.of(this.punto(-200.), this.punto(200.)).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((punto == null) ? 0 : punto.hashCode());
		result = prime * result + ((vector == null) ? 0 : vector.hashCode());
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
		Recta2D other = (Recta2D) obj;
		if (punto == null) {
			if (other.punto != null)
				return false;
		} else if (!punto.equals(other.punto))
			return false;
		if (vector == null) {
			if (other.vector != null)
				return false;
		} else if (!vector.equals(other.vector))
			return false;
		return true;
	}

	

}

