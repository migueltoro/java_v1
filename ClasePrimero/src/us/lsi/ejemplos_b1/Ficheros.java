package us.lsi.ejemplos_b1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.tools.FileTools;

public class Ficheros {
	
	public static List<Integer> listaDeFichero(String file) {
		List<String> lns = FileTools.lineasFromFile(file);
		List<Integer> r = new ArrayList<>();
	    for(String linea:lns) {
	        for(String p: linea.split(",")) {
	            if(!p.isEmpty())
	                r.add(Integer.parseInt(p));
	        }
	    }
	    return r;
	}
	
	public static Integer sumaElementosFichero(String file) {
		List<String> lns = FileTools.lineasFromFile(file);
		Integer r = 0;
	    for(String linea:lns) {
	        for(String p: linea.split("[ ,]")) {
	            if(!p.isEmpty())
	                r = r + Integer.parseInt(p);
	        }
	    }
	    return r;
	}

	public static Integer sumaElementosFicheroIf(String file,Predicate<Integer> pd) {
		List<String> lns = FileTools.lineasFromFile(file);
		Integer r = 0;
	    for(String linea:lns) {
	        for(String p: linea.split(",")) {
	            if(!p.isEmpty()) {
	            	Integer e = Integer.parseInt(p);
	                if(pd.test(e))
	                	r = r + e;
	            }
	        }
	    }
	    return r;
	}

	

	public static void main(String[] args) { 
	       System.out.println(listaDeFichero("ficheros/numeros_2.txt"));
	       System.out.println(sumaElementosFichero("ficheros/numeros_2.txt"));
	       System.out.println(sumaElementosFicheroIf("ficheros/numeros_2.txt",x->x%2==0));
	}

}
