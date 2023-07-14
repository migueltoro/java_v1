package us.lsi.ejemplos_b3.tipos;

public interface Matriz<E> {

	Integer nf();

	Integer nc();

	E get(Integer f, Integer c);

	Boolean esSimetrica();

	Matriz<E> traspuesta();

}