package us.lsi.sevici;
import java.util.Locale;

import us.lsi.sevici.RedA.TipoImplementacion;
import us.lsi.tools.Map2;

public class TestRed {

	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Red.setTipoImplementacion(TipoImplementacion.Funcional);
		Red r = Red.parse("ficheros/estaciones.csv");
		System.out.println(r);
		System.out.println(r.estacion(250));
		System.out.println(r.estacion("CALLE DE SALVADOR ALLENDE"));
		System.out.println(Map2.toString(r.numeroDeEstacionesPorBicisDisponibles()));
		r.escribe(10,"ficheros/estaciones2.txt");
//		List<Coordenadas2D> ubicaciones = r.ubicacionEstacionesDisponibles(25);
//		GraphicsMaps.of(GraphicType.Bing).markers("ficheros/MarkersBingOut.html","green", ubicaciones);
	}

}
