package us.lsi.aeropuerto;

import static us.lsi.tools.StreamTools.*;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.FileTools;

public class OcupacionesVuelos {
	
	private static OcupacionesVuelos datos;
	private static Random rnd = new Random(System.nanoTime());
	
	public static OcupacionesVuelos datos() {
		return datos;
	}

	public static void random(Integer numOcupaciones, Integer anyo) {
		Integer n = Vuelos.datos().numVuelos();
		List<OcupacionVuelo> r = toList(
				IntStream.range(0, numOcupaciones).boxed()
				.map(e -> OcupacionVuelo.random(Vuelos.datos().vuelos().get(rnd.nextInt(n)), anyo)));
		 OcupacionesVuelos.datos = new OcupacionesVuelos(r);
	}

	public static void leeFicheroOcupaciones(String fichero) {
		List<OcupacionVuelo> r = FileTools.streamFromFile(fichero)
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toList());
		OcupacionesVuelos.datos = new OcupacionesVuelos(r);
	}

	private List<OcupacionVuelo> ocupaciones;
	
	private OcupacionesVuelos(List<OcupacionVuelo> ocupaciones) {
		super();
		this.ocupaciones = ocupaciones;
	}

	public List<OcupacionVuelo> ocupaciones() {
		return ocupaciones;
	}

	
	
}
