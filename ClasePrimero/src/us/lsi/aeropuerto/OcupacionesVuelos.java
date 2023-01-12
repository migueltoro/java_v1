package us.lsi.aeropuerto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class OcupacionesVuelos {
	
	static OcupacionesVuelos focupacionesVuelos = null;
	
	public static OcupacionesVuelos of() {
		if(OcupacionesVuelos.focupacionesVuelos == null)
			OcupacionesVuelos.focupacionesVuelos = OcupacionesVuelos.of("ficheros_aeropuertos/ocupacionesVuelos.csv");
		return OcupacionesVuelos.focupacionesVuelos;
	}

	public static OcupacionesVuelos of(String fichero) {
		List<OcupacionVuelo> r = FileTools.streamDeFichero(fichero,"Windows-1252")
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toList());
		return new OcupacionesVuelos(r);
	}

	private List<OcupacionVuelo> ocupaciones;

	public OcupacionesVuelos(List<OcupacionVuelo> ocupaciones) {
		super();
		this.ocupaciones = ocupaciones;
	}

	public Stream<OcupacionVuelo> stream() {
		return this.ocupaciones.stream();
	}
	
	public OcupacionVuelo get(Integer i) {
		return this.ocupaciones.get(i);
	}
	
	public Integer size() {
		return this.ocupaciones.size();
	}
	
}
