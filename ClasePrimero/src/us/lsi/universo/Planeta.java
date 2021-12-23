package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;

public class Planeta extends CuerpoCeleste {
	
	
	public static Planeta of(String nombre, Estrella estrella) {
		Integer diametro = 15;
		Orbita2D orbita = Orbita2D.random();
		Double angulo = 0.;
		return Planeta.of(nombre, diametro, Color.MAGENTA, angulo, orbita,estrella);
	}

	
	public static Planeta satelite(String nombre, CuerpoCeleste planeta) {
		Integer diametro = 10;
		Orbita2D orbita = Orbita2D.random2();
		Double angulo = 0.;
		return  of(nombre, diametro, Color.GREEN, angulo, orbita,  planeta);
	}

	public static Planeta of(String nombre, Integer diametro, Color color, Double angulo, Orbita2D orbita,
			CuerpoCeleste centroOrbita) {
		return new Planeta(nombre, diametro, color, angulo,orbita,centroOrbita);
	}

	protected CuerpoCeleste centroOrbita;
	protected Double angulo;
	protected Orbita2D orbita;
	protected Punto2D coordenadas;

	
	private Planeta(String nombre, Integer diametro, Color color, Double angulo, Orbita2D orbita, CuerpoCeleste centroOrbita) {
		super(nombre, diametro, color, centroOrbita.universo());	
		this.angulo = angulo;
		this.orbita = orbita;
		this.centroOrbita = centroOrbita;
		this.coordenadas =  this.centroOrbita.coordenadas().add(this.orbita.radioVector(this.angulo));
	}
	
	@Override
	public Punto2D coordenadas() {
		return this.coordenadas;	
	}

	@Override
	public void unPaso() {
		this.angulo = this.angulo + this.orbita.velocidadAngular(angulo);
		Vector2D v = this.orbita.radioVector(this.angulo);
		this.coordenadas =  this.centroOrbita.coordenadas().add(v);
	}

	@Override
	public void cambiaPropiedades() {
	}

}
