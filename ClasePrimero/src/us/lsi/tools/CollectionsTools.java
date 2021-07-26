package us.lsi.tools;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionsTools {
	
	public static <V,E> String mapToString(Map<E,V> m) {
		return m.keySet().stream()
				.map(key->String.format("(%s,%s)",key,m.get(key)))
				.collect(Collectors.joining("\n"));			
	}
	
	public static <E> String collectionToString(Collection<E> c) {
		return c.stream()
				.map(e->e.toString())
				.collect(Collectors.joining("\n"));			
	}
	
	public static <K,V> Map<V,List<K>> invert(Map<K,V> m) {
		return m.keySet().stream()
				.collect(Collectors.toMap(x->m.get(x),x->List.of(x),(ls1,ls2)->{ls1.addAll(ls2);return ls1;}));			
	}

}
