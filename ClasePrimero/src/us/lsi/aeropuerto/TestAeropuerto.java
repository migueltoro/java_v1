package us.lsi.aeropuerto;

import java.time.format.DateTimeFormatter;

import us.lsi.tools.FileTools;
import us.lsi.tools.Map2;

public class TestAeropuerto {

	public static void main(String[] args) {
		Aeropuertos.leeAeropuertos("ficheros/aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/aerolineas.csv");
		GenDatos.random(100);
		FileTools.writeStream(Vuelos.get().stream().map(v->v.toString()),"ficheros/vuelos.csv");
		GenDatos.random(200, 2020);
		FileTools.writeStream(OcupacionesVuelos.get().stream().map(oc->oc.toString()),"ficheros/ocupacionesVuelos.csv");
		System.out.println(GenDatos.random());
		OcupacionVuelo ocp = GenDatos.random(Vuelos.get().get(0),2020);
		System.out.println(ocp);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(ocp.fecha().format(formatter));
//		System.out.println(CollectionsTools.mapToString(OcupacionesVuelos.fechasADestino()));
		Preguntas p = Preguntas.funcional();
		System.out.println(Map2.toString(p.destinosConMayorDuracion(2)));
	}

}
