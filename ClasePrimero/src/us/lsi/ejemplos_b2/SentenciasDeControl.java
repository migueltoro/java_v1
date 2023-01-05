package us.lsi.ejemplos_b2;

import java.util.List;
import java.util.Scanner;

import us.lsi.tools.Preconditions;

public class SentenciasDeControl {
	
	public static Double media(List<Integer> ls) {
	    Integer n = 0;
	    Double s = 0.;
	    for(Integer e:ls) {
	    	n = n+1;
	    	s = s+e;
	    }
	    Preconditions.checkArgument(n>0,"La lista esta vacia");
	    return s/n;
	}

	public static Double desviacionTipica(List<Integer> ls) {
		Integer n = 0;
	    Double s = 0.;
	    Double s2 = 0.;
	    for(Integer e:ls) {
	        n = n+1;
	    	s = s+e;
	    	s2 = s2 +e*e;
	    }
	    Preconditions.checkArgument(n>0,"La lista esta vacia");
	    Double md = s/n;
	    return Math.sqrt(s2/n-md*md);
	}

	public static Integer sumaAritmetica(Integer a,Integer b,Integer c) {
	    Integer s = 0;
	    for(int e=a;e<b;e=e+c) {
	        s = s + e;
	    }
	    return s;
	}

	public static Integer sumaPrimeros(Integer m, Integer n) {
	    Integer i = 0;
	    Integer s = 0;
	    for(int e=1;e<n;e=e+1) {
	        if(i < n)
	            s = s + e;
	        else
	            break;
	        i = i +1;
	    }
	    return s;
	}
	
	public static void ejemploWhile() {
		Integer n = 10;
		Integer suma = 0;
		int i = 1;
		while (i <= n) {
			suma = suma + i;
			i++;
		}
	}
	
	public static record Mcd(Integer a, Integer b) {}

	public static Integer mcd(Integer a, Integer b) {
	    Preconditions.checkArgument(a>=0 && b>0,
	    		String.format("El coeficiente a debe ser mayor o igual que cero y b mayor que cero y son: a = %d, b = %d",a,b));
	    Mcd d = new Mcd(a,b);
	    while(d.b() > 0) 
//	    	a, b = b, a%b
	        d = new Mcd(d.b(),d.a()%d.b());
	    return d.a();
	}
	
	public static Character ejemploSwitch(String r) {
		Character s = switch (r) {
		case "Lunes" -> r.charAt(0);
		case "Martes" -> r.charAt(1);
		default -> throw new IllegalArgumentException("Unexpected value: " + r);
		};
		return s;
	}
	

	public static void saludo() {
		Scanner in = new Scanner(System.in);
		System.out.println("Hola");
	    Integer bienestar = Integer.parseInt(in.nextLine());

	    while(bienestar < 1 || bienestar > 5) {
	    	System.out.println("Por favor, introduce un valor del 1 al 5:");
	        bienestar = Integer.parseInt(in.nextLine());
	    }
	    
	    in.close();

	    if(bienestar < 3)  // 1 o 2
	    	System.out.println("Veras como el dia mejora.");
	    else if( bienestar < 5) // 3 o 4
	    	System.out.println("No esta mal, hoy sera un gran dia");
	    else // 5
	    	System.out.println("Me alegro de que te sientas bien");
	}

	public static void main(String[] args) {
//		saludo();
		System.out.println(mcd(2,2024));
		System.out.println(ejemploSwitch("Lunes"));
	}

}
