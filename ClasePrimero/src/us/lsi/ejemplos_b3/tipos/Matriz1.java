package us.lsi.ejemplos_b3.tipos;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.tools.File2;

public class Matriz1<E> extends MatrizA<E> {
	
//	    #===========================================================================
//	    # CONSTRUCTOR
//	    #===========================================================================
	
	    protected Matriz1(List<List<E>> datos) {
	        super(datos);
	    }
	    
//	    #===========================================================================
//	    # MÉTODOS DE FACTOR�?A
//	    #===========================================================================
	   
	    public static <E> Matriz1<E> of(List<List<E>> datos) {
	    	assert datos.size() > 0 : 
	        		String.format("El número de filas tiene que ser mayor que cero");
	        assert datos.stream().anyMatch(ln->ln.size()>0): 
	        		String.format("El número de columnas tiene que ser mayor que cero en cada fila");
	        assert datos.stream().anyMatch(ln->ln.size() == datos.get(0).size()): 
	        		String.format("El número de columnas en cada fila tiene que tener el mismo");
	        return new Matriz1<>(datos);
	    }
	    
	    private static <E> List<E> partes(String ln, String sep, Function<String,E> t) {
	    	return Arrays.stream(ln.split(sep)).map(e->t.apply(e)).toList();
	    }
	    
	    public static <E> Matriz1<E> ofFile(String fichero, String sep,Function<String,E> t) {
	    	List<String> filas = File2.lineasDeFichero(fichero,"utf-8");
	    	List<List<E>> datos  = filas.stream().map(ln->partes(ln,sep,t)).toList();		
	        return Matriz1.of(datos);
	    }
	                                     
//	    #===========================================================================
//	    # PROPIEDADES DERIVADAS
//	    #===========================================================================
	         		
	    public Matriz1<E> traspuesta() {
	    	List<List<E>> datos = IntStream.range(0, this.nc()).boxed()
	    			.map(c->IntStream.range(0, this.nf()).boxed().map(f->this.get(f,c)).toList())
	    			.toList();
	        return Matriz1.of(datos);
	    }
	            
	   
//	    #===========================================================================
//	    # REPRESENTACIÓN COMO CADENA
//	    #===========================================================================
	    
	    public String toString() {
	        Function<Integer,String> fs = f -> this.datos.get(f).stream()
	        		.map(e->String.format("%5s",e))
	        		.collect(Collectors.joining(" "));
	        return IntStream.range(0, this.nf()).boxed()
	        		.map(f->fs.apply(f))
	        		.collect(Collectors.joining("\n"));
	    }
	    
//	    #===========================================================================
//	    # CRITERIO DE IGUALDAD
//	    #===========================================================================
	   
	    
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
			if (!(obj instanceof Matriz1))
				return false;
			return true;
		}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
}
