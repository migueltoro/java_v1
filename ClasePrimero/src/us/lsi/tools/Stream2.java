package us.lsi.tools;


import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Spliterators.AbstractSpliterator;
import java.util.TreeMap;

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
	
	private static <L, R, T> Spliterator<T> zip(Spliterator<L> lefts, Spliterator<R> rights, BiFunction<L, R, T> combiner) {
		return new AbstractSpliterator<T>(
				Long.min(lefts.estimateSize(), rights.estimateSize()),
				lefts.characteristics() & rights.characteristics()) {
			@Override
			public boolean tryAdvance(Consumer<? super T> action) {
				return lefts.tryAdvance(left -> rights.tryAdvance(right -> action.accept(combiner.apply(left, right))));
			}
		};
	}
	
	public static <L, R, T> Stream<T> zip(Stream<L> leftStream, Stream<R> rightStream, BiFunction<L, R, T> combiner) {
		Spliterator<L> lefts = leftStream.spliterator();
		Spliterator<R> rights = rightStream.spliterator();
		return StreamSupport.stream(Stream2.zip(lefts, rights, combiner), leftStream.isParallel() || rightStream.isParallel());
	}
	
	
	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream, Integer start) {
		Stream<Integer> st = Stream.iterate(start, e -> e + 1);
		return zip(stream, st, (e, n) -> Enumerate.of(n, e));
	}

	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream) {
		return enumerate(stream, 0);
	}
	
	public static <E,R> Stream<Enumerate<R>> flatMapEnumerate(Stream<Enumerate<E>> stream,Function<E,Stream<R>> f){
		return stream.flatMap(p->f.apply(p.value()).map(v->Enumerate.of(p.counter(),v)));
	}
	
	public static <T, U, K> Stream<Pair<T,U>> join(
			Stream<T> s1, 
			Stream<U> s2, 
			Function<T, K> k1,
			Function<U, K> k2) {
		Map<K, List<T>> m1 = Stream2.groupingList(s1,k1);
		Map<K, List<U>> m2 = Stream2.groupingList(s2,k2);
		Set<K> sk = Set2.intersection(m1.keySet(),m2.keySet());
		return sk.stream().flatMap(k->Stream2.cartesianProduct(m1.get(k).stream(),m2.get(k).stream()));
	}
	
	public static <E> Iterable<E> toIterable(Stream<E> st){
		return st::iterator;
	}
	
	public static <K,E> Map<K,List<E>> groupingList(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key));
	}
	
	public static <E,K,R> Map<K,List<R>> groupingList(Stream<E> st,Function<E,K> key, 
			Function<E,R> value){
		return st.collect(Collectors.groupingBy(key,
				Collectors.mapping(value,Collectors.toList())));
	}
	
	public static <E,K,R> SortedMap<K,List<R>> groupingList(Stream<E> st,Function<E,K> key, 
			Function<E,R> value, Comparator<K> order){
		return st.collect(Collectors.groupingBy(key,
				() ->new TreeMap<K,List<R>>(order),
				Collectors.mapping(value,Collectors.toList())));
	}
	
	public static <E,K,T,R> Map<K,R> groupingList(Stream<E> st,Function<E,K> key, Function<E,T> value, 
			Function<List<T>,R> andThen){
		return st.collect(Collectors.groupingBy(key,Collectors.mapping(value,
				Collectors.collectingAndThen(Collectors.toList(),andThen))));
	}
	
	public static <E,K> Map<K,Set<E>> groupingSet(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key,Collectors.toSet()));
	}
	
	public static <E,K> SortedMap<K,Set<E>> groupingSet(Stream<E> st,Function<E,K> key, Comparator<K> order){
		return st.collect(Collectors.groupingBy(key,
				() ->new TreeMap<K,Set<E>>(order),
				Collectors.toSet()));
	}
	
	public static <E,K,T> Map<K,Set<T>> groupingSet(Stream<E> st,Function<E,K> key, Function<E,T> value){
		return st.collect(Collectors.groupingBy(key,Collectors.mapping(value,Collectors.toSet())));
	}
	
	
	public static <E,K,T> SortedMap<K,Set<T>> groupingSet(Stream<E> st,Function<E,K> key,Function<E,T> value,
					Comparator<K> order){
		return st.collect(Collectors.groupingBy(key,
				() ->new TreeMap<K,Set<T>>(order),
				Collectors.mapping(value,Collectors.toSet())));
	}
	
	public static <E,K,T,R> Map<K,R> groupingSet(Stream<E> st,Function<E,K> key, 
			Function<E,T> value, Function<Set<T>,R> andThen){
		return st.collect(Collectors.groupingBy(key,
				Collectors.mapping(value,
						Collectors.collectingAndThen(Collectors.toSet(),andThen))));
	}
	
	public static <E> SortedMap<E,Integer> groupingSize(Stream<E> st,Comparator<E> order){
		return Stream2.groupingSize(st, x->x, order);
	}
	
	public static <E,K> SortedMap<K,Integer> groupingSize(Stream<E> st,Function<E,K> key,Comparator<K> order){
		return st.collect(Collectors.groupingBy(key,
				()->new TreeMap<K,Integer>(order),
				Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
	}
	
	public static <E> Map<E,Integer> groupingSize(Stream<E> st){
		return Stream2.groupingSize(st, x->x);
	}
	
	public static <E,K> Map<K,Integer> groupingSize(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key,
				Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
	}
	
	public static <E,K> Map<K,E> groupingReduce(Stream<E> st, Function<E,K> key, BinaryOperator<E> op){
		return st.collect(Collectors.groupingBy(key,
				Collectors.collectingAndThen(Collectors.reducing(op),e->e.get())));
	}
	
	public static <E,K,T> Map<K,T> groupingReduce(Stream<E> st, Function<E,K> key,  
			BinaryOperator<T> op, Function<E,T> value){
		return st.collect(Collectors.groupingBy(key,
				Collectors.mapping(value,
						Collectors.collectingAndThen(Collectors.reducing(op),e->e.get()))));
	}
	
	public static <E,K,T> SortedMap<K,T> groupingReduce(Stream<E> st, Function<E,K> key,  
			BinaryOperator<T> op, Function<E,T> value, Comparator<K> order){
		return st.collect(Collectors.groupingBy(key, 
				()->new TreeMap<K,T>(order),
				Collectors.mapping(value,
						Collectors.collectingAndThen(Collectors.reducing(op),e->e.get()))));
	}
	
	public static void main(String[] args) {
		List<Double> ls = List.of(2.3,3.4,5.6,7.8);
		List<Pair<Integer,Double>> ls2 = Stream2.enumerate(ls.stream()).map(e->Pair.of(e.counter(),e.value()))
				.toList();
		System.out.println(ls2);
	}

}
