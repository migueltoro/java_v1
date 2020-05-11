package us.lsi.geometria;

import java.util.function.Function;

import us.lsi.tools.Preconditions;

public class Circulo2D implements ObjetoGeometrico2D {
	
	public static Circulo2D of(Punto2D centro, Double radio) {
		return new Circulo2D(centro, radio);
	}

	private Punto2D centro;
	private Double radio;
	
	private Circulo2D(Punto2D centro, Double radio) {
		super();
		Preconditions.checkArgument(radio>=0, "El radio debe ser mayor o igual a cero");
		this.centro = centro;
		this.radio = radio;
	}
	
	public Punto2D getCentro() {
		return centro;
	}

	public Double getRadio() {
		return radio;
	}
	
	public Double getArea() {
		return Math.PI*this.radio*this.radio;
	}

	public Double getPerimetro() {
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
		Vector2D u = r.getVector().unitario();
		return Segmento2D.of(pc.add(u.multiply(this.radio)),pc.add(u.multiply(-this.radio)));
	}
	
	@Override
	public Circulo2D simetrico(Recta2D r) {
		return Circulo2D.of(this.centro.simetrico(r), this.radio);
	}
	
	@Override
	public Circulo2D transform(Function<Double,Double> xt, Function<Double,Double> yt) {
		Punto2D p = this.centro.add(Vector2D.ofXY(1.,0.).multiply(this.radio)).transform(xt,yt);
		Punto2D c = this.centro.transform(xt,yt);
		return Circulo2D.of(c,p.getX()-c.getX());
	}

	
	@Override
	public void show(Ventana v) {
		v.show(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centro == null) ? 0 : centro.hashCode());
		result = prime * result + ((radio == null) ? 0 : radio.hashCode());
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
		Circulo2D other = (Circulo2D) obj;
		if (centro == null) {
			if (other.centro != null)
				return false;
		} else if (!centro.equals(other.centro))
			return false;
		if (radio == null) {
			if (other.radio != null)
				return false;
		} else if (!radio.equals(other.radio))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return String.format("(%s,%.2f)",this.centro,this.radio);
	}

	

}
