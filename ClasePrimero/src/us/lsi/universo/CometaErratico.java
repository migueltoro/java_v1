package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.universo.Universo.Position;


public class CometaErratico extends Cometa {
    
	public static CometaErratico of(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Universo universo) {
		return new CometaErratico(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}

	
    private CometaErratico(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Universo universo) {
        super(nombre,coordenadas, diametro, direccion, velocidad,universo);
        super.color = Color.GREEN;
    }
    
   
    private double direccionAleatoriaEntre(Double inferior, Double superior) {
    	return Math.random()*(superior-inferior) + inferior;
    }
    
	public void cambiaPropiedades() {
		super.cambiaPropiedades();
		if (!this.position.equals(Position.Inside)) {
			Double angulo = this.direccion.angulo();
			angulo = angulo + direccionAleatoriaEntre(-Math.PI / 9, Math.PI / 9);
			this.direccion = Vector2D.ofRadianes(1., angulo);
		}
	}
}
