package us.lsi.aeropuerto;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Aerolineas {
	
	private static Aerolineas gestoraAerolineas = null;
	
	public static Aerolineas of() {
		return Aerolineas.of("");
	}
	
	public static Aerolineas of(String root) {
		if(Aerolineas.gestoraAerolineas == null)
			Aerolineas.gestoraAerolineas = 
				Aerolineas.parse(File2.absolute_path("aeropuertos/aerolineas.csv",root));
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

	public Aerolinea aerolinea(String codigo) {
		return this.codigosAerolineas.get(codigo);
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
		
	public String string() {
		return String.format("Aerolineas\n\t%s",this.aeroLineas.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}

}
