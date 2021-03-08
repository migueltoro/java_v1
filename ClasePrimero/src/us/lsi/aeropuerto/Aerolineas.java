package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;

public class Aerolineas {
	
	public static void leeAerolineas(String fichero) {
		List<Aerolinea> datos = FileTools.streamFromFile(fichero)
				.map(x ->Aerolinea.parse(x))
				.collect(Collectors.toList());
		Aerolineas.datos = new Aerolineas(datos);
	}
	
	public List<Aerolinea> aeroLineas;
	public Map<String,Aerolinea> codigosAerolineas;
	public Integer numeroAerolineas;
	
	private static Aerolineas datos;
	
	public static Aerolineas datos() {
		return datos;
	}

	private Aerolineas(List<Aerolinea> aerolineas) {
		super();
		this.aeroLineas = aerolineas;
		try {
			this.codigosAerolineas = this.aeroLineas.stream()
					.collect(Collectors.toMap(Aerolinea::codigo,x->x));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		this.numeroAerolineas = this.codigosAerolineas.size();
	}
	
	public void addAerolinea(Aerolinea v) {
		Preconditions.checkArgument(!this.codigosAerolineas.containsKey(v.codigo()),
				String.format("La aerolina %s ya existe",v.toString()));
		this.aeroLineas.add(v);
		this.codigosAerolineas.put(v.codigo(),v);
		this.numeroAerolineas +=1;
	}
	
	public void removeAerolinea(Aerolinea v) {	
		if (this.codigosAerolineas.containsKey(v.codigo())) {
			this.aeroLineas.remove(v);
			this.codigosAerolineas.remove(v.codigo());
			this.numeroAerolineas -= 1;
		}
	}

}
