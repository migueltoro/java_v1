package us.lsi.tools;

public record Par<A, B> (A a, B b) {
	
	public static <A,B> Par<A, B> of(A a, B b){
		return new Par<>(a,b);
	}
	
	public Par<A, B> copy(){
		return new Par<>(this.a,this.b);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",this.a,this.b);
	}
	

}

