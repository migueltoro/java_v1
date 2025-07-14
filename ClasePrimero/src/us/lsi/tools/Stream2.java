package us.lsi.tools;



import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Stream2 {
	
	public static <E> Stream<E> of(Iterable<E> iterable) {
	    return StreamSupport.stream(
	        Spliterators.spliteratorUnknownSize(
	            iterable.iterator(),
	            Spliterator.ORDERED
	        ),
	        false
	    );
	}
	
	public static <E> Stream<E> of(Iterator<E> iterator) {
	    return StreamSupport.stream(
	        Spliterators.spliteratorUnknownSize(
	            iterator,
	            Spliterator.ORDERED
	        ),
	        false
	    );
	}
	
	public static Stream<IntPair> allPairs(Integer n, Integer m){
		return allPairs(0,n,0,m);
	}
			
	public static Stream<IntPair> allPairs(Integer n1, Integer n2, Integer m1, Integer m2){
		return IntStream.range(n1, n2).boxed()
				.flatMap(x->IntStream.range(m1, m2).boxed().map(y->IntPair.of(x,y)));
	}
	
	/**
	 * Computes the Cartesian product of two streams.
	 *
	 * @param <T> the type of elements in the first stream
	 * @param <U> the type of elements in the second stream
	 * @param s1 the first stream
	 * @param s2 the second stream
	 * @return a stream of pairs, where each pair consists of an element from the first stream
	 *         and an element from the second stream
	 */	
	public static <T, U> Stream<Pair<T,U>> cartesianProduct(Stream<T> s1, Stream<U> s2) {
		List<U> ls = s2.collect(Collectors.toList());
		return s1.flatMap(x->ls.stream().map(y->Pair.of(x,y)));
	}
	
	public static <E> Iterable<E> toIterable(Stream<E> st){
		return st::iterator;
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
		Iterator<E> iterator = stream.iterator();
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Enumerate<E>>() {
			private int index = start;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Enumerate<E> next() {
				return Enumerate.of(index++, iterator.next());
			}
		}, Spliterator.ORDERED), false);
	}
	
	
	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream){
		return Stream2.enumerate(stream, 0);
	}
	
	
	public static void main(String[] args) {
//		Stream<IntPair> pairs = Stream2.allPairs(0, 3, 0, 3);
//		pairs.forEach(System.out::println);

//		Stream<Pair<Integer, String>> cp = Stream2.cartesianProduct(Stream.of(1, 2, 3), Stream.of("a", "b"));
//		cp.forEach(System.out::println);
//
		Stream<Enumerate<Integer>> enumerated = Stream2.enumerate(Stream.of(10, 20, 30), 1);
		enumerated.forEach(System.out::println);
	}

}
