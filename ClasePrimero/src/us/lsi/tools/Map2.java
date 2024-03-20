package us.lsi.tools;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Map2 {
	
	
	public static <V,E> String toString(Map<E,V> m) {
		return m.keySet().stream()
				.map(key->String.format("(%s,%s)",key,m.get(key)))
				.collect(Collectors.joining("\n"));			
	}
	
	public static <V,E> String toString(Map<E,V> m, String sep, String prefix, String suffix) {
		return m.keySet().stream()
				.map(key->String.format("(%s,%s)",key,m.get(key)))
				.collect(Collectors.joining(sep,prefix,suffix));			
	}
	
	
	public static <K,V> Map<V,List<K>> invert(Map<K,V> m) {
		return m.keySet().stream()
				.collect(Collectors.toMap(x->m.get(x),x->List.of(x),List2::union));			
	}
	
	/**
	 * @param <K> tipo de las claves
	 * @param <V> tipo de los valores
	 * @param entries Una serie de pares clave valor
	 * @return Un Map construido con esas claves
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> of(Entry<? extends K, ? extends V>... entries) {
        Map<K, V> result = new HashMap<>(entries.length);

        for (Entry<? extends K, ? extends V> entry : entries)
            if (entry.getValue() != null)
                result.put(entry.getKey(), entry.getValue());

        return result;
    }
	
	
	public static <K,V> Map<K,V> of(K key,V value){
		Map<K,V> m = new HashMap<>();
		m.put(key,value);
		return m;
	}
	
	public static <K,V> Map<K,V> of(K key1,V value1,K key2,V value2){
		Map<K,V> m = new HashMap<>();
		m.put(key1,value1);
		m.put(key2,value2);
		return m;
	}
	
	public static <K,V> Map<K,V> of(K key1,V value1,K key2,V value2,K key3,V value3){
		Map<K,V> m = new HashMap<>();
		m.put(key1,value1);
		m.put(key2,value2);
		m.put(key3,value3);
		return m;
	}
	
	public static <K, V> Map<K, V> merge(Map<K,V> m1, Map<K,V> m2) {
		Map<K, V> r = new HashMap<>(m1);
		r.putAll(m2);
		return r;
	}
	
	
	public static <K1, V1, V2> Map<K1, V2> map(Map<K1,V1> m1, Function<V1,V2> value) {
		return Map2.map(m1,x->x,value);
	}
	
	
	public static <K1, V1, K2, V2> Map<K2, V2> map(Map<K1,V1> m1, Function<K1,K2> key, Function<V1,V2> value) {
		return m1.entrySet().stream()
				.collect(Collectors.toMap(e->key.apply(e.getKey()),e->value.apply((e.getValue()))));
	}
	
	public static <K, V> Entry<K,V> entry(K key, V value) {
		return new AbstractMap.SimpleEntry<>(key,value);
	}
	
	

}
