package us.lsi.ejemplos_b3.tipos;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import us.lsi.tools.IntPair;
import us.lsi.tools.Preconditions;

public class MatrizL1 extends Matriz1<Long> implements MatrizL {
	
	public static MatrizL as(Matriz1<Long> m) {
		return new MatrizL1(m.datos);
	}

	public static MatrizL ofI(List<List<Long>> datos) {
		return new MatrizL1(datos);
	}
	
	public static MatrizL identity(Integer n) {
		List<List<Long>> datos = IntStream.range(0, n).boxed()
				.map(f->IntStream.range(0, n).boxed()
						.map(c -> c==f?1L:0L).toList()).toList();
		return MatrizL1.ofI(datos);
	}

	private MatrizL1(List<List<Long>> datos) {
		super(datos);
	}

	@Override
	public Boolean esAntisimetrica() {
		return IntStream.range(0,this.nf()).boxed()
				.flatMap(x->IntStream.range(0,this.nc()).boxed().map(y->IntPair.of(x,y)))
				.filter(p->p.second() > p.first())
				.allMatch(p->this.get(p.first(),p.second())
						.equals(-this.get(p.first(),p.second())));
						
	}

	@Override
	public MatrizL add(MatrizL m) {
		Preconditions.checkArgument(this.nf() == m.nf() && this.nc() == m.nc(), 
				String.format("No se pueden sumar"));
		List<List<Long>> datos = IntStream.range(0, this.nc()).boxed()
				.map(f->IntStream.range(0, this.nf()).boxed()
						.map(c -> this.get(f, c) + m.get(f, c)).toList()).toList();
		return MatrizL1.ofI(datos);
	}

	@Override
	public MatrizL subtract(MatrizL m) {
		Preconditions.checkArgument(this.nf() == m.nf() && this.nc() == m.nc(), 
				String.format("No se pueden restar"));
		List<List<Long>> datos = IntStream.range(0, this.nc()).boxed()
				.map(f->IntStream.range(0, this.nf()).boxed()
						.map(c -> this.get(f, c) - m.get(f, c)).toList()).toList();
		return MatrizL1.ofI(datos);
	}

	@Override
	public MatrizL multiply(MatrizL m) {
		Preconditions.checkArgument(this.nc() == m.nf(), String.format("No se pueden multiplicar"));
		BiFunction<Integer, Integer, Long> ss = (f, c) -> IntStream.range(0, this.nc()).boxed()
				.map(k -> this.get(f, k) * m.get(k, c))
				.reduce(0L,(x, y) -> x+y);
		List<List<Long>> datos = IntStream.range(0, this.nf()).boxed()
				.map(f->IntStream.range(0, this.nc()).boxed()
						.map(c -> ss.apply(f, c)).toList()).toList();		
		return MatrizL1.ofI(datos);
	}

	@Override
	public MatrizL negate() {
		List<List<Long>> datos = IntStream.range(0, this.nc()).boxed()
				.map(f->IntStream.range(0, this.nf()).boxed()
						.map(c -> -this.get(f, c)).toList()).toList();
		return MatrizL1.ofI(datos);
	}

	@Override
	public MatrizL pow(Integer n) {
		return IntStream.range(0, n).boxed()
				.<MatrizL>map(i->this)
				.reduce((x,y)->x.multiply(y))
				.get();
	}
	
}
