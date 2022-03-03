package us.lsi.geometria;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import us.lsi.tools.Preconditions;
import us.lsi.tools.Ventana;

public record Circulo2D(Punto2D centro,Double radio)  implements ObjetoGeometrico2D, ShapeDeObjeto{
	
	public static Circulo2D of(Punto2D centro, Double radio) {
		Preconditions.checkArgument(radio>=0, String.format("El radio debe ser mayor o igual a cero y es %.2f",radio));
		return new Circulo2D(centro, radio);
	}
	
	public Double area() {
		return Math.PI*this.radio*this.radio;
	}

	public Double perimetro() {
		return 2*Math.PI*this.radio;
	}

	@Override
	public Circulo2D rota(Punto2D p, Double angulo) {		
		return Circulo2D.of(this.centro.rota(p,angulo), this.radio);
	}

	@Override
	public Circulo2D traslada(Vector2D v) {
		return Circulo2D.of(this.centro.traslada(v), this.radio);
	}
	
	@Override
	public Circulo2D homotecia(Punto2D p, Double factor) {
		return Circulo2D.of(this.centro.homotecia(p,factor), this.radio*factor);
	}
	
	@Override
	public Segmento2D proyectaSobre(Recta2D r) {
		Punto2D pc = this.centro.proyectaSobre(r);
		Vector2D u = r.vector().unitario();
		return Segmento2D.of(pc.add(u.multiply(this.radio)),pc.add(u.multiply(-this.radio)));
	}
	
	@Override
	public Circulo2D simetrico(Recta2D r) {
		return Circulo2D.of(this.centro.simetrico(r), this.radio);
	}
	
	@Override
	public Circulo2D transform(Ventana v) {
		Punto2D p = this.centro.add(Vector2D.of(1.,0.).multiply(this.radio)).transform(v);
		Punto2D c = this.centro.transform(v);
		return Circulo2D.of(c,p.x()-c.x());
	}

	
	@Override
	public void show(Ventana v) {
		v.draw(this.shape(v));
	}
	
	
	@Override
	public Shape shape(Ventana v) {
		Circulo2D ct = (Circulo2D) this.transform(v);
		Punto2D sc = ct.centro().add(Vector2D.of(-1.,-1.).multiply(ct.radio()));
		return new Ellipse2D.Double(sc.x(),sc.y(),2*ct.radio(),2*ct.radio());	
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%.2f)",this.centro,this.radio);
	}

}
