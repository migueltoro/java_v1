package us.lsi.recorridos;

import java.util.Collections;

import us.lsi.tipos_agregados.Graph;

public class RecorridoTopologico<V,E> extends RecorridoEnProfundidadPostorden<V,E> {
	
	public static <V, E> RecorridoTopologico<V, E> of(Graph<V, E> grafo) {
		return new RecorridoTopologico<>(grafo);
	}

	protected RecorridoTopologico(Graph<V, E> grafo) {
		super(grafo);
	}

	public void traverse(V source) {
		super.traverse(source);
		Collections.reverse(this.path);
	}

}
