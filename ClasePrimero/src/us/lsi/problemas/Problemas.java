package us.lsi.problemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import us.lsi.tools.Enumerate;
import us.lsi.tools.Pair;
import us.lsi.tools.Set2;
import us.lsi.tools.Stream2;


public class Problemas {
	
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
		for (T element : s1) {
			K key = k1.apply(element);
			map1.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
		}
		for (U element : s2) {
			K key = k2.apply(element);
			map2.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
		}
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
	
	public static <L, R, T> Spliterator<T> zip(Spliterator<L> lefts, Spliterator<R> rights, BiFunction<L, R, T> combiner) {
		return new AbstractSpliterator<T>(
				Long.min(lefts.estimateSize(), rights.estimateSize()),
				lefts.characteristics() & rights.characteristics()) {
			@Override
			public boolean tryAdvance(Consumer<? super T> action) {
				return lefts.tryAdvance(left -> rights.tryAdvance(right -> action.accept(combiner.apply(left, right))));
			}
		};
	}
	
	/**
	 * Combines two streams into a single stream using a specified combiner function.
	 *
	 * @param <L> the type of elements in the first stream
	 * @param <R> the type of elements in the second stream
	 * @param <T> the type of elements in the resulting stream
	 * @param leftStream the first stream
	 * @param rightStream the second stream
	 * @param combiner a function that combines an element from the first stream and an element from the second stream into an element of the resulting stream
	 * @return a stream of combined elements
	 */
	public static <L, R, T> Stream<T> zip(Stream<L> leftStream, Stream<R> rightStream, BiFunction<L, R, T> combiner) {
		Spliterator<L> lefts = leftStream.spliterator();
		Spliterator<R> rights = rightStream.spliterator();
		return StreamSupport.stream(Problemas.zip(lefts, rights, combiner), leftStream.isParallel() || rightStream.isParallel());
	}
	
	/**
	 * Combines two streams into a single stream using a specified combiner function.
	 * The best implimentation is the one before
	 * 
	*/
	public static <L, R, T> Stream<T> zip2(Stream<L> leftStream, Stream<R> rightStream, BiFunction<L, R, T> combiner) {
		List<L> left = leftStream.collect(Collectors.toList());
		List<R> right = rightStream.collect(Collectors.toList());
		return Stream.iterate(0, i -> i + 1)
				.limit(Math.min(left.size(), right.size()))
				.map(i -> combiner.apply(left.get(i), right.get(i)));
	}
	
	/**
	 * Enumerates the elements of a stream starting from a given integer.
	 *
	 * @param <E> the type of elements in the stream
	 * @param stream the input stream to be enumerated
	 * @param start the starting integer for enumeration
	 * @return a stream of Enumerate objects where each element is paired with its enumeration index
	 */
	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream, Integer start) {
		Stream<Integer> st = Stream.iterate(start, e -> e + 1);
		return zip(stream, st, (e, n) -> Enumerate.of(n, e));
	}

	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream) {
		return enumerate(stream, 0);
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
	
	/**
	 * Generates a stream of consecutive pairs from the input stream.
	 *
	 * @param <T> the type of elements in the input stream
	 * @param s the input stream
	 * @return a stream of pairs where each pair consists of two consecutive elements from the input stream
	 */
	public static <T> Stream<Pair<T,T>> consecutivePairs(Stream<T> s) {
		List<T> ls = s.toList();
		return zip(ls.stream(), ls.subList(1,ls.size()).stream(), Pair::of);
	}
	
	/**
	 * Generates a stream of consecutive pairs from the input stream.
	 *
	 * @param <T> the type of elements in the input stream
	 * @param s the input stream
	 * @return a stream of pairs where each pair consists of two consecutive elements from the input stream
	 */
	public static <T> Stream<Pair<T,T>> consecutivePairs2(Stream<T> s) {
		@SuppressWarnings("unchecked")
		T[] old = (T[]) new Object[2];
		old[0] = null;
		old[1] = null;
		return s.peek(e->{old[0]=old[1];old[1]=e;}).map(e->Pair.of(old[0],old[1])).filter(p->p.first()!=null);
	}
	
	/**
	 * Generates pair with the optional first element and the rest of stream
	 *
	 * @param <T> the type of elements in the input stream
	 * @param s the input stream
	 * @return pair with the optional first element and the rest of stream
	 */
	
	public static <T> Pair<Optional<T>, Stream<T>> firstAndRest(Stream<T> s) {
	    Iterator<T> iterator = s.iterator();
	    Optional<T> first = iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
	    Iterable<T> restIterable = () -> iterator;
		if (first.isEmpty()) {
			return Pair.of(first, Stream.empty());
		} else {
			Stream<T> rest = StreamSupport.stream(restIterable.spliterator(), false);
			return Pair.of(first, rest);
		}
	}

	public static void main(String[] args) {
		Map<String, Integer> colores = Map.of("a", 1, "b", 2, "c", 3, "d", 4);
		Set<Pair<String, String>> vecinos = Set.of(Pair.of("a", "b"), Pair.of("a", "c"), Pair.of("b", "c"),
				Pair.of("b", "d"), Pair.of("c", "d"));
		System.out.println(coloresVecinos(colores, vecinos));
		System.out.println(coloresVecinosF(colores, vecinos));
		// {a=[2, 3], b=[1, 3, 4], c=[1, 2, 4], d=[2, 3]}
		List<Integer> ls = List.of(2, 3, 5, 7);
		List<Pair<Integer, Integer>> ls2 = consecutivePairs(ls.stream()).toList();
		System.out.println(ls2);
		ls2 = consecutivePairs2(ls.stream()).toList();
		System.out.println(ls2);
		List<Double> ls3 = List.of(2.3,3.4,5.6,7.8);
		List<Pair<Integer,Double>> ls4 = enumerate(ls3.stream()).map(e->Pair.of(e.counter(),e.value()))
				.toList();
		System.out.println(ls4);
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Pair<Optional<Integer>, Stream<Integer>> r3 = Problemas.firstAndRest(stream);
		Pair<Integer, List<Integer>> r2 = Pair.of(r3.first().get(), r3.second().toList());
        System.out.println(r2);
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4);
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4,7);
        Stream<Pair<Integer, Integer>> r4 = Problemas.zip(stream1, stream2, Pair::of);
        stream1 = Stream.of(1, 2, 3, 4);
        stream2 = Stream.of(1, 2, 3, 4,7);
        Stream<Pair<Integer, Integer>> r5 = Problemas.zip2(stream1, stream2, Pair::of);
        System.out.println(r4.collect(Collectors.toList()));
        System.out.println(r5.collect(Collectors.toList()));
	}

}
