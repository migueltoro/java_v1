package us.lsi.universo;

import java.awt.Color;

import us.lsi.ejemplos_b1_tipos.Vector2D;
import us.lsi.geometria.Punto2D;


public class CometaAcelerado extends Cometa {
	
	public static CometaAcelerado of(String nombre, Universo2D universo) {
		Punto2D coordenadas = Universo2D.puntoAleatorio(0., (double)universo.xMax,0.,(double)universo.yMax);
		Integer diametro = 10;
		Vector2D direccion = Vector2D.of(1.,Universo2D.valorAleatorioEntre(0.,Math.PI/2));
		Double velocidad = 5.;
		Double aceleracion = 0.5;
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
        super.color = Color.WHITE;
    }
    
    @Override
	public void unPaso() {
    	if (!this.location().equals(CuerpoCeleste.Location.Inside)) {
			this.velocidad = this.velocidad * (1 + this.aceleracion);
		} 
		this.coordenadas = this.coordenadas.traslada(this.direccion.multiply(this.velocidad));
	}
}
