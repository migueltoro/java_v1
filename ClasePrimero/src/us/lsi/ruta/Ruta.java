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
	
	public static Ruta of(String fichero) {
		return switch(Ruta.getTipoImplementacion()) {
		case Funcional->RutaF.ofCsv(fichero);
		case Imperativa->RutaI.of(fichero);
		};
	}

	Double tiempo();

	Double longitud();

	Double velocidadMedia();

	Intervalo intervalo(Integer i);

	Double desnivelCrecienteAcumulado();

	Double desnivelDecrecienteAcumulado();
	
	Map<Type,Integer> frecuencias();
	
	Set<Intervalo> llanos();
	
	Integer numMarcas();
	
	Map<Type, List<Intervalo>> agrupaPorTipoLista();
	
	Map<Type, List<Intervalo>> limita(Map<Type, List<Intervalo>> m, Integer n);
	
	Map<Type, Set<Intervalo>> agrupaPorTipoConjunto();
	
	String  imprimeGrupos(Map<Type, List<Intervalo>> m);

}