package us.lsi.tools;

public class IntPair {
	
	public static IntPair of(Integer first, Integer second) {
		return new IntPair(first, second);
	}
	public Integer first;
	public Integer second;
	private IntPair(Integer first, Integer second) {
		super();
		this.first = first;
		this.second = second;
	}
	@Override
	public String toString() {
		return String.format("(%d,%d)",first,second);
	}
}
