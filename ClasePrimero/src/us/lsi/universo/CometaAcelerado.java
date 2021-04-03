package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.universo.Universo.Position;


public class CometaAcelerado extends Cometa {
	
	public static CometaAcelerado of(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Double aceleracion, Universo universo) {
		return new CometaAcelerado(nombre,coordenadas, diametro, direccion, velocidad, aceleracion, universo);
	}


	protected double aceleracion;
	
	
    private CometaAcelerado(String nombre,Punto2D coordenadas, Integer diametro, 
    		Vector2D direccion, Double velocidad, Double aceleracion, Universo universo) {
        super(nombre, coordenadas, diametro, direccion, velocidad, universo);
        this.aceleracion = aceleracion;
        super.color = Color.RED;
    }

    public void cambiaPropiedades() {
    	super.cambiaPropiedades();
    	if (!this.position.equals(Position.Inside)) {
			this.velocidad = this.velocidad * (1 + this.aceleracion);
		}   	
    }
}
