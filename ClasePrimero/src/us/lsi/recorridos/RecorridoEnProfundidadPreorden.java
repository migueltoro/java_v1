package us.lsi.recorridos;

import java.util.Optional;

import us.lsi.tipos_agregados.Graph;
import us.lsi.tipos_agregados.Pila;

public class RecorridoEnProfundidadPreorden<V,E> extends Recorrido<V,E> {

	public static <V, E> RecorridoEnProfundidadPreorden<V, E> of(Graph<V, E> grafo) {
		return new RecorridoEnProfundidadPreorden<>(grafo);
	}
	
	protected RecorridoEnProfundidadPreorden(Graph<V, E> grafo) {
		super(grafo);
	}

	public void traverse(V source) {
        V v = source;
        Pila<V> q = Pila.of();
        q.add(v);
        this.tree.put(v, Data.of(Optional.empty(),0.));
        while (!q.isEmpty()) {
            v = q.remove();
            this.path.add(v);
            for (V neighbor : this.graph.neighbors(v)) {
                if (!this.tree.containsKey(neighbor)) {
                    q.add(neighbor);                   
                    this.tree.put(neighbor, Data.of(Optional.of(v), this.tree.get(v).weight() + 1));
                }
            }
        }
    }
}
