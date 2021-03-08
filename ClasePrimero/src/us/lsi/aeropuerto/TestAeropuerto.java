package us.lsi.aeropuerto;

import java.time.format.DateTimeFormatter;

import us.lsi.tools.CollectionsTools;
import us.lsi.tools.FileTools;

public class TestAeropuerto {

	public static void main(String[] args) {
		Aeropuertos.leeAeropuertos("ficheros/Aeropuertos.csv");
		Aerolineas.leeAerolineas("ficheros/Aerolineas.csv");
		Vuelos.random(100);
		FileTools.writeStream(Vuelos.datos().vuelos().stream().map(v->v.toString()),"ficheros/Vuelos.csv");
		OcupacionesVuelos.random(200, 2020);
		FileTools.writeStream(OcupacionesVuelos.datos().ocupaciones().stream().map(oc->oc.toString()),"ficheros/OcupacionesVuelos.csv");
		System.out.println(Vuelo.random());
		OcupacionVuelo ocp = OcupacionVuelo.random(Vuelos.datos().vuelos().get(0),2020);
		System.out.println(ocp);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(ocp.fecha().format(formatter));
//		System.out.println(CollectionsTools.mapToString(OcupacionesVuelos.fechasADestino()));
		System.out.println(CollectionsTools.mapToString(OcupacionesVuelos.destinosConMayorDuracion(2)));
	}

}
