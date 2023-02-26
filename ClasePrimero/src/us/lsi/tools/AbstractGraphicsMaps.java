package us.lsi.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.coordenadas.Coordenadas2D;



public abstract class AbstractGraphicsMaps implements GraphicsMaps{
	
	public abstract String toPoint(Coordenadas2D coordenadas);
	public abstract String toMarker(String color, String text, Coordenadas2D coordenadas);
	protected abstract String getKey();
	protected abstract String getPolylinePattern();
	protected abstract String getMarkersPattern();
	
	@Override
	public void polyline(String fileOut, List<Coordenadas2D> ubicaciones) {
		Coordenadas2D center = Coordenadas2D.center(ubicaciones);
		String result = getPolylinePattern();
		String polylineText = ubicaciones.stream().map(x->toPoint(x)).collect(Collectors.joining(",","\n[", "]\n"));
		String centerText = toPoint(center);
		String markerCenterText = toMarker("red","C",center);
		String markerBeginText = toMarker("red","S",ubicaciones.get(0));
		String markerEndText = toMarker("red","E",ubicaciones.get(ubicaciones.size()-1));
		String keyText = getKey();
		Map<String,String> reglas = new HashMap<>();
		reglas.put("center",centerText);
		reglas.put("markerbegin",markerBeginText);
		reglas.put("markerend",markerEndText);
		reglas.put("markercenter",markerCenterText);
		reglas.put("polyline",polylineText);
		reglas.put("key",keyText);
		result = String2.transform(result,reglas);
		File2.write(fileOut,result);
	}

	@Override
	public void markers(String fileOut, String markerColor, List<Coordenadas2D> ubicaciones) {
		Coordenadas2D center = Coordenadas2D.center(ubicaciones);
		String result = getMarkersPattern();
		String markersText = ubicaciones.stream().map(x->toMarker(markerColor,"E",x)).collect(Collectors.joining("\n","\n", "\n"));
		String centerMarkerText = toMarker("red","C",center);
		String centerText = toPoint(center);
		String keyText = getKey();
		Map<String,String> reglas = new HashMap<>();
		reglas.put("center",centerText);
		reglas.put("markercenter",centerMarkerText);
		reglas.put("markers",markersText);
		reglas.put("key",keyText);
		result = String2.transform(result,reglas);
		File2.write(fileOut,result);
	}

}
