package us.lsi.calculos;

import org.apache.commons.math3.fraction.Fraction;

public class Fraction2 extends Fraction implements Comparable<Fraction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Fraction2 of(int num, int den) {
		return new Fraction2(num, den);
	}

	private Fraction2(int num, int den) {
		super(num, den);
	}
	
	@Override
	public int compareTo(Fraction other) {
		return super.getNumerator()*other.getDenominator() - super.getDenominator()*other.getNumerator();
	}
	
	@Override
	public String toString() {
		String s = "";
		s = String.format("%d",super.getNumerator());
		if(super.getDenominator() != 1) s = s+ String.format("%d",super.getDenominator());
		return s;
	}

}
