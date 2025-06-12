package us.lsi.sevici;

import java.util.Map;
import java.util.Set;

public abstract class RedA implements Red{
	
	public static enum TipoImplementacion{Imperativa,Funcional}
	
	public static TipoImplementacion tipo = TipoImplementacion.Funcional;
	
	protected Set<Estacion> estaciones;
	protected Map<Integer, Estacion> indices;

	protected RedA(Set<Estacion> estaciones) {
		super();
		this.estaciones = estaciones;
		this.indices = null;
	}
	
	public Map<Integer, Estacion> indices() {
		return indices;
	}

	@Override
	public void add(Estacion e) {
		assert !this.indices.containsKey(e.numero()):"La estacion esta ya incluida";
		this.estaciones.add(e);
		this.indices = null;
	}
	
	@Override
	public void remove(Estacion e) {
		this.estaciones.remove(e);
		this.indices = null;
	}

	@Override
	public Integer numero() {
		return this.estaciones.size();
	}
	
	@Override
	public Set<Estacion> estaciones() {
		return estaciones;
	}

	@Override
	public Estacion estacion(Integer numero) {
		Estacion e = this.indices().getOrDefault(numero,null);
		assert e!=null : "No existe la estacion con numero " + numero;
		return e;
	}

}
