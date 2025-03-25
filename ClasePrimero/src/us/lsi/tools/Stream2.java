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
	
	public static <T, U> Stream<Pair<T,U>> cartesianProduct(Stream<T> s1, Stream<U> s2) {
		List<U> ls = s2.collect(Collectors.toList());
		return s1.flatMap(x->ls.stream().map(y->Pair.of(x,y)));
	}
	
	public static <E> Iterable<E> toIterable(Stream<E> st){
		return st::iterator;
	}
	
	public static void main(String[] args) {
	}

}
