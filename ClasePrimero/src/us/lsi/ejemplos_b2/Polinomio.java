package us.lsi.ejemplos_b2;

import java.util.List;

import org.apache.commons.lang3.math.Fraction;

public interface Polinomio<E> {

	Integer grado();

	E coeficiente(Integer i);
	
	List<E> coeficientes();

	Boolean constainsCoeficienteZero();

	E value(E v);

	Polinomio<E> add(Polinomio<E> other);

	Polinomio<E> subtract(Polinomio<E> other);

	Polinomio<E> multiply(Fraction other);

	Polinomio<E> multiply(Polinomio<E> other);

	Polinomio<E> pow(Integer n);

	Polinomio<E> derivada();

	Polinomio<E> integral(Fraction v);

}