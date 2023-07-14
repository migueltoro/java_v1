package us.lsi.ejemplos_b3.tipos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class Matriz2<E> extends MatrizA<E>{
	 

    //#===========================================================================
    //# CONSTRUCTOR
    //#===========================================================================

	protected Matriz2(List<List<E>> datos) {
		super(datos);
	}

    //#===========================================================================
    //# MÉTODOS DE FACTOR�?A
    //#===========================================================================

	
	public static <E> Matriz2<E> of(List<List<E>> datos) {
		Preconditions.checkArgument(datos.size() > 0, 
    		String.format("El número de filas tiene que ser mayor que cero"));
		Boolean r1 = true;
		for(List<E> e: datos) {
			if(e.size() == 0) {
				r1 = false;
				break;
			}
		}
		Preconditions.checkArgument(r1, 
    		String.format("El número de columnas para cada filatiene que ser mayor que cero"));
		Boolean r2 = true;
		for(List<E> e: datos) {
			if(e.size() != datos.get(0).size()) {
				r2 = false;
				break;
			}
		}
		Preconditions.checkArgument(r2, 
    		String.format("Todas las filas tienen que tener el mismo número de columnas"));
		return new Matriz2<>(datos);
    }


	public static <E> Matriz2<E> ofFile(String fichero, String sep, Function<String,E> t) {
		List<String> filas = File2.lineasDeFichero(fichero,"utf-8");
		List<List<E>> datos = new ArrayList<>();
		for(String ln:filas) {
			List<E> fila = new ArrayList<>();
			for(String x: ln.split(sep)){
				E e = t.apply(x);
				fila.add(e);
			}
			datos.add(fila);
		}
        return Matriz2.of(datos);
	}
                                 
    //#===========================================================================
    //# PROPIEDADES DERIVADAS
    //#===========================================================================

	public Matriz2<E> traspuesta() {
		List<List<E>> datos = new ArrayList<>();
		for(int f=0; f<this.nf(); f++) {
			List<E> fila = new ArrayList<>();
			for(int c=0; c<this.nc(); c++) {
				fila.add(this.get(c,f));
			}
			datos.add(fila);
		}
		return Matriz2.of(datos);		
	}

        

    //#===========================================================================
    //# REPRESENTACIÓN COMO CADENA
    //#===========================================================================

	
	@Override
    public String toString() {
		Integer n1 = this.nf()-1;
		String r = "";
		for(int f=0; f < this.nf(); f++) {
			for(int c=0; c<this.nc(); c++) {
				r = r + (c==0?"":" ") + String.format("%5s",this.get(f,c));
			}
			r = r + (f==n1?"":"\n");
		}
		return r;	
    }

    //#===========================================================================
    //# CRITERIO DE IGUALDAD
    //#===========================================================================


	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Matriz2))
			return false;
		return true;
	}

}
