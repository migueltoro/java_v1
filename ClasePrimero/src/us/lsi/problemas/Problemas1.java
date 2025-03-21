package us.lsi.problemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.Pair;
import us.lsi.tools.Set2;
import us.lsi.tools.Stream2;

public class Problemas1 {
	
	/**
	 * This method takes a map of elements and their associated colors, and a set of pairs representing neighboring elements.
	 * It returns a map where each element is associated with a set of colors of its neighboring elements.
	 *
	 * @param <E> the type of elements
	 * @param colores a map where keys are elements and values are their associated colors
	 * @param vecinos a set of pairs representing neighboring elements
	 * @return a map where each element is associated with a set of colors of its neighboring elements
	 */
	
	public static <E> Map<E, Set<Integer>> coloresVecinos(Map<E, Integer> colores, Set<Pair<E, E>> vecinos) {
		Map<E, Set<Integer>> r = new HashMap<>();
		for (Pair<E, E> p : vecinos) {
			E v1 = p.first();
			E v2 = p.second();
			Integer c1 = colores.get(v1);
			Integer c2 = colores.get(v2);
			r.computeIfAbsent(v1, x -> new HashSet<>()).add(c2);
			r.computeIfAbsent(v2, x -> new HashSet<>()).add(c1);
		}
		return r;
	}
	
	 /**
     * Este método toma un mapa de elementos y sus colores asociados, y un conjunto de pares que representan elementos vecinos.
     * Devuelve un mapa donde cada elemento está asociado con un conjunto de colores de sus elementos vecinos.
     *
     * @param <E> el tipo de elementos
     * @param colores un mapa donde las claves son elementos y los valores son sus colores asociados
     * @param vecinos un conjunto de pares que representan elementos vecinos
     * @return un mapa donde cada elemento está asociado con un conjunto de colores de sus elementos vecinos
     */
    public static <E> Map<E, Set<Integer>> coloresVecinosF(Map<E, Integer> colores, Set<Pair<E, E>> vecinos) {
        return vecinos.stream()
                .flatMap(p -> Stream.of(
                        Map.entry(p.first(), colores.get(p.second())),
                        Map.entry(p.second(), colores.get(p.first()))
                ))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }
    
    
    /**

    Joins two streams based on a common key extracted from each element.
    *
    @param  the type of elements in the first stream
    @param  the type of elements in the second stream
    @param  the type of the key used for joining
    @param s1 the first stream
    @param s2 the second stream
    @param k1 a function to extract the key from elements of the first stream
    @param k2 a function to extract the key from elements of the second stream
    @return a stream of pairs where each pair consists of an element from the first stream and an element from the second stream that share the same key
    */
    
    public static <T, U, K> Stream<Pair<T,U>> join(
			Stream<T> s1, 
			Stream<U> s2, 
			Function<T, K> k1,
			Function<U, K> k2) {
		Map<K, List<T>> m1 = s1.collect(Collectors.groupingBy(k1));
		Map<K, List<U>> m2 = s2.collect(Collectors.groupingBy(k2));
		Set<K> sk = Set2.intersection(m1.keySet(),m2.keySet());
		return sk.stream().flatMap(k->Stream2.cartesianProduct(m1.get(k).stream(),m2.get(k).stream()));
	}

	public <T, U, K> List<Pair<T, U>> joinI(List<T> s1, List<U> s2, Function<T, K> k1, Function<U, K> k2) {
		Map<K, List<T>> map1 = new HashMap<>();
		Map<K, List<U>> map2 = new HashMap<>();
		// Populate map1
		for (T element : s1) {
			K key = k1.apply(element);
			map1.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
		}
		// Populate map2
		for (U element : s2) {
			K key = k2.apply(element);
			map2.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
		}
		// Find common keys and create pairs
		List<Pair<T, U>> result = new ArrayList<>();
		for (K key : map1.keySet()) {
			if (map2.containsKey(key)) {
				for (T t : map1.get(key)) {
					for (U u : map2.get(key)) {
						result.add(Pair.of(t, u));
					}
				}
			}
		}
		return result;
	}
	
	/**

	Generates the Cartesian product of two streams.
	*
	@param  the type of elements in the first stream
	@param  the type of elements in the second stream
	@param s1 the first stream
	@param s2 the second stream
	@return a stream of pairs where each pair consists of an element from the first stream and an element from the second stream
	*/
	public static <T, U> Stream<Pair<T, U>> cartesianProduct(Stream<T> s1, Stream<U> s2) {
		List<U> ls = s2.collect(Collectors.toList());
		return s1.flatMap(x -> ls.stream().map(y -> Pair.of(x, y)));
	}

	public static void main(String[] args) {
		Map<String, Integer> colores = Map.of("a", 1, "b", 2, "c", 3, "d", 4);
		Set<Pair<String, String>> vecinos = Set.of(Pair.of("a", "b"), Pair.of("a", "c"), Pair.of("b", "c"),
				Pair.of("b", "d"), Pair.of("c", "d"));
		System.out.println(coloresVecinos(colores, vecinos));
		System.out.println(coloresVecinosF(colores, vecinos));
		// {a=[2, 3], b=[1, 3, 4], c=[1, 2, 4], d=[2, 3]}
		
	}

}
