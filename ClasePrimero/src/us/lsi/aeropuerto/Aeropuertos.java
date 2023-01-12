package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class Aeropuertos {
	
	private static Aeropuertos faeropuertos = null;
	
	public static Aeropuertos of() {
		if(Aeropuertos.faeropuertos == null)
			Aeropuertos.faeropuertos = Aeropuertos.of("ficheros_aeropuertos/aeropuertos.csv");
		return faeropuertos;
	}
	
	public static Aeropuertos of(String fichero) {
		List<Aeropuerto> aeropuertos = FileTools.streamDeFichero(fichero,"Windows-1252")
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
		this.codigosAeropuertos = 
				this.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a));;
		this.ciudadDeAeropuerto = 
				this.aeropuertos.stream().collect(Collectors.toMap(a->a.codigo(),a->a.ciudad()));
		this.aeropuertosEnCiudad= this.aeropuertos.stream()
			       .collect(Collectors.groupingBy(a->a.ciudad(),Collectors.toSet()));
	}


	public String string() {
		return String.format("Aeropuertos\n\t%s",this.aeropuertos.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}
	
	public Aeropuerto aeropuerto(String codigo) { //codigoAropueto, Aeropuerto
		return this.codigosAeropuertos.get(codigo);
	}

	public String ciudadDeAeropuerto(String codigo) {  //codigoAeropuerto, ciudad
		return ciudadDeAeropuerto.get(codigo);
	}
	
	public Set<Aeropuerto> aeropuertosEnCiudad(String ciudad) { //ciudad, {codigosAeropuerto, ...} 
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
