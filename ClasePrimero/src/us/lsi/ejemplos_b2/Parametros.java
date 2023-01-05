package us.lsi.ejemplos_b2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Parametros {

	public static Function<Integer,Integer> multiplicaPorDos = n -> 2 * n;
	public static Predicate<Integer> par = n -> n%2==0;
	
	public static <E> List<E> transforma(List<E> ls){
		return transforma(ls,x->x);
	}

	public static <E,R> List<R> transforma(List<E> ls,Function<E,R> t){
		List<R> lt = new ArrayList<>();
		for(E e:ls)
		    lt.add(t.apply(e));
	    return lt;
	}
	
	public static <E> List<E> filtra(List<E> ls){
		return filtra(ls,x->true);
	}
	    
	public static <E> List<E> filtra(List<E> ls,Predicate<E> p){
		List<E> lt = new ArrayList<>();
		for(E e:ls)
			if(p.test(e))
				lt.add(e);
	    return lt;
	}
	
	public static Integer sumar(Integer... nums) {
	    Integer total = 0;
	    for (int i=0 ; i < nums.length ; i++) {
	        total += nums[i];
	    }
	    return total;
	}
	
	public static <E> void actualiza(List<E> ls, Integer n, E e) {
	    ls.set(n, e);
	}
	
	public static void main(String[] args) {
		System.out.println(multiplicaPorDos.apply(45));
	    List<Integer> ls = List.of(1, 2, 3, 4, 5);
	    System.out.println(transforma(ls,e->Math.sqrt(e)));
	    System.out.println(transforma(ls,y->y*y));
	    System.out.println(filtra(ls,par));
	    System.out.println(filtra(ls));
	    System.out.println(sumar(3,4,5,6,7,89));
	    List<Integer> ml = new ArrayList<>(ls);
	    actualiza(ml,4,7);
	    System.out.println(ml);
	}

}
