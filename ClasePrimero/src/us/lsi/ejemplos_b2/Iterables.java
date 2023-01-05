package us.lsi.ejemplos_b2;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Iterables {
	
	
	public static Integer sumaAritmetica(Integer a,Integer b,Integer c) {
	    Integer s = 0;
	    for(int e=a;e<b;e=e+c) {
	        s = s + e;
	    }
	    return s;
	}
	
	
	public static void ejemplos() {
		Set<Integer> tc = Set.of(32,35,36,33,34); 
		Integer s = 0;
		for(Integer e:tc) {
			s = s+e;
		}
		System.out.println(s);
		Map<String,Double> tm = 
				Map.of("Almeria",19.9, "Cadiz", 19.1, "Cordoba", 19.1, 
	                "Granada",16.6, "Jaen", 18.2, "Huelva", 19.0,  "Malaga", 19.8, "Sevilla", 20.0);
		Double sd = 0.;
		for(Double e:tm.values()) {
			sd = sd+e;
		}
		System.out.println(sd);
		Integer sl = 0;
		for(String e:tm.keySet()) {
			sl = sl+e.length();
		}
		System.out.println(sl);
		Double sl2 = 0.;
		for(Entry<String, Double> e:tm.entrySet()) {
			if(e.getKey().charAt(0) == 'C')
				sl2 = sl2+e.getValue();
		}
		System.out.println(sl2);
	}
	

	public static void main(String[] args) {
		ejemplos();

	}

}
