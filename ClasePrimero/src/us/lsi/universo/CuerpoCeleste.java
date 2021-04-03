package us.lsi.universo;

import java.awt.Color;

import us.lsi.geometria.Punto2D;
import us.lsi.universo.Universo.Position;


public abstract class CuerpoCeleste {
	protected final String nombre;
	protected Punto2D coordenadas;
	protected final Integer diametro;
	protected Color color;
	protected Position position;
	protected Universo universo;

	
	public CuerpoCeleste(String nombre, Punto2D coordenadas, Integer diametro, Color color, Universo universo) {
		this.nombre = nombre;
		this.coordenadas = coordenadas;
		this.color = color;
		this.diametro = diametro;
		this.universo = universo;
		this.universo.cuerposCelestes.add(this);
		universo.comprobarPosicion(this);
		this.position = this.universo.position(this);
	}

	@Override
	public String toString() {
		return this.nombre;
	}
	
	public Punto2D getCoordenadas() {
		return coordenadas;
	}

	public int getDiametro() {
		return diametro;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setCoordenadas(Punto2D coordenadas) {
		this.coordenadas = coordenadas;
		this.position = this.universo.position(this);
	}

	public double distanciaA(CuerpoCeleste cuerpo) {
		double distanciaCentros = this.getCoordenadas().distanciaA(cuerpo.getCoordenadas());
		return distanciaCentros - this.diametro / 2 - cuerpo.getDiametro() / 2;
	}
	
	public Boolean visible() {
		return this.position.equals(Position.Inside);
	}

	public void mover() {
		if (this.visible()) {
			universo.borrarCuerpoCeleste(this);	
		}	
        this.setCoordenadas(nuevasCoordenadas(this.universo.tiempo));
        this.cambiaPropiedades();
        if (this.visible()) {
			universo.pintarCuerpoCeleste(this);
		}  
	}
	
	public void cambiaPropiedades() {}
	
	public abstract Punto2D nuevasCoordenadas(Integer tiempo);
}
