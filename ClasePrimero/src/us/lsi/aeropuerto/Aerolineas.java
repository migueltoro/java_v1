package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;

public class Aerolineas {
	
	public static List<Aerolinea> aerolineas;
	public static Map<String,Aerolinea> codigosAerolineas;
	public static Integer numeroAerolineas;
	
	public static void leeAerolineas(String fichero) {
		Aerolineas.aerolineas = FileTools.streamFromFile(fichero)
				.map(x ->Aerolinea.parse(x))
				.collect(Collectors.toList());
		try {
			Aerolineas.codigosAerolineas = Aerolineas.aerolineas.stream()
					.collect(Collectors.toMap(Aerolinea::codigo,x->x));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		Aerolineas.numeroAerolineas = Aerolineas.codigosAerolineas.size();
	}
	
	public static void addAerolinea(Aerolinea v) {
		Aerolineas.aerolineas.add(v);
	}
	
	public static void removeAeropuerto(Aerolinea v) {
		Aerolineas.aerolineas.remove(v);
	}

}
