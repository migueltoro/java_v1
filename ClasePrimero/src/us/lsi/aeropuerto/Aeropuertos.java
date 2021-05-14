package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;

public class Aeropuertos {
	
private static Aeropuertos datos;
	
	public static Aeropuertos getDatos() {
		return datos;
	}
	
	public static void leeAeropuertos(String fichero) {
		List<Aeropuerto> aeropuertos = FileTools.streamFromFile(fichero)
				.map(x -> Aeropuerto.parse(x))
				.collect(Collectors.toList());	
		Aeropuertos.datos = new Aeropuertos(aeropuertos);
	}
	
	private List<Aeropuerto> aeropuertos;
	private Map<String,Aeropuerto> codigosAeropuertos = null;
	private Map<String,String> ciudadDeAeropuerto = null;
	private Map<String,Set<Aeropuerto>> aeropuertosEnCiudad = null;

	public Aeropuertos(List<Aeropuerto> aeropuertos) {
		super();
		this.aeropuertos = aeropuertos;
	}

	public List<Aeropuerto> getAeropuertos() {
		return aeropuertos;
	}
	
	public void addAeropuerto(Aeropuerto a) {
		this.aeropuertos.add(a);
	}
	
	public void removeAeropuerto(Aeropuerto a) {
		this.aeropuertos.remove(a);
	}
	
	public Map<String,Aeropuerto> getCodigosAeropuertos() { //codigoAropueto, Aeropuerto
		if(this.codigosAeropuertos == null)
			this.codigosAeropuertos = this.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
		return this.codigosAeropuertos;
	}
	
	public Map<String,String> getCiudadDeAeropuerto() {  //codigoAeropuerto, ciudad
		if(ciudadDeAeropuerto == null)
		  this.ciudadDeAeropuerto = this.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a.ciudad()));
		return ciudadDeAeropuerto;
	}
	
	public Map<String,Set<Aeropuerto>> getAeropuertosEnCiudad() { //ciudad, {codigosAeropuerto, ...} 
		if(aeropuertosEnCiudad == null)
		   this.aeropuertosEnCiudad = this.aeropuertos.stream()
		       .collect(Collectors.groupingBy(a->a.ciudad(),Collectors.toSet()));
		return aeropuertosEnCiudad;
	}
	
	public Integer getNumAeropuertos() {
		return this.aeropuertos.size();
	}

	@Override
	public String toString() {
		return String.format("Aeropuertos\n\t%s",this.aeropuertos.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}


}
