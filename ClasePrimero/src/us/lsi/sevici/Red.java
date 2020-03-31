package us.lsi.sevici;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;

public class Red {
	
	public static Red parse(String fichero) {
		List<Estacion> estaciones = FileTools.fromFile("ficheros/estaciones.csv")
				.skip(1)
				.map(linea->Estacion.parse(linea))
				.collect(Collectors.toList());
		return new Red(estaciones);
	}
	
	private List<Estacion> estaciones;

	
	private Red(List<Estacion> estaciones) {
		super();
		this.estaciones = estaciones;
	}


	@Override
	public String toString() {
		return estaciones.stream().map(e->e.toString()).collect(Collectors.joining("\n"));
	}
	
	
	
}
