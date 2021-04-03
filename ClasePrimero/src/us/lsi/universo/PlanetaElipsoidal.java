package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.tools.Preconditions;

public class PlanetaElipsoidal extends CuerpoCeleste{

	public static PlanetaElipsoidal of(String nombre, Integer diametro, Boolean dextrogiro,
			Double velocidadGiro, Double a, Double anguloEjeMayor, Double b, Double angulo, Estrella estrella) {
		Double excentricidad = Math.sqrt(1-b*b/(a*a));
		Double d = a*(1-excentricidad*excentricidad)/(1+excentricidad*Math.cos(angulo));
		Punto2D coordenadas = estrella.coordenadas.add(Vector2D.ofRadianes(d,anguloEjeMayor+angulo));
		Preconditions.checkNotNull(excentricidad,d,coordenadas);
		return new PlanetaElipsoidal(nombre,coordenadas,diametro,dextrogiro,velocidadGiro,a,anguloEjeMayor,b,excentricidad,angulo,estrella);
	}

	protected CuerpoCeleste estrella;	
	protected Double velocidadGiro;
	protected Double a;
	protected Double anguloEjeMayor;
	protected Double b;
	protected Double excentricidad;
	protected Double angulo;
	
    
    private PlanetaElipsoidal(String nombre,Punto2D coordenadas,Integer diametro, 
    		       Boolean dextrogiro, Double velocidadGiro,
    		       Double a,
    		       Double anguloEjeMayor,
    		   	   Double b,
    		   	   Double excentricidad,
    		   	   Double angulo,
    		       Estrella estrella) {
        super(nombre,coordenadas,diametro,Color.MAGENTA,estrella.universo);
        this.estrella = estrella;
        this.velocidadGiro = dextrogiro?velocidadGiro:-velocidadGiro;   
        this.a = a;
        this.anguloEjeMayor = anguloEjeMayor;
        this.b = b;
        this.angulo = angulo;
        this.excentricidad = excentricidad;   
    }
    
    private Double distanciaAlFoco() {
    	Double d = this.a*(1-this.excentricidad*this.excentricidad)/(1+this.excentricidad*Math.cos(this.angulo));
    	return d;
    }

	@Override
	public Punto2D nuevasCoordenadas(Integer tiempo) {
		this.angulo = this.angulo+this.velocidadGiro;
		Double d = this.distanciaAlFoco();
		Punto2D r = this.estrella.coordenadas.add(Vector2D.ofRadianes(d,this.anguloEjeMayor+this.angulo));
		return r;
	}   
	
	
}
