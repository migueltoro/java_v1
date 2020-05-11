package us.lsi.geometria;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.Preconditions;


public class Poligono2D implements ObjetoGeometrico2D {

	public static Poligono2D empty() {
		return new Poligono2D();
	}
	
	public static Poligono2D triangulo(Punto2D p1, Punto2D p2, Punto2D p3) {
		return new Poligono2D(p1, p2, p3);
	}
	
	public static Poligono2D trianguloEquilatero(Punto2D p1, Vector2D lado) {
		return new Poligono2D(p1, p1.add(lado), p1.add(lado.rota(Math.PI/3)));
	}
	
	public static Poligono2D cuadrado(Punto2D v, Vector2D lado) {
		return new Poligono2D(v, v.add(lado), v.add(lado).add(lado.ortogonal()),v.add(lado.ortogonal()));
	}
	
	public static Poligono2D rectangulo(Punto2D v, Vector2D base, Double altura) {
		return new Poligono2D(v, 
				v.add(base), 
				v.add(base).add(base.ortogonal().multiply(altura)),
				v.add(base.ortogonal().multiply(altura)));
	}

	public static Poligono2D ofPuntos(Punto2D... lp) {
		return new Poligono2D(lp);
	}
	
	public static Poligono2D ofPuntos(List<Punto2D> lp) {
		return new Poligono2D(lp);
	}

	public static Poligono2D rectanguloHorizontal(Punto2D p, Double base, Double altura) {
		Poligono2D r = Poligono2D.empty();
		r.addVertice(p);
		r.addVertice(p.add(Vector2D.ofXY(base, 0.)));
		r.addVertice(p.add(Vector2D.ofXY(base, altura)));
		r.addVertice(p.add(Vector2D.ofXY(0.,altura)));
		return r;
	}
	
	public static Poligono2D rectanguloHorizontal(Double xMin, Double xMax, Double yMin, Double yMax) {
		Poligono2D r = Poligono2D.empty();
		r.addVertice(Punto2D.of(xMin, yMin));
		r.addVertice(Punto2D.of(xMax, yMin));
		r.addVertice(Punto2D.of(xMax, yMax));
		r.addVertice(Punto2D.of(xMin, yMax));
		return r;
	}
	private List<Punto2D> vertices;
	
	private Poligono2D() {
		vertices = new ArrayList<>();
	}
	
	private Poligono2D(Punto2D p1, Punto2D p2, Punto2D p3) {
		vertices = new ArrayList<>();
		vertices.add(p1);
		vertices.add(p2);
		vertices.add(p3);
	}
	
	private Poligono2D(List<Punto2D> lp) {
		Preconditions.checkArgument(lp.size()>=3);
		this.vertices = new ArrayList<>(lp);
	}
		
	public Double getArea(){
		Integer n = this.getNumeroDeVertices();
		Double area = IntStream.range(1,n-1)
				.mapToDouble(i->this.getDiagonal(0,i).multiplicaVectorial(this.getLado(i)))
				.sum();
		return area/2;
	}
	
	private Poligono2D(Punto2D... lp) {
		vertices = new ArrayList<>();
		Preconditions.checkArgument(lp.length>=3);
		for(Punto2D p : lp){
			vertices.add(p);
		}
	}
	
	public void addVertice(Punto2D p){
		vertices.add(p);
	}
	
	public int getNumeroDeVertices(){
		return vertices.size();
	}

	public List<Punto2D> getVertices() {
		return Collections.unmodifiableList(vertices);
	}
	
	public Punto2D getVertice(Integer i) {
		Integer n = this.getNumeroDeVertices();
		Preconditions.checkElementIndex(i, n);
		return vertices.get(i);
	}
	
	public Vector2D getLado(Integer i) {
		Integer n = this.getNumeroDeVertices();
		Preconditions.checkElementIndex(i, n);
		return Vector2D.of(this.vertices.get(i),this.vertices.get((i+1)%n));
	}
	
	public Vector2D getDiagonal(Integer i, Integer j) {
		Integer n = this.getNumeroDeVertices();
		Preconditions.checkElementIndex(i, n);
		Preconditions.checkElementIndex(j, n);
		return Vector2D.of(this.vertices.get(i),this.vertices.get(j));
	}
	
	public Poligono2D rota(Punto2D p, Double angulo) {
		return Poligono2D.ofPuntos(
				this.vertices.stream()
				.map(x->x.rota(p,angulo))
				.collect(Collectors.toList()));
	}

	public Poligono2D traslada(Vector2D v) {
		return Poligono2D.ofPuntos(
				this.vertices.stream()
				.map(p->p.traslada(v))
				.collect(Collectors.toList()));
	}
	
	@Override
	public Poligono2D homotecia(Punto2D p, Double factor) {
		return Poligono2D.ofPuntos(this.vertices.stream().map(x->x.homotecia(p, factor)).collect(Collectors.toList()));
	}
	
	@Override
	public Poligono2D proyectaSobre(Recta2D r) {
		return Poligono2D.ofPuntos(this.vertices.stream().map(x->x.proyectaSobre(r)).collect(Collectors.toList()));
	}
	
	@Override
	public Poligono2D simetrico(Recta2D r) {
		return Poligono2D.ofPuntos(this.vertices.stream().map(x->x.simetrico(r)).collect(Collectors.toList()));
	}
	
	@Override
	public Poligono2D transform(Function<Double,Double> xt, Function<Double,Double> yt) {
		return Poligono2D.ofPuntos(this.vertices.stream().map(x->x.transform(xt,yt)).collect(Collectors.toList()));
	}
	
	@Override
	public void show(Ventana v) {
		v.show(this);
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((vertices == null) ? 0 : vertices.hashCode());
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
		Poligono2D other = (Poligono2D) obj;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.vertices.stream().map(p->p.toString()).collect(Collectors.joining(",", "(",")"));
	}
	
}
