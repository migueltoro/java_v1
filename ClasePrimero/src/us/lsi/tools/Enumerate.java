package us.lsi.tools;

public record Enumerate<E>(Integer counter, E value) {

	public static <E> Enumerate<E> of(Integer counter, E value) {
		return new Enumerate<E>(counter, value);
	}

}
