package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.tools.Preconditions;



public class Planeta extends CuerpoCeleste {

	public static Planeta of(String nombre, Boolean dextrogiro, Estrella estrella) {

		Integer diametro = 15;
		Double a = Orbita2D.valorAleatorioEntre(100., 200.);
		Double excentricidad = Orbita2D.valorAleatorioEntre(0.2, 0.9);
		Double anguloEjeMayor = Orbita2D.valorAleatorioEntre(0., Math.PI / 2);
		Double velocidadGiro = Orbita2D.valorAleatorioEntre(0.05, 0.1);
		Double angulo = 0.;

		return Planeta.of(nombre, diametro, a, excentricidad, anguloEjeMayor, dextrogiro, velocidadGiro, angulo,estrella);
	}

	
	public static Planeta of(String nombre, Integer diametro, Double a, Double excentricidad, Double anguloEjeMayor,
			Boolean dextrogiro, Double velocidadGiro, Double angulo, Estrella estrella) {
		return new Planeta(nombre, diametro, a, excentricidad, anguloEjeMayor, dextrogiro, velocidadGiro, angulo,estrella);
	}

	protected Estrella estrella;
	protected Double velocidadGiro;
	protected Double angulo;
	protected Orbita2D orbita;
	protected Punto2D lastCoordenadas;

	private Planeta(String nombre, Integer diametro, Double a, Double excentricidad, Double anguloEjeMayor,
			Boolean dextrogiro, Double velocidadGiro, Double angulo, Estrella estrella) {
		super(nombre, diametro, Color.MAGENTA, estrella.universo);
		Preconditions.checkArgument(excentricidad>=0 && excentricidad <=1.,String.format("La excentricidad es %.2f",excentricidad));
		this.estrella = estrella;
		this.velocidadGiro = dextrogiro ? velocidadGiro : -velocidadGiro;
		this.angulo = angulo;
		this.orbita = Orbita2D.ofExcentricidad(a,excentricidad, anguloEjeMayor, this.estrella.coordenadas());
		this.lastCoordenadas = this.orbita.punto(this.angulo);
	}

	@Override
	public String toString() {
		return "Planeta [nombre=" + nombre + ", diametro=" + diametro + ", color=" + color.toString() + "\norbita="
				+ orbita + "\n  + estrella=" + estrella + ", velocidadGiro=" + velocidadGiro + ", angulo=" + angulo
				+ "]";
	}

	@Override
	public Punto2D coordenadas() {
		return this.lastCoordenadas;
	}

	@Override
	public void unPaso() {
		this.angulo = this.angulo + this.velocidadGiro; 
		this.lastCoordenadas = this.orbita.punto(this.angulo);
	}

	@Override
	public void cambiaPropiedades() {
	}

}
