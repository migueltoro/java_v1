package us.lsi.universo;

import java.awt.Color;
import java.util.Locale;

import us.lsi.geometria.Punto2D;


public class TestUniverso {
		
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
//		try {
			Universo2D universo = Universo2D.empty("Universo", 1200, 600, Color.blue);
			Estrella sol = Estrella.of("Sol", Punto2D.of(300., 300.), 20, universo);
			Estrella polar = Estrella.of("Polar", Punto2D.of(1100., 500.), 20, universo);
			Planeta tierra = Planeta.of("Tierra",true,sol);
			Satelite luna = Satelite.of("Luna", tierra);
			Planeta marte = Planeta.of("Marte", false,sol);
			Planeta zeus = Planeta.of("Zeus", true, polar);
			Cometa mc = Cometa.of("Cometa", universo);
			Cometa me = CometaErratico.of("Erratico", universo);
			Cometa ma = CometaAcelerado.of("Acelerado", universo);
			Cometa.of("HaciaElSol",universo);  // Directo al sol
//			
//			System.out.println(universo.cuerposCelestes);
//			System.out.printf("%.2f\n", marte.distanciaA(sol));
//			System.out.printf("%.2f\n", universo.distanciaMinima());
			universo.simular();
//		} catch (Exception e) {
//			System.out.println("Simulacion terminada:\n   " + e);
//		}

//			System.out.println(tierra);
//			tierra.mover();
//			System.out.println(tierra);

	}
}

