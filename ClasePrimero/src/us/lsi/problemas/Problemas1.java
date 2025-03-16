package us.lsi.problemas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.Pair;

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

    

	public static void main(String[] args) {
		Map<String, Integer> colores = Map.of("a", 1, "b", 2, "c", 3, "d", 4);
		Set<Pair<String, String>> vecinos = Set.of(Pair.of("a", "b"), Pair.of("a", "c"), Pair.of("b", "c"),
				Pair.of("b", "d"), Pair.of("c", "d"));
		System.out.println(coloresVecinos(colores, vecinos));
		System.out.println(coloresVecinosF(colores, vecinos));
		// {a=[2, 3], b=[1, 3, 4], c=[1, 2, 4], d=[2, 3]}
	}

}
