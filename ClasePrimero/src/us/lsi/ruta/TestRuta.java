package us.lsi.ruta;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.GraphicsMaps;
import us.lsi.tools.GraphicsMaps.GraphicType;

public class TestRuta {

	public static void main(String[] args) {
		Ruta r = Ruta.parse("ficheros/ruta.csv");
//		System.out.println(r.toString2());
		System.out.println(r.desnivelCrecienteAcumulado());
//		System.out.println(r.getDesnivelDecrecienteAcumulado());
//		System.out.println(r.getLongitud());
		List<Coordenadas2D> coordenadas = r.marcas().stream().map(m->m.coordenadas().to2D()).collect(Collectors.toList());
		GraphicsMaps.of(GraphicType.Bing).polyline("ficheros/BingPolylineOut.html",coordenadas);
	}

}
