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
	
	private static OcupacionesVuelos focupacionesVuelos = null;
	
	public static OcupacionesVuelos get() {
		return OcupacionesVuelos.focupacionesVuelos;
	}

	public static OcupacionesVuelos random(Integer numOcupaciones, Integer anyo) {
		Integer n = Vuelos.get().size();
		List<OcupacionVuelo> r = toList(
				IntStream.range(0, numOcupaciones).boxed()
				.map(e -> OcupacionVuelo.random(Vuelos.get().get(rnd.nextInt(n)), anyo)));
		OcupacionesVuelos.focupacionesVuelos =  new OcupacionesVuelos(r);
		return OcupacionesVuelos.focupacionesVuelos;
	}

	public static OcupacionesVuelos leeFicheroOcupaciones(String fichero) {
		List<OcupacionVuelo> r = FileTools.streamFromFile(fichero)
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toList());
		return new OcupacionesVuelos(r);
	}

	private List<OcupacionVuelo> ocupaciones;

	public OcupacionesVuelos(List<OcupacionVuelo> ocupaciones) {
		super();
		this.ocupaciones = ocupaciones;
	}

	public Stream<OcupacionVuelo> stream() {
		return this.ocupaciones.stream();
	}
	
	public OcupacionVuelo get(Integer i) {
		return this.ocupaciones.get(i);
	}
	
	public Integer size() {
		return this.ocupaciones.size();
	}
	
}
