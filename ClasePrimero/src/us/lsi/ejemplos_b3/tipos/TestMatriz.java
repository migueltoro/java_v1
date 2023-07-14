package us.lsi.ejemplos_b3.tipos;



public class TestMatriz {

	public static void main(String[] args) {
		 Matriz<Long> m2 = Matriz2.ofFile("ficheros/matriz4.txt"," ",x->Long.parseLong(x));
		 System.out.println(m2);
		 System.out.println("___________");
		 Matriz<Long> m1 = Matriz1.ofFile("ficheros/matriz4.txt"," ",x->Long.parseLong(x));
		 System.out.println(m1);
		 System.out.println("___________");
		 MatrizL m3 = 
				 MatrizL1.as(Matriz1.ofFile("ficheros/matriz2.txt"," ",x->Long.parseLong(x)));
		 System.out.println(m3);
		 MatrizL m4 = 
				 MatrizL2.as(Matriz2.ofFile("ficheros/matriz2.txt"," ",x->Long.parseLong(x)));
		 System.out.println(m4);
		 System.out.println("___________");
		 System.out.println(m3.add(m4));
		 System.out.println("___________");
		 System.out.println(m4.add(m3));
		 System.out.println("___________");
		 System.out.println(m3.subtract(m4));
		 System.out.println("___________");
		 System.out.println(m4.subtract(m3));
		 System.out.println("___________");
		 System.out.println(m3.multiply(m4));
		 System.out.println("___________");
		 System.out.println(m4.multiply(m3));
		 System.out.println("___________");
		 System.out.println(m3.negate());
		 System.out.println("___________");
		 System.out.println(m4.negate());
		 System.out.println(m2.traspuesta());
		 System.out.println(m1.traspuesta()); 
		 System.out.println(m2.esSimetrica()); 
		 System.out.println(m1.esSimetrica()); 	
		 System.out.println(m3.esAntisimetrica());
		 System.out.println(m4.esAntisimetrica());		
	}

}
