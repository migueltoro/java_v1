package us.lsi.tools;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Preconditions {

	/**
	 * Checks that the boolean is true. Use for validating arguments to methods.
	 * @param condition A condition
	 */
	public static void checkArgument(boolean condition){
		if(!condition){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Checks that the boolean is true. Use for validating arguments to methods.
	 * @param message A message
	 * @param condition A condition
	 */
	public static void checkArgument(boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks some state of the object, not dependent on the method arguments.  
	 * @param condition A condition
	 */
	public static void checkState(boolean condition){
		if(!condition){
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Checks some state of the object, not dependent on the method arguments. 
	 * @param message Mensaje a imprimir
	 * @param condition A condition
	 */
	public static void checkState(boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Comprueba que los par�metros son no null
	 * @param <T> El tipo del elemento	
	 * @param reference Par�metros a comprobar
	 */
	@SafeVarargs
	public static <T> void checkNotNull(T... reference){
		if(Arrays.stream(reference).anyMatch(x->x == null)){
			throw new NullPointerException(String.format("Son nulos los elementos %s",
					IntStream.range(0, reference.length)
					.boxed()
					.filter(i->reference[i] == null)
					.collect(Collectors.toList())));
		}
	}
		
	/**
	 * Checks that index is a valid element index into a list, string, or array with the specified size. 
	 * An element index may range from 0 inclusive to size exclusive. 
	 * You don't pass the list, string, or array directly; you just pass its size. 
	 * @param index Un �ndice 
	 * @param size El tama�o de la lista
	 * @return Index El �ndice del elemento
	 */
	public static int checkElementIndex(int index, int size){
		if(!(index>=0 && index<size)){
			throw new IndexOutOfBoundsException(String.format("Index = %d, size %d", index,size));
		}
		return index;
	}
	
	/**
	 * Checks that index is a valid position index into a list, string, or array with the specified size. 
	 * A position index may range from 0 inclusive to size inclusive. 
	 * You don't pass the list, string, or array directly; you just pass its size. Returns index.
	 * @param index El �ndice del elemento
	 * @param size El tama�o de la lista
	 * @return Index El �ndice del elemento
	 */
	public static int checkPositionIndex(int index, int size){
		if(!(index>=0 && index<=size)){
			throw new IndexOutOfBoundsException(String.format("Index = %d, size %d", index,size));
		}
		return index;
	}
	
	
}

