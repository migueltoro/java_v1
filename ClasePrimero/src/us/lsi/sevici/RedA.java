package us.lsi.sevici;

import java.util.List;
import java.util.Map;

import us.lsi.tools.Preconditions;

public abstract class RedA implements Red{
	
	protected List<Estacion> estaciones;
	protected Map<Integer, Estacion> indices;

	protected RedA(List<Estacion> estaciones) {
		super();
		this.estaciones = estaciones;
		this.indices = null;
	}
	
	@Override
	public void add(Estacion e) {
		Preconditions.checkArgument(!this.indices().containsKey(e.numero()),"La estacion esta ya incluida");
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
	public List<Estacion> estaciones() {
		return estaciones;
	}

	

}
