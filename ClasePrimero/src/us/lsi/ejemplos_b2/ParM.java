package us.lsi.ejemplos_b2;

import java.util.Objects;

public class ParM<A, B> // implements Comparable<ParM<A,B>>
	{

	public static <A, B> ParM<A, B> of(A a, B b) {
		return new ParM<A, B>(a, b);
	}

	private A a;
	private B b;

	private ParM(A a, B b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
	
	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}
	
	public ParM<A, B> getCopy() {
		return new ParM<>(this.a, this.b);
	}
	
//	@Override
//	public int compareTo(ParM<A, B> o) {
//		int r = this.a().compareTo(other.a());
//		if(r==0) r = this.b().compareTo(other.b());
//		return r;
//	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)", a, b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParM<?, ?> other = (ParM<?, ?>) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b);
	}

	public static void main(String[] args) {
		ParM<String,Double> p = ParM.of("Juan",34.5);
		System.out.println(p);
		p.setB(78.5);
		System.out.println(p);
		
	}

}
