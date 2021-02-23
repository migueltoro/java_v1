package us.lsi.aeropuerto;

import java.time.format.DateTimeFormatter;

import us.lsi.tools.CollectionsTools;

public class TestAeropuerto {

	public static void main(String[] args) {
		Aeropuertos.leeFicheroAeropuertos("ficheros/Aeropuertos.csv");
		EmpresasAereas.leeFicheroEmpresas("ficheros/EmpresasAereas.csv");
		Vuelos.random(100);
		System.out.println(Vuelo.random());
		OcupacionVuelo ocp = OcupacionVuelo.random(Vuelos.vuelos.get(0),2020);
		System.out.println(ocp);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(ocp.fecha().format(formatter));
		System.out.println(CollectionsTools.mapToString(Vuelos.masBarato()));
	}

}
