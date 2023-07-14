package us.lsi.ejemplos_b3.tipos;

public interface MatrizL extends Matriz<Long> {
	
	Boolean esAntisimetrica();
	
	MatrizL add(MatrizL m);
	
	MatrizL subtract(MatrizL m);
	
	MatrizL multiply(MatrizL m);
	
	MatrizL negate();
	
	MatrizL pow(Integer n);

}
