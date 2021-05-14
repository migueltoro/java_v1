package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;
public class Aerolineas {
	
	public static void leeAerolineas(String fichero) {
		List<Aerolinea> datos = FileTools.streamFromFile(fichero)
				.map(x ->Aerolinea.parse(x))
				.toList();
		Aerolineas.datos = new Aerolineas(datos);
	}
	
	private List<Aerolinea> aeroLineas;
	
	public List<Aerolinea> getAeroLineas() {
		return aeroLineas;
	}
	
	private Map<String,Aerolinea> codigosAerolineas = null;

	public Map<String, Aerolinea> getCodigosAerolineas() {
		if(codigosAerolineas == null)
		   this.codigosAerolineas = this.aeroLineas.stream().collect(Collectors.toMap(a->a.codigo(),a->a));
		return this.codigosAerolineas;
	}
	
	public Integer getNumeroAerolineas() {
		return this.aeroLineas.size();
	}
	
	private static Aerolineas datos;

	public static Aerolineas getDatos() {
		return datos;
	}

	public Aerolineas(List<Aerolinea> aeroLineas) {
		super();
		this.aeroLineas = aeroLineas;
	}
	
	@Override
	public String toString() {
		return String.format("Aerolineas\n\t%s",this.aeroLineas.stream()
				.map(a->a.toString())
				.collect(Collectors.joining("\n\t")));
	}

}
