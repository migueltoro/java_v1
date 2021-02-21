package us.lsi.geometria;

import java.awt.Color;
import java.util.Locale;

public class TestGeometria {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Punto2D p = Punto2D.parse("(2.0,3.1");
		System.out.println(p);
		Circulo2D c1 = Circulo2D.of(Punto2D.of(0.,0.),60.);
		Circulo2D c2 = Circulo2D.of(Punto2D.of(60.,70.),30.);
		Circulo2D c3 = Circulo2D.of(Punto2D.of(-50.,150.),100.);
		Punto2D pp0 = Punto2D.of(0.,0.);
		Punto2D pp1 = Punto2D.of(50.,0.);
		Punto2D pp2 = Punto2D.of(-50.,0.);
		Punto2D pp3 = Punto2D.of(0.,-50.);
		Punto2D p1 = Punto2D.of(-50.,-50.);
		Punto2D p2 = Punto2D.of(50.,-50.);
		Punto2D p3 = Punto2D.of(50.,50.);
		Punto2D p4 = Punto2D.of(-50.,50.);
		Punto2D p5 = Punto2D.of(500.,600.);
		Punto2D p6 = Punto2D.of(100.,400.);
		Poligono2D pl = Poligono2D.ofPuntos(p1,p2,p3,p4);
		Poligono2D pl0 = pl.rota(p2, Math.PI/3).traslada(Vector2D.ofXY(100.,200.));
		Poligono2D pl2 = pl.homotecia(Punto2D.of(500.,-500.), 0.5);
		Recta2D r = Recta2D.of(p5, p6);	
		Poligono2D pl3 = pl2.simetrico(r);
		System.out.println(pl);		
		Segmento2D s = Segmento2D.of(p5, p6);
		Punto2D p0 = p3.proyectaSobre(r);
		Poligono2D cd = Poligono2D.cuadrado(Punto2D.of(50., 50.),Vector2D.ofXY(20., 20.));
		Poligono2D tr = Poligono2D.trianguloEquilatero(Punto2D.of(200., 200.), Vector2D.ofXY(150., 100.));
		Poligono2D tr2 = tr.traslada(Vector2D.ofXY(0., 200.));
		System.out.println(tr);
		System.out.println(tr2);		
		System.out.println(p0);
		System.out.println(p3);
		System.out.println(Vector2D.ofXY(2.0, 1.0).proyectaSobre(Vector2D.ofXY(0.,2.)).toString());
		Segmento2D pc = c3.proyectaSobre(r);
		AgregadoGeometrico2D a = AgregadoGeometrico2D.of(c1,c2,c3,pl,pl2,s);
		Ventana.escala = 1.;
		Ventana.draw(a,Color.BLUE);

	}

}
