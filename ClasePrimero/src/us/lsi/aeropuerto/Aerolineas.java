package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class Aerolineas {
	
	private static Aerolineas faerolineas = null;
	
	public static Aerolineas get() {
		return faerolineas;
	}
	
	public static Aerolineas leeAerolineas(String fichero) {
		List<Aerolinea> datos = FileTools.streamFromFile(fichero)
				.map(x ->Aerolinea.parse(x))
				.toList();
		Aerolineas aerolineas = new Aerolineas(datos,null);
		return aerolineas;
	}
	
	public Aerolineas(List<Aerolinea> aeroLineas, Map<String, Aerolinea> codigosAerolineas) {
		super();
		this.aeroLineas = aeroLineas;
		this.codigosAerolineas = codigosAerolineas;
	}

	
	private List<Aerolinea> aeroLineas;
	
	private Map<String,Aerolinea> codigosAerolineas = null;

	public Aerolinea aerolinea(String codigo) {
		if(codigosAerolineas == null)
			this.codigosAerolineas = this.aeroLineas.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
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
