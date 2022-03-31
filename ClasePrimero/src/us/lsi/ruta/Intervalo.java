package us.lsi.ruta;

import java.time.temporal.ChronoUnit;

public record Intervalo(Marca principio, Marca fin) {
	
	public static enum Type {Llano, Ascendente, Descendente}
	
	public static Intervalo of(Marca principio, Marca fin) {
		return new Intervalo(principio, fin);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", this.principio.toString(),this.fin.toString());
	}
	
	public Double desnivel() {
		return this.fin.coordenadas().altitud()-this.principio().coordenadas().altitud();
	}
	
	public Double longitud() {
		return this.principio().coordenadas().distancia(this.fin().coordenadas());
	}
	
	public Double tiempo() {
		return this.principio().time().until(this.fin().time(), ChronoUnit.SECONDS)/3600.;
	}
	
	public Double velocidad() {
		return this.longitud()/this.tiempo();
	}

	public Type type() {
		Type r;
		if(this.desnivel() == 0.) r = Type.Llano;
		else if(this.desnivel() > 0.) r = Type.Ascendente;
		else r = Type.Descendente;
		return r;
	}
	
}
