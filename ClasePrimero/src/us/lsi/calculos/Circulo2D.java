package us.lsi.calculos;



import us.lsi.tools.Preconditions;


public record Circulo2D(Punto2D centro,Double radio){
	
	public static Circulo2D of(Punto2D centro, Double radio) {
		Preconditions.checkArgument(radio>=0, String.format("El radio debe ser mayor o igual a cero y es %.2f",radio));
		return new Circulo2D(centro, radio);
	}
	
	public Double area() {
		return Math.PI*this.radio*this.radio;
	}

	public Double longitud() {
		return 2*Math.PI*this.radio;
	}

	@Override
	public String toString() {
		return String.format("(%s,%.2f)",this.centro,this.radio);
	}
}

