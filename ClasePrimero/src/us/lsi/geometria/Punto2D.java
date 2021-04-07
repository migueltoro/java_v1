package us.lsi.geometria;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import us.lsi.tools.Ventana;
import us.lsi.tools.Ventana.DoublePair;

public record Punto2D(Double x,Double y) implements ObjetoGeometrico2D, Comparable<Punto2D>, ShapeDeObjeto {
	
	private static Punto2D cero = Punto2D.of(0.,0.);
	
	public static Punto2D origen(){
		return Punto2D.cero;
	}
	
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x, y);
	}

	public static Punto2D parse(String text) {
		String text2 = text.substring(1, text.length()-1);
		String[] campos = text2.split(",");
		return new Punto2D(Double.parseDouble(campos[0]), Double.parseDouble(campos[1]));
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
    
    public static Double distanciaA(Punto2D p1, Punto2D p2) {
    	Double dx = p1.x()-p2.x();
    	Double dy = p1.y()-p2.y();
		return Math.sqrt(dx*dx+dy*dy);
    }
    
    public Double distanciaA(Punto2D p) {
    	Double dx = this.x()-p.x();
    	Double dy = this.y()-p.y();
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
    	return Vector2D.of(this.x-p.x(),this.y-p.y());
    }
    
    public Vector2D vector() {
		return Vector2D.of(this.x, this.y);
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
	public Punto2D transform(Ventana v) {
		DoublePair d = v.transform(this.x,this.y);
		Punto2D t = Punto2D.of(d.x(), d.y());
		return t;
	}
	
	@Override
	public void show(Ventana v) {
		v.canvas.fill(this.shape(v));
	}
	
	@Override
	public Shape shape(Ventana v) {
		Punto2D t = this.transform(v);
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

	//854.400375,1204.159458
	public static void main(String[] args) {
		Punto2D p1 = Punto2D.of(200.00,100.00);
		Punto2D p2 = Punto2D.of(1000.00,400.00);
		System.out.println(p1.distanciaA(p2));
		Double distanciaCentros = Math.sqrt(
				Math.pow(p1.x()-p2.x(),2)+
				Math.pow(p1.y()-p2.y(),2));
		System.out.println(distanciaCentros);
		Double distanciaCentros2 = Math.sqrt(
			    (p1.x()-p2.x())*(p1.x()-p2.x())+
			    (p1.y()-p2.y())*(p1.y()-p2.y()));
		System.out.println(distanciaCentros2);
		System.out.println(distanciaA(p1,p2));
	}


}
