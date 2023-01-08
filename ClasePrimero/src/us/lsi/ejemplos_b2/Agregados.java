package us.lsi.ejemplos_b2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Agregados {
	
	public static void listasConjuntos() {
		Random rn = new Random(System.nanoTime());
		List<Integer> t1 = List.of(32, 36, 35, 36, 32, 33); 
		List<Integer> t2 = new ArrayList<>(List.of(32, 36, 35)); 
		t2.addAll(t1);
		System.out.println(t2);
		t2 = new ArrayList<>(List.of(32, 36, 35)); 
		t2.removeAll(t1);
		System.out.println(t2);
		t2 = new ArrayList<>(List.of(32, 36, 35)); 
		t2.retainAll(t1);
		System.out.println(t2);
		t2 = new ArrayList<>(List.of(32, 36, 35)); 
		System.out.println(t1.containsAll(t2));
		System.out.println(t1.subList(1, 4));
		List<Integer> p = IntStream.range(0,100).boxed().map(i->rn.nextInt(1000)).toList();
		System.out.println(p);
		Set<Integer> st = IntStream.range(0,100).boxed()
				.map(i->rn.nextInt(1000)).collect(Collectors.toSet());
		System.out.println(st);
		System.out.println("---------------");
	}
	
	public static void maps() {
		Map<String,Double> tp = 
			new HashMap<>(Map.of("Almeria",19.9, "Cadiz", 19.1, "Cordoba", 19.1, 
                "Granada",16.6, "Jaen", 18.2, "Huelva", 19.0,  "Malaga", 19.8, "Sevilla", 20.0));
		System.out.println(tp);
		Map<String,Double> t2 = 
				Map.of("Almeria",20.9, 
	                "Granada",19.6, "Jaen", 18.2, "Huelva", 19.0,  "Malaga", 19.8, "Madrid", 23.);
		tp.putAll(t2);
		System.out.println(tp);
		Map<Integer,Integer> m = IntStream.range(0,10).boxed().collect(Collectors.toMap(i->i,i->i*i));
		System.out.println(m);
		System.out.println("---------------");
	}
	
	public static void main(String[] args) {
		listasConjuntos();
		maps();
	}

}
