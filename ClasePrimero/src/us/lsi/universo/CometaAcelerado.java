package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.universo.Universo2D.Location;


public class CometaAcelerado extends Cometa {
	
	public static CometaAcelerado of(String nombre, Universo2D universo) {
		Punto2D coordenadas = Orbita2D.puntoAleatorio(0., (double)universo.xMax,0.,(double)universo.yMax);
		Integer diametro = 10;
		Vector2D direccion = Vector2D.of(1.,Orbita2D.valorAleatorioEntre(0.,Math.PI/2));
		Double velocidad = 10.;
		Double aceleracion = 0.1;
		return new CometaAcelerado(nombre,coordenadas, diametro, direccion, velocidad, aceleracion, universo);
	}
	
	public static CometaAcelerado of(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Double aceleracion, Universo2D universo) {
		return new CometaAcelerado(nombre,coordenadas, diametro, direccion, velocidad, aceleracion, universo);
	}


	protected double aceleracion;
	
	
    private CometaAcelerado(String nombre,Punto2D coordenadas, Integer diametro, 
    		Vector2D direccion, Double velocidad, Double aceleracion, Universo2D universo) {
        super(nombre, coordenadas, diametro, direccion, velocidad, universo);
        this.aceleracion = aceleracion;
        super.color = Color.RED;
    }

    public void cambiaPropiedades() {
    	super.cambiaPropiedades();
    	if (!this.location().equals(Location.Inside)) {
			this.velocidad = this.velocidad * (1 + this.aceleracion);
		}   	
    }
}
