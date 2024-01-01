package us.lsi.coordenadas;

import java.util.Locale;

public class TestCoordenadas3D {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Coordenadas3D c1 = Coordenadas3D.of(36.743941297754645, -5.163318822160363, 0.8);
//		Coordenadas3D c2 = Coordenadas3D.of(36.743941297754645, -5.163318822160363, 1.0);
		Coordenadas3D c3 = Coordenadas3D.of(36.74342648126185, -5.16417670994997, 0.4);
//		Coordenadas3D c4 = Coordenadas3D.of(36.74274251796305, -5.165218245238066, 1.2);
		System.out.println(c1.distancia(c3));
		System.out.println(Coordenadas3D.distancia(c1,c3));
	}

}
