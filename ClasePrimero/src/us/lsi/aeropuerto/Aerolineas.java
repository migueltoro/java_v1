package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.File2;

public class Aerolineas {
	
	private static Aerolineas faerolineas = null;
	
	public static Aerolineas of() {
		if(Aerolineas.faerolineas == null)
			Aerolineas.faerolineas = Aerolineas.of("ficheros_aeropuertos/aerolineas.csv");
		return faerolineas;
	}
	
	public static Aerolineas of(String fichero) {
		List<Aerolinea> datos = File2.streamDeFichero(fichero,"Windows-1252")
				.map(x ->Aerolinea.parse(x))
				.toList();
		Aerolineas aerolineas = new Aerolineas(datos);
		return aerolineas;
	}
	
	public Aerolineas(List<Aerolinea> aeroLineas) {
		super();
		this.aeroLineas = aeroLineas;
		this.codigosAerolineas = 
				this.aeroLineas.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
	}

	
	private List<Aerolinea> aeroLineas;
	
	private Map<String,Aerolinea> codigosAerolineas = null;

	public Aerolinea aerolinea(String codigo) {
		return this.codigosAerolineas.get(codigo);
	}
	
	public Integer size() {
		return this.aeroLineas.size();
	}
	
	public Stream<Aerolinea> stream() {
		return this.aeroLineas.stream();
	}
	
	public Aerolinea get(Integer i) {
		return this.aeroLineas.get(i);
	}
	
	public String string() {
		return String.format("Aerolineas\n\t%s",this.aeroLineas.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}

}
