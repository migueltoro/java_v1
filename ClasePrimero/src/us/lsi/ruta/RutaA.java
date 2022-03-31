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
		this.marcas = marcas;
	}

	public RutaA() {
		super();
	}

	@Override
	public Double getVelocidadMedia() {
		return this.getLongitud()/this.getTiempo();
	}

	@Override
	public Intervalo getIntervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1));
	}
	
	@Override
	public Integer gerNumMarcas() {
		return this.marcas.size();
	}

}