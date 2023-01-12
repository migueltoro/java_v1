package us.lsi.aeropuerto;

import java.io.IOException;

import us.lsi.tools.FileTools;
import us.lsi.tools.Map2;

public class TestAeropuerto {

	public static void main(String[] args) throws IOException {
		System.out.println(FileTools.getFileCharset("ficheros_aeropuertos/aerolineas.csv"));
		Preguntas p = Preguntas.funcional();
		Preguntas p2 = Preguntas.imperativo();
		System.out.println(Map2.toString(p.destinosConMayorDuracion(2)));
		System.out.println(Map2.toString(p2.precioMedio(10)));
	}

}
