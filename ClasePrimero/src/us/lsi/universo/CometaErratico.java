package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.universo.Universo2D.Location;


public class CometaErratico extends Cometa {
    
	public static CometaErratico of(String nombre, Universo2D universo) {
		Punto2D coordenadas = Orbita2D.puntoAleatorio(0., (double)universo.xMax,0.,(double)universo.yMax);
		Integer diametro = 10;
		Vector2D direccion = Vector2D.of(1.,Orbita2D.valorAleatorioEntre(0.,Math.PI/2));
		Double velocidad = 10.;
		return new CometaErratico(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}
	
	
	public static CometaErratico of(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Universo2D universo) {
		return new CometaErratico(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}

	
    private CometaErratico(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Universo2D universo) {
        super(nombre,coordenadas, diametro, direccion, velocidad,universo);
        super.color = Color.GREEN;
    }
    
   
    
    
	public void cambiaPropiedades() {
		super.cambiaPropiedades();
		if (!this.location().equals(Location.Inside)) {
			Double angulo = this.direccion.angulo();
			angulo = angulo + Orbita2D.valorAleatorioEntre(-Math.PI / 9, Math.PI / 9);
			this.direccion = Vector2D.ofRadianes(1., angulo);
		}
	}
}
