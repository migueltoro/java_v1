package us.lsi.ruta;

import us.lsi.ruta.RutaA.TipoImplementacion;

public class TestRuta {

	public static void main(String[] args) {
		RutaA.setTipoImplementacion(TipoImplementacion.Funcional);;
		Ruta r = Ruta.of("ficheros/ruta.csv");
//		System.out.println(r);
		System.out.println(r.desnivelCrecienteAcumulado());
//		System.out.println(r.getDesnivelDecrecienteAcumulado());
//		System.out.println(r.getLongitud());
		System.out.println(r.frecuencias());
		System.out.println(r.imprimeGrupos(r.limita(r.agrupaPorTipoLista(),5)));
	}

}
