package us.lsi.ruta;

import java.util.List;

public interface Ruta {
	
	public static Ruta of(List<Marca> marcas) {
		return switch(RutaA.tipo) {
		case Funcional->RutaF.of(marcas);
		case Imperativa->RutaI.of(marcas);
		};
	}
	
	public static Ruta leeDeFichero(String fichero) {
		return switch(RutaA.tipo) {
		case Funcional->RutaF.leeDeFichero(fichero);
		case Imperativa->RutaI.leeDeFichero(fichero);
		};
	}

	Double getTiempo();

	Double getLongitud();

	Double getVelocidadMedia();

	Intervalo getIntervalo(Integer i);

	Double getDesnivelCrecienteAcumulado();

	Double getDesnivelDecrecienteAcumulado();

}