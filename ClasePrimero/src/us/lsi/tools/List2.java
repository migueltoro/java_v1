package us.lsi.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class List2 {
	
	/**
	 * @pre La lista no está vacía
	 * @param <E> Tipo de los elementos
	 * @param ls Una lista
	 * @return Su primer elemento
	 */
	public static <E> E first(List<E> ls){
		Preconditions.checkArgument(!ls.isEmpty(), "La lista no puede estar vacía");
		return ls.get(0);
	}
	
	/**
	 * @pre La lista no está vacía
	 * @param <E> Tipo de los elementos
	 * @param ls Una lista
	 * @return Su último elemento
	 */
	public static <E> E last(List<E> ls){
		Preconditions.checkArgument(!ls.isEmpty(), "La lista no puede estar vacía");
		int n = ls.size();
		return ls.get(n-1);
	}
	
	
	/**
	 * @param <E> tipo de los elementos de la lista
	 * @param ls Una lista
	 * @post La lista queda con el último elemento eliminado
	 * @pre la lista no puede estar vacía
	 * @return El último elemento eliminado
	 */
	public static <E> E removeLast(List<E> ls){
		int last = ls.size()-1;
		E e = ls.remove(last);
		return e;
	}
	
	/**
	 * @param <E> tipo de los elementos de la lista
	 * @param ls Una lista
	 * @param e Un elemento
	 * @post La lista queda con e añadido en primer lugar
	 */
	public static <E> void addFirst(List<E> ls, E e){
		ls.add(0,e);
	}
	
	/**
	 * @param <E> tipo de los elementos
	 * @param elements Una serie de elementos
	 * @return Una lista construida de first más los que están en elements
	 */
	@SafeVarargs
	public static <E> List<E> of(E... elements){
		List<E> r = new ArrayList<E>();
		r.addAll(Arrays.stream(elements).collect(Collectors.toList()));
		return r;
	}
	
	/**
	 * @param <E> Tipo de los elementos de la lista
	 * @param ls1 Una lista
	 * @param ls2 Una segunda lista
	 * @return La concatenación d elas dos listas
	 */
	public static <E> List<E> concat(List<E> ls1, List<E> ls2){
		List<E> r = new ArrayList<>(ls1);
		r.addAll(ls2);
		return r;
	}
	
	public static <E> List<E> difference(List<E> s1,  List<E> s2){
		List<E> s = new ArrayList<>(s1);
		s.removeAll(s2);
		return s;
	}
	
	public static <E> List<E> union(List<E> s1,  List<E> s2){
		List<E> s = new ArrayList<>(s1);
		s.addAll(s2);
		return s;
	}
	
	public static <E> List<E> intersection(List<E> s1,  List<E> s2){
		List<E> s = new ArrayList<>(s1);
		s.retainAll(s2);
		return s;
	}
	
	
	public static <E> Boolean contains(List<E> s1,  List<E> s2){
		return s1.containsAll(s2);
	}
	
	public static <E> String toString(List<E> c) {
		return c.stream()
				.map(e->e.toString())
				.collect(Collectors.joining("\n"));			
	}
	
	public static <E> String toString(List<E> c, String sep, String prefix, String suffix) {
		return c.stream()
				.map(e->e.toString())
				.collect(Collectors.joining(sep,prefix,suffix));			
	}
	
}
