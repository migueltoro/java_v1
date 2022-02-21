package us.lsi.calculos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tools.Preconditions;

public class ListasStreams {

	// List<E>

	public static void ejemploListas() {
		List<Integer> ls = new ArrayList<>();
		ls.add(23);
		ls.add(567);
		ls.add(65);
		System.out.println(ls);
		System.out.println(ls.get(2));
		List<Integer> ls2 = Arrays.asList(23, 567, 65, 65);
		System.out.println(ls2);
		List<Integer> ls3 = List.of(23, 567, 65);
		System.out.println(ls3);
		Set<Integer> s1 = new HashSet<>();
		s1.add(23);
		s1.add(567);
		s1.add(65);
		System.out.println(s1);
		Set<Integer> s2 = Set.of(23, 567, 65);
		System.out.println(s2);
		Set<Integer> s3 = new HashSet<>(Set.of(23, 567, 65));
		s3.add(56);
		s3.remove(567);
		System.out.println(s3);
		Integer sm = 0;
		for (Integer e : ls) {
			sm = sm + e;
		}
		System.out.println(sm);
		Integer sm2 = 0;
		for (Integer e : s1) {
			sm2 = sm2 + e;
		}
		System.out.println(sm2);
		Integer[] a = { 23, 567, 65 };
		Integer sm3 = 0;
		for (Integer e : a) {
			sm3 = sm3 + e;
		}
		System.out.println(a[2]);
		a[1] = -23;
		System.out.println(Arrays.asList(a));
		Set<Integer> s4 = new HashSet<>(ls2);
		System.out.println(ls2);
		System.out.println(s4);
	}

	public static Stream<Integer> secuenciaAritmetica(Integer a, Integer b, Integer c) {
		return IntStream.range(a, b).boxed().filter(e -> (e - a) % c == 0);
	}

	public static Double media(Stream<Integer> st) {
		Tmedia ac = new Tmedia(0, 0);
		Iterable<Integer> it = () -> st.iterator();
		for (Integer e : it) {
			ac = new Tmedia(ac.sum + e, ac.n + 1);
		}
		Preconditions.checkArgument(ac.n > 0, String.format("El iterador esta vacio"));
		return (1.0 * ac.sum) / ac.n;
	}

	public static record Tmedia(Integer sum, Integer n) {
	}

}
