package us.lsi.aeropuerto;

import static us.lsi.tools.StreamTools.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class OcupacionesVuelos {

	private static Random rnd = new Random(System.nanoTime());


	public static void random(Integer numOcupaciones, Integer anyo) {
		Integer n = Vuelos.size();
		List<OcupacionVuelo> r = toList(
				IntStream.range(0, numOcupaciones).boxed()
				.map(e -> OcupacionVuelo.random(Vuelos.get(rnd.nextInt(n)), anyo)));
		 OcupacionesVuelos.ocupaciones = r;
	}

	public static void leeFicheroOcupaciones(String fichero) {
		List<OcupacionVuelo> r = FileTools.streamFromFile(fichero)
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toList());
		OcupacionesVuelos.ocupaciones = r;
	}

	private static List<OcupacionVuelo> ocupaciones;

	
	public static Stream<OcupacionVuelo> stream() {
		return ocupaciones.stream();
	}
	
	public static OcupacionVuelo get(Integer i) {
		return ocupaciones.get(i);
	}
	
	public static Integer size() {
		return ocupaciones.size();
	}
	
}
