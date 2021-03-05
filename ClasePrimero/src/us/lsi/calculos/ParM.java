package us.lsi.calculos;

public class ParM<A,B>  {


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
	
	public ParM<A, B> copy(){
		return of(this.a, this.b);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",this.a,this.b);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParM<?,?> other = (ParM<?,?>) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}

	
	
	
}

