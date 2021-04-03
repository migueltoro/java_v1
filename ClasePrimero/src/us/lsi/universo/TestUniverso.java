package us.lsi.universo;

import java.awt.Color;
import java.util.Locale;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Vector2D;


public class TestUniverso {
		
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		try {
			Universo universo = Universo.empty("Universo", 1200, 600, Color.BLUE);
			Estrella sol = Estrella.of("Sol", Punto2D.of(100., 100.), 30, universo);
			Estrella polar = Estrella.of("Polar", Punto2D.of(1100., 500.), 40, universo);
			PlanetaCircular tierra = PlanetaCircular.of("Tierra", Punto2D.of(120., 130.), 15, true, 0.2, sol);
			PlanetaElipsoidal marte = PlanetaElipsoidal.of("Marte", 10, false, 0.1, 200.,Math.PI/9,150.,Math.PI/9, polar);
			System.out.println(marte.excentricidad);
			Cometa mc = Cometa.of("Cometa", Punto2D.of(100., 100.), 10, Vector2D.of(1., 1.5), 10., universo);
			Cometa me = CometaErratico.of("Erratico", Punto2D.of(90., 100.), 10, Vector2D.of(1., 1.5), 10., universo);
			Cometa ma = CometaAcelerado.of("Acelerado", Punto2D.of(110., 100.), 10, Vector2D.of(1., 1.5), 10., 0.1, universo);
			Cometa.of("HaciaElSol",Punto2D.of(50., 50.),10,Vector2D.ofRadianes(1.,7*Math.PI/4),10.,universo);  // Directo al sol
//			
			System.out.println(universo.cuerposCelestes);
//			System.out.printf("%.2f\n", marte.distanciaA(sol));
//			System.out.printf("%.2f\n", universo.distanciaMinima());
			universo.simular();
		} catch (Exception e) {
			System.out.println("Simulacion terminada:\n   " + e);
		}

//			System.out.println(tierra);
//			tierra.mover();
//			System.out.println(tierra);

	}
}

