package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;

public class PlanetaCircular extends CuerpoCeleste {
	
	
	public static PlanetaCircular of(String nombre,Punto2D posicion, int diametro, boolean dextrogiro,
			double velocidadGiro, Estrella estrella) {
		return new PlanetaCircular(nombre,posicion, diametro, dextrogiro, velocidadGiro, estrella);
	}

	protected CuerpoCeleste estrella;
	protected Double velocidadGiro;
    
    private PlanetaCircular(String nombre,Punto2D posicion,int diametro, 
    		       boolean dextrogiro, double velocidadGiro, Estrella estrella) {
        super(nombre,posicion, diametro,Color.ORANGE,estrella.universo);
        this.estrella = estrella;
        this.velocidadGiro = dextrogiro?velocidadGiro:-velocidadGiro;    
    }

	@Override
	public Punto2D nuevasCoordenadas(Integer tiempo) {
		return this.coordenadas.rota(this.estrella.coordenadas,this.velocidadGiro);
	}   
	
	
}
