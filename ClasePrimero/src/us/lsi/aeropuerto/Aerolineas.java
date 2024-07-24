package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Aerolineas {
	
	private static Aerolineas gestoraAerolineas = null;
	
	public static Aerolineas of() {
		return Aerolineas.of("aeropuertos/aerolineas.csv");
	}
	
	public static Aerolineas of(String file) {
		if(Aerolineas.gestoraAerolineas == null)
			Aerolineas.gestoraAerolineas = new Aerolineas(file);
		return gestoraAerolineas;
	}
	
	public Aerolineas(String file) {
		super();
		this.aeroLineas = File2.streamDeFichero(file,"Windows-1252")
				.map(x ->Aerolinea.parse(x))
				.collect(Collectors.toSet());
		this.codigosAerolineas = 
				this.aeroLineas.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
	}

	private Set<Aerolinea> aeroLineas;
	
	private Map<String,Aerolinea> codigosAerolineas = null;

	public Optional<Aerolinea> aerolinea(String codigo) {
		return Optional.ofNullable(this.codigosAerolineas.get(codigo));
	}
	
	public Integer size() {
		return this.aeroLineas.size();
	}
	
	public Set<Aerolinea> todas() {
		return this.aeroLineas;
	}
	
	public Aerolinea get(Integer i) {
		return this.aeroLineas.stream().toList().get(i);
	}
	
	@Override
	public String toString() {
		return String.format("Aerolineas\n\t%s",this.aeroLineas.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}

}
