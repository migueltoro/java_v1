package us.lsi.calculos;

public record Punto2D(Double x, Double y) implements Comparable<Punto2D>{
	
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x,y);
	}
	
	public static Punto2D parse(String text) {
		Integer n = text.length();
		text = text.substring(1,n-1);
		String[]  p = text.split(",");
		return Punto2D.of(Double.parseDouble(p[0]),Double.parseDouble(p[1]));
	}
	
	public Double distanciaAlOrigen() {
		return Math.sqrt(this.x()*this.x()+this.y()*this.y());
	}
	
	public Cuadrante cuadrante() {
		Cuadrante c;
		if (this.x() >= 0 && this.y() >= 0) {
			c = Cuadrante.PRIMER_CUADRANTE;
		} else if (this.x() <= 0 && this.y() >= 0) {
			c = Cuadrante.SEGUNDO_CUADRANTE;
		} else if (this.x() <= 0 && this.y() <= 0) {
			c = Cuadrante.TERCER_CUADRANTE;
		} else {
			c = Cuadrante.CUARTO_CUADRANTE;
		}
		return c;
	}
	
	@Override
	public String toString() {
    	return String.format("(%.2f,%.2f)",this.x(),this.y());
    }

	@Override
	public int compareTo(Punto2D other) {
		return this.distanciaAlOrigen().compareTo(other.distanciaAlOrigen());
	}
	
	
}

