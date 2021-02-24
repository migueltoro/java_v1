package us.lsi.calculos;


import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.fraction.Fraction;

import static us.lsi.tools.StreamTools.*;

import us.lsi.tools.Preconditions;

public class Calculos {
	
	
	public static Double areaCirculo(Double radio) {
	    Preconditions.checkArgument(radio>=0,
	    		String.format("El radio debe ser mayor o igual a cero y es %.2f'",radio));
	    return Math.PI*radio*radio;
	}
	
	public static Double longitud_circunferencia(Double radio) {
	    Preconditions.checkArgument(radio>=0,
	    		String.format("El radio debe ser mayor o igual a cero y es %.2f'",radio));
	    return 2*Math.PI*radio;
	}
	    		
	public static Double sol_ecuacion_primer_grado(Double a,Double b) { 
	    Preconditions.checkArgument(a>0,
	    		String.format("El coeficiente a debe ser distinto de cero y es %.2f",a));
	    return -b/a;
	}
	
	public static Solucion2G solEcuacionSegundoGrado(Double a, Double b, Double c) {
	    Preconditions.checkArgument(a>0,
	    		String.format("El coeficiente a debe ser distinto de cero y es %.2",a));
	    Double disc = b*b-4*a*c;
	    if (disc >= 0) {
	        Double r1 = -b/(2*a);
	        Double r2 = Math.sqrt(disc)/(2*a);
	        Double s1 = r1+r2;
	        Double s2  = r1-r2;
	        return new Solucion2G(Complex.valueOf(s1),Complex.valueOf(s2));   		
	    }else {
	        Double re = -b/(2*a);
	        Double im = Math.sqrt(-disc)/(2*a);
	        return new Solucion2G(Complex.valueOf(re,im), Complex.valueOf(re,-im));
	    }
	        		
	}
	
	// ls = (e for e in range(a,b) if (e-a)%c==0)
	
	public static Stream<Integer> secuenciaAritmetica(Integer a,Integer b,Integer c) {
		return IntStream.range(a,b).boxed().filter(e->(e-a)%c==0);
	}
	
	public static Double media(Stream<Integer> st) {
	    Tmedia ac = new Tmedia(0,0);
	    for(Integer e: toIterable(st)) {
	    	ac = new Tmedia(ac.sum+e,ac.n+1);
	    }
	    Preconditions.checkArgument(ac.n>0,String.format("El iterador esta vacio"));
	    return (1.0*ac.sum)/ac.n; 
	}
	
	public static void fractions() {
		Fraction f1 = Fraction.getReducedFraction(6, 8);
	    Fraction f2 = Fraction.getReducedFraction(1, 4);
	    System.out.println(f1);
	    System.out.println(f1.subtract(f2));
	    System.out.println(f1.multiply(f2));
	}
	
	public static int mcd(int a, int b){		
	       Tmcd t = Tmcd.of(a, b);
	       while (t.b() != 0) {
	            t = Tmcd.of(t.b(),t.a()%t.b());
	       }
		    return t.a();
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(Calculos.solEcuacionSegundoGrado(4.,1.,1.));
		System.out.println(toList(Calculos.secuenciaAritmetica(5,20,3)));
		System.out.println(Calculos.media(Calculos.secuenciaAritmetica(5,20,3)));
		fractions();
		System.out.println(mcd(79,11));
	}
	
	public static record Tmcd(Integer a, Integer b) {
		public static Tmcd of(Integer a, Integer b) {
			return new Tmcd(a,b);
		}
	}
	
	public static record Tmedia(Integer sum, Integer n) {}
	
	public static record Solucion2G(Complex a, Complex b) {
		
		public static Solucion2G of(Complex a, Complex b) {
			return new Solucion2G(a,b);
		}
		
		public String toString() {	
			String ra = "";
			if(this.a.getReal() != 0.) ra = String.format("%s%.2f",ra,this.a.getReal());
			if(this.a.getImaginary() != 0.) ra = String.format("%s+%.2fi",ra,this.a.getImaginary());
			String rb = "";
			if(this.b.getReal() != 0.) rb = String.format("%s%.2f",rb,this.b.getReal());
			if(this.b.getImaginary() != 0.) rb = String.format("%s+%.2fi",rb,this.b.getImaginary());
	    	return String.format("(%s,%s)",ra,rb);
	    }
	}

}

