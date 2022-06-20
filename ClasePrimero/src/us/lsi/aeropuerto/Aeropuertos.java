package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class Aeropuertos {
	
	private static Aeropuertos faeropuertos = null;
	
	public static Aeropuertos get() {
		return faeropuertos;
	}
	
	public static Aeropuertos leeAeropuertos(String fichero) {
		List<Aeropuerto> aeropuertos = FileTools.streamFromFile(fichero)
				.map(x -> Aeropuerto.parse(x))
				.collect(Collectors.toList());	
		Aeropuertos.faeropuertos =  new Aeropuertos(aeropuertos);
		return Aeropuertos.faeropuertos;
	}
	
	private List<Aeropuerto> aeropuertos;
	private Map<String,Aeropuerto> codigosAeropuertos = null;
	private Map<String,String> ciudadDeAeropuerto = null;
	private Map<String,Set<Aeropuerto>> aeropuertosEnCiudad= null;
	
	public Aeropuertos(List<Aeropuerto> aeropuertos) {
		super();
		this.aeropuertos = aeropuertos;
	}


	public String string() {
		return String.format("Aeropuertos\n\t%s",this.aeropuertos.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}
	
	
	public void addAeropuerto(Aeropuerto a) {
		this.aeropuertosEnCiudad = null;
		this.ciudadDeAeropuerto = null;
		this.codigosAeropuertos = null;
		this.aeropuertos.add(a);
	}
	
	public void removeAeropuerto(Aeropuerto a) {
		this.aeropuertosEnCiudad = null;
		this.ciudadDeAeropuerto = null;
		this.codigosAeropuertos = null;
		this.aeropuertos.remove(a);
	}

	
	public Aeropuerto aeropuerto(String codigo) { //codigoAropueto, Aeropuerto
		if(this.codigosAeropuertos == null)
			this.codigosAeropuertos = this.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
		return this.codigosAeropuertos.get(codigo);
	}
	
	
	
	public String ciudadDeAeropuerto(String codigo) {  //codigoAeropuerto, ciudad
		if(this.ciudadDeAeropuerto == null)
			this.ciudadDeAeropuerto = this.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a.ciudad()));
		return ciudadDeAeropuerto.get(codigo);
	}
	
	
	public Set<Aeropuerto> aeropuertosEnCiudad(String ciudad) { //ciudad, {codigosAeropuerto, ...} 
		if(this.aeropuertosEnCiudad == null)
			this.aeropuertosEnCiudad = this.aeropuertos.stream()
			       .collect(Collectors.groupingBy(a->a.ciudad(),Collectors.toSet()));
		return this.aeropuertosEnCiudad.get(ciudad);
	}
	
	public Integer size() {
		return this.aeropuertos.size();
	}

	public Stream<Aeropuerto> stream() {
		return this.aeropuertos.stream();
	}
	
	public Aeropuerto get(Integer i) {
		return this.aeropuertos.get(i);
	}


}
