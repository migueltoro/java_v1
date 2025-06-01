package us.lsi.recorridos;

import java.util.Optional;

import us.lsi.tipos_agregados.Graph;
import us.lsi.tipos_agregados.Heap;

public class RecorridoOrdenado<V, E> extends Recorrido<V, E> {

	public static <V, E> RecorridoOrdenado<V, E> of(Graph<V, E> grafo) {
		return new RecorridoOrdenado<>(grafo);
	}

	protected RecorridoOrdenado(Graph<V, E> grafo) {
		super(grafo);
	}

	public void traverse(V source) {
        V v = source;
        Heap<V,Double> q = Heap.of();
        q.add(v,0.);
        this.tree.put(v, Data.of(Optional.empty(),0.));
        while (!q.isEmpty()) {
            v = q.remove();
            this.path.add(v);
            Double w = this.tree.get(v).weight();
            for (V neighbor : this.graph.neighbors(v)) {
                Double nbw = w + this.graph.edgeWeight(v, neighbor); 
                if (!this.tree.containsKey(neighbor)) {
                    this.tree.put(neighbor, Data.of(Optional.of(v), nbw));
                    q.add(neighbor,nbw);                    
                } else {
                    if (nbw < this.tree.get(neighbor).weight()) {
                        this.tree.put(neighbor, Data.of(Optional.of(v), nbw));
                        q.decrease(neighbor, nbw);
                    }
                }
            }
        }
	}
}
