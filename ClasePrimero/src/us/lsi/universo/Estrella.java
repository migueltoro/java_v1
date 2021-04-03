package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;

public class Estrella extends CuerpoCeleste {   
   
	public static Estrella of(String nombre, Punto2D posicion, int diametro, Universo universo) {
		return new Estrella(nombre, posicion, diametro, universo);
	}

	
    private Estrella(String nombre,Punto2D posicion, Integer diametro,Universo universo) {
        super(nombre,posicion, diametro,Color.YELLOW,universo); 
    }

	@Override
	public Punto2D nuevasCoordenadas(Integer tiempo) {
		return this.coordenadas;
	}    
}
