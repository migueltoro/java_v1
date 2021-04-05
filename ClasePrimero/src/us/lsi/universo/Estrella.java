package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;

public class Estrella extends CuerpoCeleste {   
   
	public static Estrella of(String nombre, Punto2D posicion, Integer diametro, Universo2D universo) {
		return new Estrella(nombre, posicion, diametro, universo);
	}

	
    private Estrella(String nombre,Punto2D posicion, Integer diametro, Universo2D universo) {
        super(nombre,diametro,Color.YELLOW,universo); 
        this.coordenadas = posicion;
    }

    private Punto2D coordenadas;


	@Override
	public Punto2D coordenadas() {
		return this.coordenadas;
	}


	@Override
	public void unPaso() {}


	@Override
	public void cambiaPropiedades() {}  

}
