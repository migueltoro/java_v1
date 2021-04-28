package us.lsi.tools;

import java.util.function.Function;
import java.util.stream.Stream;

public record Enumerate<E>(Integer counter, E value) {

	public static <E> Enumerate<E> of(Integer num, E value) {
		return new Enumerate<E>(num, value);
	}
	
	public <R> Stream<Enumerate<R>> expand(Function<E, Stream<R>> f){
		return f.apply(this.value()).map(e->Enumerate.of(this.counter(),e));
	}

}
