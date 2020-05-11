package us.lsi.geometria;

import java.util.function.Function;

public class Punto2D implements Comparable<Punto2D>, ObjetoGeometrico2D {	

	private static Punto2D cero = Punto2D.of();
	
	public static Punto2D getOrigen(){
		return Punto2D.cero;
	}
	
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x, y);
	}

	public static Punto2D copy(Punto2D p) {
		return new Punto2D(p.getX(), p.getY());
	}
	
	public static Punto2D of() {
		return new Punto2D();
	}
	
	private Double x;
	private Double y;

	protected Punto2D(){
		x = 0.;
		y = 0.;
	}
	
	protected Punto2D(Double x, Double y){
		this.x = x;
		this.y = y;
	}
	
	public Double getX(){
		return this.x;
	}
	public Double getY(){
		return this.y;
	}

	public void setX(Double x){
		this.x = x;
	}
    public void setY(Double y){
    	this.y = y;
    }
    
    public Cuadrante getCuadrante(){
		Cuadrante c;
		if(this.getX() >=0 && this.getY() >=0){
			c = Cuadrante.PRIMER_CUADRANTE;
		} else if(this.getX() <=0 && this.getY() >=0){
			c = Cuadrante.SEGUNDO_CUADRANTE;
		} else if(this.getX() <=0 && this.getY() <=0){
			c = Cuadrante.TERCER_CUADRANTE;
		} else {
			c = Cuadrante.CUARTO_CUADRANTE;
		}
		return c;
	}
    
    public Double getDistanciaA(Punto2D p) {
    	Double dx = this.x-p.getX();
    	Double dy = this.y-p.getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
    
	public Double getDistanciaAlOrigen() {
		return getDistanciaA(Punto2D.getOrigen());
	} 
	
	public Punto2D add(Vector2D v){
    	return Punto2D.of(this.x+v.getX(),this.y+v.getY());
    }
	
	public Punto2D minus(Vector2D v){
    	return Punto2D.of(this.x-v.getX(),this.y-v.getY());
    }
    
    public Vector2D minus(Punto2D v){
    	return Vector2D.ofXY(this.x-v.getX(),this.y-v.getY());
    }
    
    public Vector2D vector() {
		return Vector2D.ofXY(this.x, this.y);
	}	
	
	public Punto2D traslada(Vector2D v){
		return add(v);
	}
    
	public Punto2D rota(Punto2D p, Double angulo){
		Vector2D v = minus(p).rota(angulo);
		return p.add(v);
	}	
	
	@Override
	public Punto2D homotecia(Punto2D p, Double factor) {
		return p.add(Vector2D.of(p, this).multiply(factor));
	}
	
	@Override
	public Punto2D proyectaSobre(Recta2D r) {
		return r.punto(0.).add(this.minus(r.punto(0.)).proyectaSobre(r.getVector()));
	}
	
	@Override
	public Punto2D simetrico(Recta2D r) {
		Punto2D p = this.proyectaSobre(r);
		return p.vector().multiply(2.).minus(this.vector()).punto();
	}
	
	@Override
	public Punto2D transform(Function<Double,Double> xt, Function<Double,Double> yt) {
		return Punto2D.of(xt.apply(this.x),yt.apply(this.y));
	}	
	
	@Override
	public void show(Ventana v) {
		v.show(this);
	}
		
	public String toString() {
    	return String.format("(%.2f,%.2f)",this.getX(),this.getY());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Punto2D))
			return false;
		Punto2D other = (Punto2D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public int compareTo(Punto2D p) {
		if(p==null || this.getX() ==null || this.getY() == null|| p.getX() ==null || p.getY() == null ){
	           throw new NullPointerException();
	    }
		return this.getDistanciaAlOrigen().compareTo(p.getDistanciaAlOrigen());
	}
	
}
