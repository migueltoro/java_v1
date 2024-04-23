package us.lsi.aeropuerto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.aeropuerto.OcupacionVuelo.Ocv;
import us.lsi.tools.File2;

public class OcupacionesVuelos {
	
	static OcupacionesVuelos getorOcupacionesVuelos = null;
	
	public static OcupacionesVuelos of() {
		if(OcupacionesVuelos.getorOcupacionesVuelos == null)
			OcupacionesVuelos.getorOcupacionesVuelos = OcupacionesVuelos.parse("aeropuertos/ocupacionesVuelos.csv");
		return OcupacionesVuelos.getorOcupacionesVuelos;
	}

	public static OcupacionesVuelos parse(String fichero) {
		Set<OcupacionVuelo> r = File2.streamDeFichero(fichero,"Windows-1252")
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toSet());
		return new OcupacionesVuelos(r);
	}

	private Set<OcupacionVuelo> ocupaciones;
	private Map<Ocv,OcupacionVuelo> map;

	public OcupacionesVuelos(Set<OcupacionVuelo> ocupaciones) {
		super();
		this.ocupaciones = ocupaciones;
		this.map = this.ocupaciones.stream().collect(Collectors.toMap(ov->ov.key(),ov->ov));
	}

	public Set<OcupacionVuelo> todas() {
		return this.ocupaciones;
	}
	
	public OcupacionVuelo get(Integer i) {
		return this.ocupaciones.stream().toList().get(i);
	}
	
	public OcupacionVuelo ocupacionVuelo(String codigoVuelo, LocalDateTime fecha) {
		return this.map.getOrDefault(Ocv.of(codigoVuelo,fecha),null);
	}
	
	
	public Integer size() {
		return this.ocupaciones.size();
	}
	
}
