package us.lsi.tipos_agregados;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tools.Preconditions;

public abstract class AgregadoLineal<E> {

	protected List<E> elements;

	protected AgregadoLineal() {
		this.elements = new ArrayList<>();
	}

	abstract void add(E e);

	public E remove() {
		Preconditions.checkArgument(!this.elements.isEmpty());
		return this.elements.remove(0);
	}

	public void addAll(List<E> ls) {
		ls.stream().forEach(e -> this.add(e));
	}

	public List<E> removeAll() {
		List<E> ls = new ArrayList<>();
		while (!this.isEmpty()) {
			ls.add(this.remove());
		}
		return ls;
	}

	public int size() {
		return this.elements.size();
	}

	public Boolean isEmpty() {
		return this.elements.isEmpty();
	}

}
