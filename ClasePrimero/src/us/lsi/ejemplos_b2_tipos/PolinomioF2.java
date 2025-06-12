package us.lsi.ejemplos_b2_tipos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.Fraction;

public record PolinomioF2(List<Fraction> coeficientes) implements Polinomio<Fraction> {
	
	public static  PolinomioF2 of(List<Fraction> coeficientes){
        return new PolinomioF2(coeficientes);
    }
	
	public static PolinomioF2 of(Fraction... coeficientes) {
        List<Fraction> coef = Arrays.asList(coeficientes);
        return new PolinomioF2(coef);
    }
	
	public static PolinomioF2 zero(){
        return new PolinomioF2(List.of(Fraction.getFraction(0, 1)));
    }
    
    public static PolinomioF2 one(){
        return new PolinomioF2(List.of(Fraction.getFraction(1, 1)));
    }

	@Override
	public Integer grado() {
		return this.coeficientes.size() - 1;
	}

	@Override
	public Fraction coeficiente(Integer i) {
    	if(i<this.coeficientes.size())
        	return this.coeficientes.get(i);
        else 
        	return Fraction.ZERO;
    }

	@Override
	public List<Fraction> coeficientes() {
		return  this.coeficientes;
	}

	@Override
	public Boolean constainsCoeficienteZero() {
		return  this.coeficientes.contains(Fraction.getFraction(0, 1));
	}

	@Override
	public Fraction value(Fraction v) {
		Fraction p = Fraction.getFraction(1, 1);
        Fraction s = this.coeficiente(0);
        for(int i=1; i< this.grado()+1;i++) {
            p = p.multiplyBy(v);
            s = s.add(p.multiplyBy(this.coeficiente(i))); 
        }
        return s;
	}

	@Override
	public PolinomioF2 add(Polinomio<Fraction> other) {
		List<Fraction> c = new ArrayList<>();
		Integer n = Math.max(this.coeficientes.size(),other.coeficientes().size());
		for(int i=0;i<n;i++) {
			c.add(this.coeficiente(i).add(other.coeficiente(i)));
		}
		return PolinomioF2.of(c);
	}

	@Override
	public PolinomioF2 subtract(Polinomio<Fraction> other) {
		List<Fraction> c = new ArrayList<>();
		Integer n = Math.max(this.coeficientes.size(),other.coeficientes().size());
		for(int i=0;i<n;i++) {
			c.add(this.coeficiente(i).subtract(other.coeficiente(i)));
		}
		return PolinomioF2.of(c);
	}

	@Override
	public Polinomio<Fraction> multiply(Fraction other) {
		List<Fraction> c = new ArrayList<>();
		for(int i=0;i<this.coeficientes.size();i++) {
			c.add(this.coeficiente(i).multiplyBy(other));
		}
		return PolinomioF2.of(c);
	}

	@Override
	public PolinomioF2 multiply(Polinomio<Fraction> other) {
		Fraction zero = Fraction.getReducedFraction(0, 1);
		Integer n1 = this.coeficientes().size();
		Integer n2 = other.coeficientes().size();
		Integer n = this.grado()+other.grado()+1;
        List<Fraction> p = new ArrayList<>();
        for(int i=0;i < n;i++) {
        	p.add(zero);
        }
        for(int i=0; i < n1;i++) {
        	for(int j=0; j < n2;j++) {
        		p.set(i+j,p.get(i+j).add(this.coeficiente(i).multiplyBy(other.coeficiente(j))));
        	}
        }
        return PolinomioF2.of(p);
	}

	@Override
	public PolinomioF2 pow(Integer n) {
		assert n >=0 : String.format("El exponente no puede ser negativo y es %d",n);
        PolinomioF2 r = PolinomioF2.one();
        for(int i=0; i<n; i++) {
            r = r.multiply(this);
        }
        return r;
	}

	@Override
	public PolinomioF2 derivada() {
		List<Fraction> coef = new ArrayList<>();
		for(int i = 1;i<this.coeficientes.size();i++) {
			coef.add(this.coeficiente(i).multiplyBy(Fraction.getFraction(i, 1)));
		}
        return PolinomioF2.of(coef);
	}

	@Override
	public PolinomioF2 integral(Fraction v) {
		List<Fraction> coef = new ArrayList<>();
		coef.add(v);
		for(int i = 0;i<this.coeficientes.size();i++) {
			coef.add(this.coeficiente(i).multiplyBy(Fraction.getFraction(1,i+1)));
		}
        return PolinomioF2.of(coef);
	}

	
	public String pf(Integer i) {
		String s = (this.coeficiente(i).getDenominator() == 1)
				? String.format("%d", this.coeficiente(i).getNumerator())
				: String.format("%s", this.coeficiente(i));
		s = (i > 0) ? String.format("%s x^%d", s, i) : s;
		return s;
	}
	
	@Override
    public String toString() {
		Integer n = this.coeficientes.size();
		String s = pf(n-1);
		for(int i=n-2;i>=0;i--) {
			if(!this.coeficiente(i).equals(Fraction.ZERO))
				s = s+"+"+pf(i);
		}
		return s;
	}
	
	public static void main(String[] args) {
		PolinomioF2 p0 = PolinomioF2.of(Fraction.getFraction(1,1),Fraction.getFraction(1,1));
	    PolinomioF2 p1 = PolinomioF2.of(Fraction.getFraction(3,1),Fraction.getFraction(-4,1),Fraction.getFraction(0,1),Fraction.getReducedFraction(7,1));
	
	    System.out.println(String.format("p0 = %s",p0));
	    System.out.println(String.format("p1 = %s",p1));
	    System.out.println(String.format("Derivada de p1 = %s",p1.derivada()));
	    System.out.println(String.format("Integral de p1 = %s",p1.integral(Fraction.getFraction(0, 1))));
	    System.out.println(String.format("Valor de p0 en 1 = %s",p0.value(Fraction.getFraction(1,1))));

	    System.out.println(String.format("p0*p1 = %s",p0.multiply(p1)));
	    System.out.println(String.format("p0+p1 = %s",p0.add(p1)));
	    System.out.println(String.format("p0-p1 = %s",p0.subtract(p1)));
	    System.out.println(String.format("p0**2 = %s",p0.pow(2)));

	}

}
