package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.tools.Preconditions;

public class Satelite extends CuerpoCeleste {
	
	
	public static Satelite of(String nombre, CuerpoCeleste planeta) {

		Integer diametro = 10;
		Double a = Orbita2D.valorAleatorioEntre(20., 30.);
		Double excentricidad = 0.;
		Double anguloEjeMayor = 0.;
		Boolean dextrogiro = false;
		Double velocidadGiro = Orbita2D.valorAleatorioEntre(0.1,1.);
		Double angulo = 0.;

		return  new Satelite(nombre, diametro, a, excentricidad, anguloEjeMayor, dextrogiro, velocidadGiro, angulo,planeta);
	}

	
	protected CuerpoCeleste planeta;
	protected Double velocidadGiro;
	protected Double angulo;
	protected Orbita2D orbita;
	protected Punto2D last;

	
	private Satelite(String nombre, Integer diametro, Double a, Double excentricidad, Double anguloEjeMayor,
			Boolean dextrogiro, Double velocidadGiro, Double angulo, CuerpoCeleste planeta) {
		super(nombre, diametro, Color.PINK, planeta.universo);
		Preconditions.checkArgument(excentricidad>=0 && excentricidad <=1.,String.format("La excentricidad es %.2f",excentricidad));
		this.planeta = planeta;
		this.velocidadGiro = dextrogiro ? velocidadGiro : -velocidadGiro;
		this.angulo = angulo;
		this.orbita = Orbita2D.ofExcentricidad(a,excentricidad, anguloEjeMayor,Punto2D.of(0.,0.));
		Vector2D v = this.planeta.coordenadas().vector();
		this.last =  this.orbita.punto(this.angulo).add(v);
	}
	
	@Override
	public Punto2D coordenadas() {
		return this.last;	
	}

	@Override
	public void unPaso() {
		this.angulo = this.angulo + this.velocidadGiro;
		Vector2D v = this.planeta.coordenadas().vector();
		this.last =  this.orbita.punto(this.angulo).add(v);
	}

	@Override
	public void cambiaPropiedades() {
	}

}
