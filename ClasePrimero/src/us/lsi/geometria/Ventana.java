package us.lsi.geometria;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Ventana extends JPanel {
  
	private static final long serialVersionUID = 865807501071430378L;
	
	private ShowObjeto objeto;
	private Color color = Color.BLACK;
	public Graphics2D g2;
	
	
	
	/**
	 * La anchura pantalla en pixels 
	 */
	public static Integer xMax = 1200;
	/**
	 * La altura pantalla en pixels
	 */
	public static Integer yMax = 700;
	/**
	 * La distancia del centro de coordenadas al margen izquierdo de la ventana
	 */
	public static Integer xCentro = xMax/2;
	/**
	 * La distancia del centro de coordenadas al margen superior de la ventana
	 */
	public static Integer yCentro = yMax/2;
	/**
	 * La escala de transformación del objetos a proyectarse en la ventana
	 */
	public static Double escala = 0.1;
	
	public static Vector2D centro = Vector2D.ofXY((double)Ventana.xCentro,(double)Ventana.yCentro);
	
	public static Function<Double,Double> xt = x->Ventana.escala*x+Ventana.xCentro;
	
	public static Function<Double,Double> yt = y->-Ventana.escala*y+Ventana.yCentro;
	
	public ObjetoGeometrico2D transform(ObjetoGeometrico2D o2d) {
		return o2d.transform(Ventana.xt,Ventana.yt);
	}
		
	public Ventana(ShowObjeto objeto,Color color) {
		super();
		this.objeto = objeto;
		this.color = color;
	}
	
	public void axes() {		
		Segmento2D xAxe = Segmento2D.of(Punto2D.of(-Ventana.xCentro/Ventana.escala,0.),Punto2D.of((Ventana.xMax-Ventana.xCentro)/Ventana.escala,0.));
		Segmento2D yAxe = Segmento2D.of(Punto2D.of(0.,-(Ventana.yMax-Ventana.yCentro)/Ventana.escala),Punto2D.of(0.,Ventana.yCentro/Ventana.escala));
		xAxe.show(this);
		yAxe.show(this);
	}
	
	public void escalaAxes(Graphics g) {
		g.drawString(String.format("%.0f", Ventana.yCentro / Ventana.escala), 2 + Ventana.xCentro, 15);
		g.drawString(String.format("%.0f", -(Ventana.yMax - Ventana.yCentro) / Ventana.escala), 2 + Ventana.xCentro,
				Ventana.yMax - 40);
		g.drawString(String.format("%.0f", (Ventana.xMax - Ventana.xCentro) / Ventana.escala), Ventana.xMax - 60,
				Ventana.yCentro - 2);
		g.drawString(String.format("%.0f", -Ventana.xCentro / Ventana.escala), 2, Ventana.yCentro - 2);
	}
	
	@Override
	public void paint(Graphics g) {
	  Graphics2D g2 = (Graphics2D) g;
	  this.g2 = g2;
	  g.setFont(new Font("Seqoe UI", Font.PLAIN, 16));
	  g.setColor(Color.DARK_GRAY);
	  g.drawString("Figuras Geométricas", 10, 20);
	  this.axes();
	  this.escalaAxes(g);
	  g.setColor(this.color);
	  g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	  this.objeto.show(this);
	}
  
	public static void draw(ShowObjeto objeto, Color color) {
	  JFrame f = new JFrame("Ventana de dibujo");
	  f.getContentPane().add(new Ventana(objeto,color));
	  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  f.setSize(Ventana.xMax,Ventana.yMax);
	  f.setLocation(0, 0);
	  f.setVisible(true);
  }

	
}
