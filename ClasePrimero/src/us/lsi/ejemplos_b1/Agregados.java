package us.lsi.ejemplos_b1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Agregados {
	
	public static record Jugador(String nombre, String apellidos, Integer edad) {
	}
	
	public static void records() {
		Jugador jugador = new Jugador("Mark", "Lenders", 15);
		System.out.printf("%s %s\n","Nombre:", jugador.nombre());
		System.out.printf("%s %s\n","Apellidos:", jugador.apellidos());
		System.out.printf("%s %s\n","Edad:", jugador.edad());
//      jugador.edad() = 16
	    System.out.println("---------------");
	}
	
	public static void listas() {
		List<Integer> temperaturas = List.of(32, 36, 35, 36, 32, 33); 
		System.out.println(temperaturas);
		System.out.println(temperaturas.get(0));
		System.out.println(temperaturas.size());
		List<Integer> tm = new ArrayList<>(temperaturas);
		tm.add(29); 
		tm.add(29); 
		Integer a = 32;
		tm.remove(a);
		System.out.println(tm);
		System.out.println(tm.get(0));
		System.out.println(tm.size());
		System.out.println("---------------");
	}
	
	public static void arrays() {
		Integer[] temperaturas = {32, 36, 35, 36, 32, 33}; 
		System.out.println(temperaturas);
		temperaturas[0] = -2;
		System.out.println(temperaturas[0]);
		System.out.println(temperaturas.length);
		List<Integer> tm = new ArrayList<>(Arrays.asList(temperaturas));
		tm.add(29); 
		tm.add(29); 
		System.out.println(tm);
		System.out.println(tm.get(0));
		System.out.println(tm.size());
		System.out.println("---------------");
	}
	
	public static void conjuntos() {
		Set<Integer> temperaturas_conjunto = Set.of(32,35,36,33,34); 
		System.out.println(temperaturas_conjunto);
		System.out.println(temperaturas_conjunto.size());
		Set<Integer> sm = new HashSet<>(temperaturas_conjunto);
		sm.add(29); 
		sm.add(29);
		sm.remove(32);
		System.out.println(sm);
		System.out.println(sm.size());
		System.out.println("---------------");
	}
	
	public static void maps() {
		Map<String,Double> temperaturas_por_provincias = 
			Map.of("Almeria",19.9, "Cadiz", 19.1, "Cordoba", 19.1, 
                "Granada",16.6, "Jaen", 18.2, "Huelva", 19.0,  "Malaga", 19.8, "Sevilla", 20.0);
		System.out.println(temperaturas_por_provincias);
		System.out.println(temperaturas_por_provincias.size());
		System.out.println(temperaturas_por_provincias.get("Malaga"));
		Map<String,Double> mm = new HashMap<>(temperaturas_por_provincias);
		mm.put("Badajoz",15.8);
		mm.put("Badajoz",20.8);
		System.out.println(mm);
		System.out.println(mm.size());
		System.out.println(temperaturas_por_provincias.get("Malaga"));
		System.out.println("---------------");
	}

	public static void main(String[] args) {
		records();
		arrays();
		listas();
		conjuntos();
		maps();
	}

}
