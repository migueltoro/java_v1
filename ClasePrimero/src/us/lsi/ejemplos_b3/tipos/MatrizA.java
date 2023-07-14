package us.lsi.ejemplos_b3.tipos;

import java.util.List;
import java.util.Objects;

public abstract class MatrizA<E> implements Matriz<E> {

	protected List<List<E>> datos;

	protected MatrizA(List<List<E>> datos) {
		super();
        this.datos = datos;
	}

	@Override
	public Integer nf() {  
	    return this.datos.size();
	}

	@Override
	public Integer nc() {  
	    return this.datos.get(0).size();
	}

	@Override
	public E get(Integer f, Integer c) {
	    return this.datos.get(f).get(c); 
	}
	
	@Override
	public Boolean esSimetrica() {
		return  this.nc() == this.nf() && this.equals(this.traspuesta());
	}
	
	@Override
	public abstract Matriz<E> traspuesta();

	@Override
	public int hashCode() {
		return Objects.hash(datos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrizA<?> other = (MatrizA<?>) obj;
		return Objects.equals(datos, other.datos);
	}

}