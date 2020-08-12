package us.lsi.tools;

public record IntPair(Integer first,Integer second) {

	public static IntPair of(Integer first, Integer second) {
		return new IntPair(first, second);
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d)",first,second);
	}
	
}
