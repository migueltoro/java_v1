package us.lsi.tools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import us.lsi.coordenadas.Coordenadas2D;

public class GraphicsBingMaps extends AbstractGraphicsMaps implements GraphicsMaps {
	
	
	public GraphicsBingMaps() {}

	private static Integer n = -1;
	
	public String toPoint(Coordenadas2D coordenadas) {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		DecimalFormat formateador = new DecimalFormat("####.######",simbolos);
		String lat = formateador.format(coordenadas.latitud());
		String lng = formateador.format(coordenadas.longitud());
		return String.format("new Microsoft.Maps.Location(%s,%s)",lat,lng);
	}

	public String toMarker(String color, String text,Coordenadas2D coordenadas) {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		DecimalFormat formateador = new DecimalFormat("####.######",simbolos);
		String lat = formateador.format(coordenadas.latitud());
		String lng = formateador.format(coordenadas.longitud());
		n++;
		return String.format(" var pin%d = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(%s,%s), {\r\n" + 
				"color: '%s'," +
				"text: '%s'\r\n });\r\n" + 
				"map.entities.push(pin%d);",n,lat,lng,color,text,n);
	}

	@Override
	public String getKey() {
		return File2.lineasDeFichero("C:/Users/migueltoro/OneDrive - UNIVERSIDAD DE SEVILLA/Escritorio/Jars/Keys/privateBing.txt").get(0);
	}

	@Override
	protected String getPolylinePattern() {
		return File2.text("resources/BingPolylinePattern.html");
	}

	@Override
	protected String getMarkersPattern() {
		return File2.text("resources/BingMarkersPattern.html");
	}

}
