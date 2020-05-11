package us.lsi.geometria;

public class Recta2D {
	
	public static Recta2D of(Punto2D punto, Vector2D vector) {
		return new Recta2D(punto, vector);
	}
	
	public static Recta2D of(Punto2D p1, Punto2D p2) {
		return new Recta2D(p1, p2.minus(p1));
	}

	private Punto2D punto;
	private Vector2D vector;
	
	
	private Recta2D(Punto2D punto, Vector2D vector) {
		super();
		this.punto = punto;
		this.vector = vector;
	}

	public Vector2D getVector() {
		return this.vector;
	}
	
	public Vector2D unitario() {
		return this.vector.unitario();
	}

	public Punto2D punto(Double lambda) {
		return this.punto.add(this.vector.multiply(lambda));
	}

	@Override
	public String toString() {
		return Segmento2D.of(this.punto(-200.), this.punto(200.)).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((punto == null) ? 0 : punto.hashCode());
		result = prime * result + ((vector == null) ? 0 : vector.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recta2D other = (Recta2D) obj;
		if (punto == null) {
			if (other.punto != null)
				return false;
		} else if (!punto.equals(other.punto))
			return false;
		if (vector == null) {
			if (other.vector != null)
				return false;
		} else if (!vector.equals(other.vector))
			return false;
		return true;
	}
	

}

