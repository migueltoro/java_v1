package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.universo.Universo2D.Location;


public abstract class CuerpoCeleste {
	protected final String nombre;
	protected final Integer diametro;
	protected Color color;
	protected Universo2D universo;

	
	public CuerpoCeleste(String nombre, Integer diametro, Color color, Universo2D universo) {
		this.nombre = nombre;
		this.color = color;
		this.diametro = diametro;
		this.universo = universo;
		this.universo.cuerposCelestes.add(this);
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	public int diametro() {
		return diametro;
	}

	public String nombre() {
		return nombre;
	}
	
	public abstract Punto2D coordenadas();
	public abstract void unPaso();
	public abstract void cambiaPropiedades();

	public Location location() {
		return this.universo.location(this);
	}

	public double distanciaA(CuerpoCeleste cuerpo) {
		Double distanciaCentros = this.coordenadas().distanciaA(cuerpo.coordenadas());
		Double d = distanciaCentros - this.diametro / 2 - cuerpo.diametro() / 2;
		return d;
	}
	
	public Boolean esVisible() {
		return this.location().equals(Location.Inside);
	}

	public void mover() {
		if (this.esVisible()) {
			universo.borrarCuerpoCeleste(this);	
		}	
        this.unPaso();
        this.cambiaPropiedades();
        if (this.esVisible()) {
			universo.pintarCuerpoCeleste(this);
		}  
	}
}
