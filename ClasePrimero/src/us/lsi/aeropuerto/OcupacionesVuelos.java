package us.lsi.aeropuerto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.aeropuerto.OcupacionVuelo.Ocv;
import us.lsi.tools.File2;

public class OcupacionesVuelos {
	
	static OcupacionesVuelos getorOcupacionesVuelos = null;
	
	public static OcupacionesVuelos of() {
		return OcupacionesVuelos.of("aeropuertos/ocupacionesVuelos.csv");
	}
	
	public static OcupacionesVuelos of(String file) {
		if(OcupacionesVuelos.getorOcupacionesVuelos == null)
			OcupacionesVuelos.getorOcupacionesVuelos = new OcupacionesVuelos(file);
		return OcupacionesVuelos.getorOcupacionesVuelos;
	}

	private Set<OcupacionVuelo> ocupaciones;
	private Map<Ocv,OcupacionVuelo> map;
	
	public OcupacionesVuelos(Set<OcupacionVuelo> ocupaciones) {
		super();
		this.ocupaciones = ocupaciones;
		this.map = this.ocupaciones.stream().collect(Collectors.toMap(ov->ov.key(),ov->ov));
	}

	private OcupacionesVuelos(String file) {
		super();
		this.ocupaciones = File2.streamDeFichero(file,"Windows-1252")
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toSet());
		this.map = this.ocupaciones.stream().collect(Collectors.toMap(ov->ov.key(),ov->ov));
	}

	public Set<OcupacionVuelo> todas() {
		return this.ocupaciones;
	}
	
	public OcupacionVuelo get(Integer i) {
		return this.ocupaciones.stream().toList().get(i);
	}
	
	public Optional<OcupacionVuelo> ocupacionVuelo(String codigoVuelo, LocalDateTime fecha) {
		return Optional.ofNullable(this.map.get(Ocv.of(codigoVuelo,fecha)));
	}
	
	
	public Integer size() {
		return this.ocupaciones.size();
	}
	
}
