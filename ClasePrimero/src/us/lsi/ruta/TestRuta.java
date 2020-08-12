package us.lsi.ruta;

public class TestRuta {

	public static void main(String[] args) {
		Ruta r = Ruta.parse("ficheros/ruta.csv");
//		System.out.println(r.toString2());
		System.out.println(r.desnivelCrecienteAcumulado());
//		System.out.println(r.getDesnivelDecrecienteAcumulado());
//		System.out.println(r.getLongitud());
	}

}
