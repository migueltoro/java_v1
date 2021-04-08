package us.lsi.tools;

import java.util.List;

import us.lsi.coordenadas.Coordenadas2D;

public interface GraphicsMaps {
	
	public enum GraphicType{Bing,Google}
	
	public static GraphicsMaps of(GraphicType type) {
		GraphicsMaps g = null;
		switch(type) {
		case Bing: g = new GraphicsBingMaps(); break;
		case Google: g = new GraphicsGoogleMaps(); break;
		}
		return g;
	}
	/**
	 * Muestra un mapa con los marcadores en las coordenadas indicadas
	 * @param fileOut Fichero de salida 
	 * @param markerColor Color de los marcadores
	 * @param ubicaciones Coordenadas
	 */
	void markers(String fileOut, String markerColor, List<Coordenadas2D> ubicaciones);
	
	/**
	 * Muestra un mapa con una polilinea en las coordenadas indicadas
	 * @param fileOut Fichero de salida
	 * @param ubicaciones Coordenadas
	 */
	void polyline(String fileOut, List<Coordenadas2D> ubicaciones);
}
