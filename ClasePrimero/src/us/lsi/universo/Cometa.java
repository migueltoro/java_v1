package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.universo.Universo.Position;


public class Cometa extends CuerpoCeleste {
	
	public static Cometa of(String nombre,Punto2D posicion, Integer diametro,
			Vector2D direccion, Double velocidad, Universo universo) {
		return new Cometa(nombre,posicion, diametro, direccion, velocidad, universo);
	}

	protected Vector2D direccion;
	protected Double velocidad;
   
    protected Cometa(String nombre,Punto2D posicion, Integer diametro, 
    		      Vector2D direccion, Double velocidad, Universo universo) {
        super(nombre, posicion, diametro, Color.WHITE, universo);
        this.direccion = direccion.unitario();
        this.velocidad = velocidad;
        
    }
    
    public void cambiaPropiedades() {
    	 Position p = this.position;
//	     System.out.println(p);
	     switch(p) {
	     case Down: this.direccion = Vector2D.of(this.direccion.x(), -this.direccion.y()); break;
	     case Inside: break;
		 case Left:this.direccion = Vector2D.of(-this.direccion.x(), this.direccion.y());break;
		 case Right:this.direccion = Vector2D.of(-this.direccion.x(), this.direccion.y()); break;
		 case Up: this.direccion = Vector2D.of(this.direccion.x(), -this.direccion.y());break;
		 case OutSide:break;
	     }
//	     System.out.println(this.coordenadas);
//	     System.out.println(this.direccion);
    }

	@Override
	public Punto2D nuevasCoordenadas(Integer tiempo) {
		 return this.coordenadas.traslada(this.direccion.multiply(this.velocidad));
	}
	
	
    
}
