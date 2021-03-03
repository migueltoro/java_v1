package us.lsi.calculos;

import org.apache.commons.math3.complex.Complex;

public class Complex2 extends Complex {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static Complex2 of(Double real) {
		return new Complex2(real,0.);
	}
	
	public static Complex2 of(Double real, Double imaginary) {
		return new Complex2(real, imaginary);
	}

	private Complex2(Double real, Double imaginary) {
		super(real, imaginary);
	}

	@Override
	public String toString() {
		String s = "";
		s = String.format("%.2f",super.getReal());
		if(super.getImaginary() != 0.) s = s+ String.format("+%.2fi",super.getImaginary());
		return s;
	}

}

