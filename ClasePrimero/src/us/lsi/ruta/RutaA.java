package us.lsi.ruta;

import java.util.List;

public abstract class RutaA implements Ruta {
	
	public static enum TipoImplementacion{Imperativa,Funcional}
	
	private static TipoImplementacion tipo = TipoImplementacion.Funcional;
	
	public static void setTipoImplementacion(TipoImplementacion t) {
		tipo = t;
	}
	
	public static TipoImplementacion getTipoImplementacion() {
		return tipo;
	}

	protected List<Marca> marcas;
	
	protected RutaA(List<Marca> marcas) {
		super();
		assert marcas != null && marcas.size() > 1 : "Lista de marcas debe tener al menos dos marcas";
		this.marcas = marcas;
	}

	@Override
	public Double velocidadMedia() {
		return this.longitud()/this.tiempo();
	}

	@Override
	public Intervalo intervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1));
	}
	
	@Override
	public Integer numMarcas() {
		return this.marcas.size();
	}

}