package us.lsi.ruta;

import us.lsi.ruta.RutaA.TipoImplementacion;

public class TestRuta {

	public static void main(String[] args) {
		RutaA.setTipoImplementacion(TipoImplementacion.Funcional);;
		Ruta r = Ruta.leeDeFichero("ficheros/ruta.csv");
		System.out.println(r);
		System.out.println(r.getDesnivelCrecienteAcumulado());
//		System.out.println(r.getDesnivelDecrecienteAcumulado());
//		System.out.println(r.getLongitud());
		System.out.println(r.getFrecuencias());
	}

}
