package us.lsi.tools;

public record Enumerate<E>(Integer counter, E value) {

	public static <E> Enumerate<E> of(Integer num, E element) {
		return new Enumerate<E>(num, element);
	}

	@Override
	public String toString() {
		return String.format("(%d,%s)",counter,value.toString());
	}

}
