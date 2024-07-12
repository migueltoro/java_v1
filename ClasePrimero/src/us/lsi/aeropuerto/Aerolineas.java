package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Aerolineas {
	
	private static Aerolineas gestoraAerolineas = null;
	
	public static Aerolineas of() {
		if(Aerolineas.gestoraAerolineas == null)
			Aerolineas.gestoraAerolineas = Aerolineas.parse("aeropuertos/aerolineas.csv");
		return gestoraAerolineas;
	}
	
	public static Aerolineas parse(String fichero) {
		Set<Aerolinea> datos = File2.streamDeFichero(fichero,"Windows-1252")
				.map(x ->Aerolinea.parse(x))
				.collect(Collectors.toSet());
		Aerolineas aerolineas = new Aerolineas(datos);
		return aerolineas;
	}
	
	public Aerolineas(Set<Aerolinea> aeroLineas) {
		super();
		this.aeroLineas = aeroLineas;
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
