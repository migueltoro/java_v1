package us.lsi.tools;

import java.util.function.Function;
import java.util.stream.Stream;

public record Enumerate<E>(Integer counter, E value) {

	public static <E> Enumerate<E> of(Integer counter, E value) {
		return new Enumerate<E>(counter, value);
	}
	
	public <R> Stream<Enumerate<R>> stream(Function<E,Stream<R>> f){
		return f.apply(this.value())
				.map(v->Enumerate.of(this.counter(),v));
	}

}
