package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;
import us.lsi.tools.StreamTools;

public class Aeropuertos {
	
	public static void leeAeropuertos(String fichero) {
		List<Aeropuerto> aeropuertos = FileTools.streamFromFile(fichero)
				.map(x -> Aeropuerto.parse(x))
				.collect(Collectors.toList());
		
		Aeropuertos.datos = new Aeropuertos(aeropuertos);
	}

	private List<Aeropuerto> aeropuertos;
	private Map<String,Aeropuerto> codigosAeropuertos;
	private Map<String,String> ciudadDeAeropuerto;  //codigoAeropuerto, ciudad
	private Map<String,Set<Aeropuerto>> aeropuertosEnCiudad; //ciudad, {codigosAeropuerto, ...} 
	private Integer numAeropuertos;
	
	private static Aeropuertos datos;
	
	public static Aeropuertos datos() {
		return datos;
	}

	private Aeropuertos(List<Aeropuerto> aeropuertos) {
		super();
		this.aeropuertos = aeropuertos;
		try {
			this.codigosAeropuertos = this.aeropuertos.stream()
					.collect(Collectors.toMap(Aeropuerto::codigo,x->x));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		try {
			this.ciudadDeAeropuerto = this.aeropuertos.stream()
					.collect(Collectors.toMap(Aeropuerto::codigo, Aeropuerto::ciudad));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		this.aeropuertosEnCiudad = StreamTools.groupingSet(this.aeropuertos.stream(),Aeropuerto::ciudad);
		this.numAeropuertos = this.ciudadDeAeropuerto.size();
	}
	
	public List<Aeropuerto> aeropuertos() {
		return aeropuertos;
	}

	public Map<String, Aeropuerto> codigosAeropuertos() {
		return codigosAeropuertos;
	}
	
	public Map<String, String> ciudadDeAeropuerto() {
		return ciudadDeAeropuerto;
	}

	public Map<String, Set<Aeropuerto>> aeropuertosEnCiudad() {
		return aeropuertosEnCiudad;
	}

	public Integer numAeropuertos() {
		return numAeropuertos;
	}
	
	public void addAeropuerto(Aeropuerto v) {
		Preconditions.checkArgument(!this.codigosAeropuertos.containsKey(v.codigo()),
				String.format("La aerolina %s ya existe",v.toString()));
		this.aeropuertos.add(v);
		this.codigosAeropuertos.put(v.codigo(),v);
		this.ciudadDeAeropuerto.put(v.codigo(),v.ciudad());
		this.numAeropuertos +=1;
	}
	
	public void removeAeropuerto(Aeropuerto v) {
		if (this.codigosAeropuertos.containsKey(v.codigo())) {
			this.aeropuertos.remove(v);
			this.codigosAeropuertos.remove(v.codigo());
			this.ciudadDeAeropuerto.remove(v.codigo());
			this.numAeropuertos +=1;
		}
	}

}
