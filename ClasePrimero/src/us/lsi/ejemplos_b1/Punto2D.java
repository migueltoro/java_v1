package us.lsi.ejemplos_b1;

public record Punto2D(Double x,Double y) implements Comparable<Punto2D> {
	
	private static Punto2D cero = Punto2D.of(0.,0.);
	
	public static Punto2D origen(){
		return Punto2D.cero;
	}
	
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x, y);
	}

	public static Punto2D parse(String text) {
		String text2 = text.substring(1, text.length()-1);
		String[] campos = text2.split(",");
		return new Punto2D(Double.parseDouble(campos[0]), Double.parseDouble(campos[1]));
	}
    
    public Cuadrante cuadrante(){
		Cuadrante c;
		if(this.x() >=0 && this.y() >=0){
			c = Cuadrante.PRIMER_CUADRANTE;
		} else if(this.x() <=0 && this.y() >=0){
			c = Cuadrante.SEGUNDO_CUADRANTE;
		} else if(this.x() <=0 && this.y() <=0){
			c = Cuadrante.TERCER_CUADRANTE;
		} else {
			c = Cuadrante.CUARTO_CUADRANTE;
		}
		return c;
	}
    
    public static Double distanciaA(Punto2D p1, Punto2D p2) {
    	Double dx = p1.x()-p2.x();
    	Double dy = p1.y()-p2.y();
		return Math.sqrt(dx*dx+dy*dy);
    }
    
    public Double distanciaA(Punto2D p) {
    	Double dx = this.x()-p.x();
    	Double dy = this.y()-p.y();
		return Math.sqrt(dx*dx+dy*dy);
	}
    
	public Double distanciaAlOrigen() {
		return distanciaA(Punto2D.origen());
	} 
	
	@Override
	public int compareTo(Punto2D other) {
		return this.distanciaAlOrigen().compareTo(other.distanciaAlOrigen());
	}

		
	public String toString() {
    	return String.format("(%.2f,%.2f)",this.x(),this.y());
    }
	
	public static void main(String[] args) {
		Punto2D p1 = Punto2D.of(200.00,100.00);
		Punto2D p2 = Punto2D.of(1000.00,400.00);
		System.out.println(p1.distanciaA(p2));
		System.out.println(distanciaA(p1,p2));
	}
	

}

