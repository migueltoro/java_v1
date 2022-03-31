package us.lsi.ruta;

import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.ruta.Intervalo.Type;
import us.lsi.ruta.RutaA.TipoImplementacion;

public interface Ruta {
	
	public static void setTipoImplementacion(TipoImplementacion t) {
		RutaA.setTipoImplementacion(t);
	}
	
	public static TipoImplementacion getTipoImplementacion() {
		return RutaA.getTipoImplementacion();
	}
	
	public static Ruta of(List<Marca> marcas) {
		return switch(Ruta.getTipoImplementacion()) {
		case Funcional->RutaF.of(marcas);
		case Imperativa->RutaI.of(marcas);
		};
	}
	
	public static Ruta leeDeFichero(String fichero) {
		return switch(Ruta.getTipoImplementacion()) {
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
	
	Map<Type,Integer> getFrecuencias();
	
	Set<Intervalo> getLLanos();
	
	Integer gerNumMarcas();

}