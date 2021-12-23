package us.lsi.universo;

import java.awt.Color;
import java.util.Locale;

import us.lsi.geometria.Punto2D;


public class TestUniverso {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Universo2D universo = Universo2D.empty("Universo", 1200, 600, Color.blue);
		Estrella sol = Estrella.of("Sol", Punto2D.of(300., 200.), 20, universo);
		Estrella polar = Estrella.of("Polar", Punto2D.of(1000., 400.), 20, universo);
		Planeta tierra = Planeta.of("Tierra", sol);
		Planeta.satelite("Luna", tierra);
		Planeta.of("Marte", sol);
		Planeta.of("Zeus", polar);
		Cometa.of("Cometa1", universo);
		CometaErratico.of("Erratico", universo);
		CometaAcelerado.of("Acelerado", universo);
		Cometa.of("Cometa2", universo);
		universo.simular();

	}
}

