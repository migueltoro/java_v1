package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.universo.Universo2D.Location;


public class Cometa extends CuerpoCeleste {
	
	
	public static Cometa of(String nombre,Universo2D universo) {
		Punto2D coordenadas = Orbita2D.puntoAleatorio(0., (double)universo.xMax,0.,(double)universo.yMax);
		Integer diametro = 10;
		Vector2D direccion = Vector2D.of(1.,Orbita2D.valorAleatorioEntre(0.,Math.PI/2));
		Double velocidad = 10.;
		return new Cometa(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}
	
	public static Cometa of(String nombre,Punto2D coordenadas, Integer diametro,
			Vector2D direccion, Double velocidad, Universo2D universo) {
		return new Cometa(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}

	protected Vector2D direccion;
	protected Double velocidad;
	protected Punto2D coordenadas;
   
    protected Cometa(String nombre,Punto2D coordenadas, Integer diametro, 
    		      Vector2D direccion, Double velocidad, Universo2D universo) {
        super(nombre, diametro, Color.WHITE, universo);
        this.direccion = direccion.unitario();
        this.velocidad = velocidad;
        this.coordenadas = coordenadas;
    }
    
    public void cambiaPropiedades() {
    	 Location p = super.location();
	     switch(p) {
	     case Down: this.direccion = Vector2D.of(this.direccion.x(), -this.direccion.y()); break;
	     case Inside: break;
		 case Left:this.direccion = Vector2D.of(-this.direccion.x(), this.direccion.y());break;
		 case Right:this.direccion = Vector2D.of(-this.direccion.x(), this.direccion.y()); break;
		 case Up: this.direccion = Vector2D.of(this.direccion.x(), -this.direccion.y());break;
		 case OutSide:break;
	     }
    }


	@Override
	public Punto2D coordenadas() {
		return this.coordenadas;
	}

	@Override
	public void unPaso() {
		this.coordenadas = this.coordenadas.traslada(this.direccion.multiply(this.velocidad));
	}
	
    
}
