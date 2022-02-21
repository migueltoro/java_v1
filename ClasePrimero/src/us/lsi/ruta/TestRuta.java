package us.lsi.ruta;


public class TestRuta {

	public static void main(String[] args) {
//		RutaA.tipo = RutaA.TipoImplementacion.Imperativa;
		Ruta r = Ruta.leeDeFichero("ficheros/ruta.csv");
		System.out.println(r.toString());
		System.out.println(r.getDesnivelCrecienteAcumulado());
//		System.out.println(r.getDesnivelDecrecienteAcumulado());
//		System.out.println(r.getLongitud());
	}

}
