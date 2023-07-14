package us.lsi.tools;

public record Pair<A, B> (A first, B second) {
	
	public static <A,B> Pair<A, B> of(A first, B second){
		return new Pair<>(first,second);
	}
	
	public Pair<A, B> copy(){
		return new Pair<>(this.first,this.second);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",this.first,this.second);
	}
	

}

