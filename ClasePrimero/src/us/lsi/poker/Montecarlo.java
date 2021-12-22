package us.lsi.poker;

import java.util.List;
import java.util.stream.IntStream;
import us.lsi.geometria.Punto2D;

public class Montecarlo {
	
	public static Double montecarlo(Integer n) {
	    List<Punto2D> st = IntStream.range(0,n)
	    		.mapToObj(e-> Punto2D.of(Math.random()*2-1,Math.random()*2-1))
	    		.toList();
	    
	    Integer pt = 0; 
	    Integer pi = 0;
	    for(Punto2D p:st) {
	        pt = pt + 1;
	        if(p.distanciaAlOrigen() <= 1)
	            pi = pi + 1;
	    }
	    return 4.*pi/pt;
	}
	
	public static void main(String[] args) {
		System.out.println(montecarlo(1000000));
	}

}
