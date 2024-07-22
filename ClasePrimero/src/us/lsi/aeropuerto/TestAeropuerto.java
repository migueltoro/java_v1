package us.lsi.aeropuerto;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;
import us.lsi.tools.Map2;

public class TestAeropuerto {
	
	public static Set<OcupacionVuelo> of(String fichero) {
		Set<OcupacionVuelo> r = File2.streamDeFichero(fichero,"Windows-1252")
				.map(x -> OcupacionVuelo.parse(x))
				.collect(Collectors.toSet());
		return r;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(File2.getFileEncoding("aeropuertos/aerolineas.csv"));
		Vuelos v = EspacioAereo.of().vuelos();
		System.out.println(v.size());
		Set<OcupacionVuelo> r = EspacioAereo.of().ocupacionesVuelos().todas();
		System.out.println(r.size());
		Preguntas p = Preguntas.funcional();
		Preguntas p2 = Preguntas.imperativo();
		System.out.println(Map2.toString(p.destinosConMayorDuracion(2)));
		System.out.println(Map2.toString(p2.precioMedio(10)));
	}
	
}
