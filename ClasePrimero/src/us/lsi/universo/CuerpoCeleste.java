package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;

public abstract class CuerpoCeleste {
	public static enum Location {
		Inside, Left, Right, Up, Down, OutSide
	}

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
		return this.diametro;
	}

	public String nombre() {
		return this.nombre;
	}
	
	public Universo2D universo() {
		return this.universo;
	}
	
	public Color color() {
		return this.color;
	}
	
	public abstract Punto2D coordenadas();
	public abstract void unPaso();
	public abstract void cambiaPropiedades();

	public double distanciaA(CuerpoCeleste cuerpo) {
		Double distanciaCentros = this.coordenadas().distanciaA(cuerpo.coordenadas());
		Double d = distanciaCentros - this.diametro / 2 - cuerpo.diametro() / 2;
		return d;
	}
	
	public void mostrarCuerpoCeleste() {
		this.universo().mostrarCuerpoCeleste(this);
	}
   
	public void ocultarCuerpoCeleste() {
		this.universo().ocultarCuerpoCeleste(this);
	}
    
	public Location location() {
		Integer minimoX = this.coordenadas().x().intValue() - (this.diametro / 2);
		Integer maximoX = this.coordenadas().x().intValue() + (this.diametro / 2);
		Integer minimoY = this.coordenadas().y().intValue() - (this.diametro / 2);
		Integer maximoY = this.coordenadas().y().intValue() + (this.diametro / 2);
		CuerpoCeleste.Location r = null;
		if (minimoX > 0 && maximoX < this.universo.xMax() && minimoY > 0 && maximoY < this.universo.yMax())
			r = CuerpoCeleste.Location.Inside;
		else if (minimoX < 0 && minimoY > 0 && maximoY < this.universo.yMax())
			r = CuerpoCeleste.Location.Left;
		else if (maximoX > this.universo.xMax() && minimoY > 0 && maximoY < this.universo.yMax())
			r = CuerpoCeleste.Location.Right;
		else if (minimoX > 0 && maximoX < this.universo.xMax() && minimoY < 0)
			r = CuerpoCeleste.Location.Up;
		else if (minimoX > 0 && maximoX < this.universo.xMax() && maximoY > this.universo.yMax())
			r = CuerpoCeleste.Location.Down;
		else
			r = CuerpoCeleste.Location.OutSide;
		return r;
	}
    
	public void comprobarPosicion() {
		CuerpoCeleste.Location p = this.location();
		assert p.equals(CuerpoCeleste.Location.Inside): String.format("El cuerpo est� fuera de la ventana %s", p);
	}
	
	public Boolean esVisible() {
		return this.location().equals(CuerpoCeleste.Location.Inside);
	}

	public void mover() {
		if (this.esVisible()) {
			this.ocultarCuerpoCeleste();	
		}		
        this.unPaso();
        this.cambiaPropiedades();
        if (this.esVisible()) {
			this.mostrarCuerpoCeleste();
		}  
	}
}
