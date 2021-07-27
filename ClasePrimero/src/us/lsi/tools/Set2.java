package us.lsi.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Set2 {
	
	@SafeVarargs
	public static <E> Set<E> of(E... e){
		return Arrays.stream(e).collect(Collectors.toSet());
	}

	
	public static <E> Set<E> difference(Set<E> s1,  Set<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.removeAll(s2);
		return s;
	}
	
	public static <E> Set<E> union(Set<E> s1,  Set<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.addAll(s2);
		return s;
	}
	
	public static <E> Set<E> intersection(Set<E> s1,  Set<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.retainAll(s2);
		return s;
	}
	
	public static <E> Boolean contains(Set<E> s1,  Set<E> s2){
		return s1.containsAll(s2);
	}
	
	public static <E> String toString(Set<E> c) {
		return c.stream()
				.map(e->e.toString())
				.collect(Collectors.joining("\n"));			
	}
	
	public static <E> String toString(Set<E> c, String sep, String prefix, String suffix) {
		return c.stream()
				.map(e->e.toString())
				.collect(Collectors.joining(sep,prefix,suffix));			
	}

}
