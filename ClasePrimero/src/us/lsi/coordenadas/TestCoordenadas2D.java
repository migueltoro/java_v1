package us.lsi.coordenadas;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TestCoordenadas2D {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Coordenadas2D c1 = Coordenadas2D.of(36.74991256557405,-5.147951105609536);
		Coordenadas2D c2 = Coordenadas2D.of(36.74274251796305,-5.165218245238066);
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(String.format("%.2f",c1.distancia(c2)));
		System.out.println(String.format("%.2f",Coordenadas2D.distancia(c1,c2)));
		List<Coordenadas2D> lc = Arrays.asList(
				c1,c2,
				Coordenadas2D.of(36.743941297754645,-5.163318822160363),
				Coordenadas2D.of(36.74342648126185,-5.16417670994997),
				Coordenadas2D.of(36.74274251796305,-5.165218245238066));
		System.out.println(lc);
		System.out.println(Coordenadas2D.center(lc));
	}

}
