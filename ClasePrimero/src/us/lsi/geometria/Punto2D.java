package us.lsi.geometria;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.function.Function;

public record Punto2D(Double x,Double y) implements ObjetoGeometrico2D, ShapeDeObjeto, Comparable<Punto2D> {
	
	private static Punto2D cero = Punto2D.of(0.,0.);
	
	public static Punto2D origen(){
		return Punto2D.cero;
	}
	
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x, y);
	}

    
    public Cuadrante cuadrante(){
		Cuadrante c;
		if(this.x() >=0 && this.y() >=0){
			c = Cuadrante.PRIMER_CUADRANTE;
		} else if(this.x() <=0 && this.y() >=0){
			c = Cuadrante.SEGUNDO_CUADRANTE;
		} else if(this.x() <=0 && this.y() <=0){
			c = Cuadrante.TERCER_CUADRANTE;
		} else {
			c = Cuadrante.CUARTO_CUADRANTE;
		}
		return c;
	}
    
    public Double distanciaA(Punto2D p) {
    	Double dx = this.x-p.x();
    	Double dy = this.y-p.x();
		return Math.sqrt(dx*dx+dy*dy);
	}
    
	public Double distanciaAlOrigen() {
		return distanciaA(Punto2D.origen());
	} 
	
	public Punto2D add(Vector2D v){
    	return Punto2D.of(this.x+v.x(),this.y+v.y());
    }
	
	public Punto2D minus(Vector2D v){
    	return Punto2D.of(this.x-v.x(),this.y-v.y());
    }
    
    public Vector2D minus(Punto2D p){
    	return Vector2D.ofXY(this.x-p.x(),this.y-p.y());
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
		return r.punto().add(this.minus(r.punto()).proyectaSobre(r.vector()));
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
		v.g2.fill(this.shape(Ventana.xt,Ventana.yt));
	}
	
	@Override
	public Shape shape(Function<Double,Double> xt, Function<Double,Double> yt) {
		Punto2D t = this.transform(xt,yt);
		Punto2D sc = t.minus(Vector2D.baseX().multiply(5.));
		sc = sc.minus(Vector2D.baseY().multiply(5.));
		return new Ellipse2D.Double(sc.x(),sc.y(),10.,10.);
	}
		
	public String toString() {
    	return String.format("(%.2f,%.2f)",this.x(),this.y());
    }
	
	@Override
	public int compareTo(Punto2D p) {
		if(p==null || this.x() ==null || this.y() == null|| p.x() ==null || p.y() == null ){
	           throw new NullPointerException();
	    }
		return this.distanciaAlOrigen().compareTo(p.distanciaAlOrigen());
	}


}
