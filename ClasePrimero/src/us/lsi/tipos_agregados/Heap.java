package us.lsi.tipos_agregados;


import java.util.Comparator;
import java.util.List;

public interface Heap<V, P> {
	
	public static <V, P extends Comparable<? super P>> Heap<V, P> of() {
		return new ListaOrdenadaPrioridad<V, P>(Comparator.naturalOrder());
	}
	
	public static <V, P> Heap<V, P> of(Comparator<P> comparator) {
		return new ListaOrdenadaPrioridad<V, P>(comparator);
	}
	
	void clear();

	boolean isEmpty();

	Integer size();

	void add(V value, P priority);

	void decrease(V value, P priority);

	V remove();

	void addAll(List<V> values, List<P> priorities);

	List<V> removeAll();

}