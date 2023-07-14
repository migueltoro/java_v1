package us.lsi.tools;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.util.function.Function;

import us.lsi.ejemplos_b1_tipos.Vector2D;



public class Ventana {
	
	public static Ventana of(String title, Integer xMax, Integer yMax, Double escala, Color bg, Color fg) {
		return new Ventana(title, xMax, yMax, escala, bg, fg);
	}
	
	public static Ventana of(String title, Double escala, Color bg, Color fg) {
		return new Ventana(title, 1000, 600, escala, bg, fg);
	}

	
	public record DoublePair(Double x, Double y) {
		
		public static DoublePair of(Double x, Double y) {
			return new Ventana.DoublePair(x,y);
		}
		
		public DoublePair transform(Ventana v) {
			return new Ventana.DoublePair(v.xt.apply(x),v.yt.apply(y));
		}
		
	}
	
	/**
	 * La anchura pantalla en pixels 
	 */
	public Integer xMax;
	/**
	 * La altura pantalla en pixels
	 */
	public Integer yMax;
	/**
	 * La distancia del centro de coordenadas al margen izquierdo de la ventana
	 */
	public Integer xCentro;
	/**
	 * La distancia del centro de coordenadas al margen superior de la ventana
	 */
	public Integer yCentro;
	/**
	 * La escala de transformación del objetos a proyectarse en la ventana
	 */
	public Double escala;
	
	public Vector2D centro;
	
	private Function<Double,Double> xt;
	
	private Function<Double,Double> yt;
	
	public void draw(Shape sp) {
		this.canvas.draw(sp);
	}
	
	public void fill(Shape sp) {
		this.canvas.fill(sp);
	}

	private Canvas canvas;
	
	private Ventana(String title,Integer xMax, Integer yMax, Double escala, Color bg, Color fg) {
		this.canvas = Canvas.of(title,xMax,yMax,bg);
		this.xMax = xMax;
		this.yMax = yMax;
		this.escala = escala;
		this.xCentro = xMax/2;
		this.yCentro = yMax/2;
		this.centro = Vector2D.of((double)this.xCentro,(double)this.yCentro);
		this.xt = x->this.escala*x+this.xCentro;
		this.yt = y->-this.escala*y+this.yCentro;
		axes();
		this.canvas.setForegroundColor(fg);
		Graphics2D gc = this.canvas.graphic();
		gc.setStroke(new BasicStroke(2));	
	}
	
	public DoublePair transform(Double x, Double y) {
		return new DoublePair(xt.apply(x),yt.apply(y));
	}
	
	/**
     * Sets the foreground color of the Canvas.
     * @param  newColor   the new color for the foreground 
     */
    public void setForegroundColor(Color newColor) {
        this.canvas.setForegroundColor(newColor);
    }

    /**
     * Sets the background color of the Canvas.
     * @param  newColor   the new color for the background 
     */
    public void setBackgroundColor(Color newColor) { 
    	this.canvas.setBackgroundColor(newColor);
    }

	public void axes() {
		this.canvas.drawString(String.format("%.0f", this.yCentro/this.escala), this.xCentro+2, 15);
		this.canvas.drawString(String.format("%.0f", -(this.yMax - this.yCentro)/this.escala), this.xCentro+2, this.yMax - 10);
		this.canvas.drawString(String.format("%.0f", (this.xMax - this.xCentro)/ this.escala), this.xMax - 40,this.yCentro - 2);
		this.canvas.drawString(String.format("%.0f", -this.xCentro / this.escala), 2, this.yCentro - 2);
		this.canvas.drawLine(this.xCentro,0,this.xCentro,this.yMax);
		this.canvas.drawLine(0,this.yCentro,this.xMax,this.yCentro);
	}

	@Override
	public String toString() {
		return "Ventana [xMax=" + xMax + ", yMax=" + yMax + ", xCentro=" + xCentro + ", yCentro=" + yCentro
				+ ", escala=" + escala + ", centro=" + centro + "]";
	}

}
