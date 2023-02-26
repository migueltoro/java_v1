package us.lsi.tools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import us.lsi.coordenadas.Coordenadas2D;

public class GraphicsGoogleMaps extends AbstractGraphicsMaps implements GraphicsMaps {
	
	public GraphicsGoogleMaps() {}
	
	private static Integer n = -1;
	@Override
	public String toMarker(String color, String text, Coordenadas2D coordenadas) {
		String url = "\"http://maps.google.com/mapfiles/ms/icons/";
		  url += color + "-dot.png\"";
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		DecimalFormat formateador = new DecimalFormat("####.######",simbolos);
		String lat = formateador.format(coordenadas.latitud());
		String lng = formateador.format(coordenadas.longitud());
		n++;
		return String.format("marker%d = new google.maps.Marker({\r\n" + 
				"    map: map,\r\n" + 
				"    position: {lat: %s, lng: %s},\r\n" + 
				"    title: '%s' ,\r\n" +
				"    icon: {\r\n url: " + url+ "}\r\n" + 
				"  });",n,lat,lng,text);
	}

	@Override
	public String toPoint(Coordenadas2D coordenadas) {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		DecimalFormat formateador = new DecimalFormat("####.######",simbolos);
		String lat = formateador.format(coordenadas.latitud());
		String lng = formateador.format(coordenadas.longitud());
		return String.format("{lat: %s, lng: %s}",lat,lng);
	}

	@Override
	public String getKey() {
		return File2.lineasDeFichero("C:/Users/migueltoro/OneDrive - UNIVERSIDAD DE SEVILLA/Escritorio/Jars/Keys/privateGoogle.txt").get(0);
	}
	

	@Override
	protected String getPolylinePattern() {
		return File2.text("resources/GooglePolylinePattern.html");
	}
	
	@Override
	protected String getMarkersPattern() {
		return File2.text("resources/GoogleMarkersPattern.html");
	}

	@Override
	public String toString() {
		return "GraphicsGoogleMaps";
	}
	
	
}
