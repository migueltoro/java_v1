package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Aeropuertos {
	
	private static Aeropuertos gestorAeropuertos = null;
	
	public static Aeropuertos of() {
		return Aeropuertos.of("aeropuertos/aeropuertos.csv");
	}
	
	public static Aeropuertos of(String file) {
		if(Aeropuertos.gestorAeropuertos == null)
			Aeropuertos.gestorAeropuertos = new Aeropuertos(file);
		return gestorAeropuertos;
	}
	
	private Set<Aeropuerto> aeropuertos;
	private Map<String,Aeropuerto> codigosAeropuertos = null;
	private Map<String,String> ciudadDeAeropuerto = null;
	private Map<String,Set<Aeropuerto>> aeropuertosEnCiudad= null;
	
	private Aeropuertos(String file) {
		super();
		this.aeropuertos = File2.streamDeFichero(file,"Windows-1252")
				.map(x -> Aeropuerto.parse(x))
				.collect(Collectors.toSet());
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
	
	public Optional<Aeropuerto> aeropuerto(String codigo) { //codigoAropueto, Aeropuerto
		return Optional.ofNullable(this.codigosAeropuertos.get(codigo));
	}

	public Optional<String> ciudadDeAeropuerto(String codigo) {  //codigoAeropuerto, ciudad
		return Optional.ofNullable(this.ciudadDeAeropuerto.get(codigo));
	}
	
	public Set<Aeropuerto> aeropuertosEnCiudad(String ciudad) { //ciudad, {codigosAeropuerto, ...} 
		return this.aeropuertosEnCiudad.get(ciudad);
	}
	
	public Integer size() {
		return this.aeropuertos.size();
	}

	public Set<Aeropuerto> todos() {
		return this.aeropuertos;
	}
	
	public Aeropuerto get(Integer i) {
		return this.aeropuertos.stream().toList().get(i);
	}


}
