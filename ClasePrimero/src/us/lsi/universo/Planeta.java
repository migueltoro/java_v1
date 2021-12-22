package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;
import us.lsi.tools.Preconditions;

public class Planeta extends CuerpoCeleste {
	
	
	public static Planeta of(String nombre, Boolean dextrogiro, Estrella estrella) {
		Integer diametro = 15;
		Double a = Orbita2D.valorAleatorioEntre(200., 400.);
		Double excentricidad = Orbita2D.valorAleatorioEntre(0.2, 0.9);
		Double anguloEjeMayor = Orbita2D.valorAleatorioEntre(0., Math.PI / 2);
		Double velocidadGiro = Orbita2D.valorAleatorioEntre(0.05, 0.1);
		Double angulo = 0.;
		return Planeta.of(nombre, diametro, Color.MAGENTA, a, excentricidad, anguloEjeMayor, dextrogiro, velocidadGiro, angulo,estrella);
	}

	
	public static Planeta satelite(String nombre, Boolean dextrogiro, CuerpoCeleste planeta) {
		Integer diametro = 10;
		Double a = Orbita2D.valorAleatorioEntre(40., 50.);
		Double excentricidad = 0.;
		Double anguloEjeMayor = 0.;
		Double velocidadGiro = Orbita2D.valorAleatorioEntre(0.5,1.5);
		Double angulo = 0.;
		return  of(nombre, diametro, Color.GREEN, a, excentricidad, anguloEjeMayor, dextrogiro, velocidadGiro, angulo, planeta);
	}

	
	
	public static Planeta of(String nombre, Integer diametro, Color color,Double a, Double excentricidad, Double anguloEjeMayor,
			Boolean dextrogiro, Double velocidadGiro, Double angulo, CuerpoCeleste planeta) {
		return new Planeta(nombre, diametro, color,a, excentricidad, anguloEjeMayor, dextrogiro, velocidadGiro, angulo,
				planeta);
	}

	protected CuerpoCeleste planeta;
	protected Double velocidadGiro;
	protected Double angulo;
	protected Orbita2D orbita;
	protected Punto2D last;

	
	private Planeta(String nombre, Integer diametro, Color color, Double a, Double excentricidad, Double anguloEjeMayor,
			Boolean dextrogiro, Double velocidadGiro, Double angulo, CuerpoCeleste planeta) {
		super(nombre, diametro, color, planeta.universo);
		Preconditions.checkArgument(excentricidad>=0 && excentricidad <=1.,String.format("La excentricidad es %.2f",excentricidad));
		this.planeta = planeta;
		this.velocidadGiro = dextrogiro ? velocidadGiro : -velocidadGiro;
		this.angulo = angulo;
		this.orbita = Orbita2D.of(a,excentricidad, anguloEjeMayor);
		this.last =  this.planeta.coordenadas().add(this.orbita.radioVector(this.angulo));
	}
	
	@Override
	public Punto2D coordenadas() {
		return this.last;	
	}

	@Override
	public void unPaso() {
		this.angulo = this.angulo + this.velocidadGiro;
		Vector2D v = this.orbita.radioVector(this.angulo);
		this.last =  this.planeta.coordenadas().add(v);
	}

	@Override
	public void cambiaPropiedades() {
	}

}
