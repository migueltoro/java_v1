package us.lsi.tools;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class StreamTools {
	
	public static <L, R, T> Spliterator<T> zip(Spliterator<L> lefts, Spliterator<R> rights,
			BiFunction<L, R, T> combiner) {
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
		return StreamSupport.stream(zip(lefts,rights,combiner), leftStream.isParallel() || rightStream.isParallel());
	}
	
	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream, Integer start){
		Stream<Integer> st = Stream.iterate(start,e->e+1);
		return zip(stream,st,(e,n)->Enumerate.of(n, e));
	}
	
	public static <E> Stream<Enumerate<E>> enumerate(Stream<E> stream){
		return enumerate(stream,0);
	}
	
	public static <E> Iterable<E> toIterable(Stream<E> st){
		return st::iterator;
	}
	
	public static <E> List<E> toList(Stream<E> st){
		return st.collect(Collectors.toList());
	}
	
	public static <E> Set<E> toSet(Stream<E> st){
		return st.collect(Collectors.toSet());
	}
	
	public static <E> SortedSet<E> toSortedSet(Stream<E> st){
		return st.collect(Collectors.toCollection(()->new TreeSet<>()));
	}
	
	public static <E> SortedSet<E> toSortedSet(Stream<E> st, Comparator<E> cmp){
		return st.collect(Collectors.toCollection(()->new TreeSet<>(cmp)));
	}
	
	public static <K,E> Map<K,E> toMap(Stream<E> st, Function<E,K> key){
		return st.collect(Collectors.toMap(key,x->x));
	}
	
	public static <K,V,E> Map<K,V> toMap(Stream<E> st, Function<E,K> key, Function<E,V> value){
		return st.collect(Collectors.toMap(key,value));
	}
	
	public static <K,V,E> Map<K,V> toMap(Stream<E> st, Function<E,K> key, Function<E,V> value, BinaryOperator<V> op){
		return st.collect(Collectors.toMap(key,value,op));
	}
	
	public static <K,E> Map<K,List<E>> groupingList(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key));
	}
	
	public static <E,K,T> Map<K,List<T>> groupingList(Stream<E> st,Function<E,K> key, Function<E,T> map){
		return st.collect(Collectors.groupingBy(key,Collectors.mapping(map,Collectors.toList())));
	}
	
	public static <E,K> Map<K,Set<E>> groupingSet(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key,Collectors.toSet()));
	}
	
	public static <E,K> Map<K,SortedSet<E>> groupingSortedSet(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key,Collectors.toCollection(TreeSet::new)));
	}
	
	public static <E,K> Map<K,Integer> counting(Stream<E> st,Function<E,K> key){
		return st.collect(Collectors.groupingBy(key,
				Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
	}
	
	public static <E> Map<E,Integer> counting(Stream<E> st){
		return st.collect(Collectors.groupingBy(x->x,
				Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
	}
	
	public static <E,K> Map<K,E> groupingReduce(Stream<E> st, Function<E,K> key, BinaryOperator<E> op){
		return st.collect(Collectors.groupingBy(key,
				Collectors.collectingAndThen(Collectors.reducing(op),e->e.get())));
	}
	
	public static <E,K> Map<K,E> groupingReduce(Stream<E> st, Function<E,K> key, BinaryOperator<E> op, E a0){
		return st.collect(Collectors.groupingBy(key,Collectors.reducing(a0,op)));
	}
	
	public static <E,K,T> Map<K,T> groupingReduce(Stream<E> st, Function<E,K> key, Function<E,T> map, BinaryOperator<T> op, T a0){
		return st.collect(Collectors.groupingBy(key,Collectors.reducing(a0,map,op)));
	}

}
