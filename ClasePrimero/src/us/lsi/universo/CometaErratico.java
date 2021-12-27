package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;


public class CometaErratico extends Cometa {
    
	public static CometaErratico of(String nombre, Universo2D universo) {
		Punto2D coordenadas = Universo2D.puntoAleatorio(0., (double)universo.xMax,0.,(double)universo.yMax);
		Integer diametro = 10;
		Vector2D direccion = Vector2D.of(1.,Universo2D.valorAleatorioEntre(0.,Math.PI));
		Double velocidad = 5.;
		return new CometaErratico(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}
	
	
	public static CometaErratico of(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Universo2D universo) {
		return new CometaErratico(nombre,coordenadas, diametro, direccion, velocidad, universo);
	}

	
    private CometaErratico(String nombre,Punto2D coordenadas, Integer diametro, 
			Vector2D direccion,Double velocidad, Universo2D universo) {
        super(nombre,coordenadas, diametro, direccion, velocidad,universo);
        super.color = Color.BLACK;
    }
	
	@Override
	public void unPaso() {
		Double angulo = Universo2D.valorAleatorioEntre(0., Math.PI);
		this.direccion = Vector2D.ofRadianes(1., angulo);		
		this.coordenadas = this.coordenadas.traslada(this.direccion.multiply(this.velocidad));
	}
}
