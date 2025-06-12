package us.lsi.ejemplos_b2_tipos;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.Fraction;
import us.lsi.tools.Stream2;

public record PolinomioF1(List<Fraction> coeficientes) implements Polinomio<Fraction> {
      
    public static  PolinomioF1 of(List<Fraction> coeficientes){
        return new PolinomioF1(coeficientes);
    }

    public static PolinomioF1 of(Fraction... coeficientes) {
        List<Fraction> coef = Arrays.asList(coeficientes);
        return new PolinomioF1(coef);
    }
      
    public static PolinomioF1 zero(){
        return new PolinomioF1(List.of(Fraction.getReducedFraction(0, 1)));
    }
    
    public static PolinomioF1 one(){
        return new PolinomioF1(List.of(Fraction.getReducedFraction(1, 1)));
    }
    
    @Override
	public Integer grado() {
        return this.coeficientes().size() - 1;
    }
    
    @Override
	public Fraction coeficiente(Integer i) {
    	if(i<this.coeficientes().size())
        	return this.coeficientes().get(i);
        else 
        	return Fraction.ZERO;
    }
    
    @Override
	public Boolean constainsCoeficienteZero() {
        return  this.coeficientes().contains(Fraction.getReducedFraction(0, 0));
    }
    
    @Override
	public Fraction value(Fraction v) {
    	Integer n = this.coeficientes().size();
    	Fraction p = Fraction.getFraction(1, 1);
        Fraction s = this.coeficiente(0);
        for(int i=1; i< n;i++) {
            p = p.multiplyBy(v);
            s = s.add(p.multiplyBy(this.coeficiente(i))); 
        }
        return s;
    }
    
    @Override
	public PolinomioF1 add(Polinomio<Fraction> other) {
    	int n = Math.max(this.coeficientes().size(),other.coeficientes().size());
    	List<Fraction> c = IntStream.range(0,n).boxed()
    			.map(i->this.coeficiente(i).add(other.coeficiente(i)))
    			.toList();
    	return PolinomioF1.of(c);
    }
        
    @Override
	public PolinomioF1 subtract(Polinomio<Fraction> other) {
    	int n = Math.max(this.coeficientes().size(),other.coeficientes().size());
    	List<Fraction> c = IntStream.range(0,n).boxed()
    			.map(i->this.coeficiente(i).subtract(other.coeficiente(i)))
    			.toList();
    	return PolinomioF1.of(c);
    }
    
    @Override
	public PolinomioF1 multiply(Fraction other) {
            List<Fraction> coef = IntStream.range(0,this.grado()+1).boxed()
            		.map(i->other.multiplyBy(this.coeficiente(i))).toList();
            return PolinomioF1.of(coef);
    }
    
	@Override
	public PolinomioF1 multiply(Polinomio<Fraction> other) {
		Fraction zero = Fraction.getFraction(0, 1);
		Integer n1 = this.coeficientes().size();
		Integer n2 = other.coeficientes().size();
		Integer n = this.grado()+other.grado()+1;
        List<Fraction> coef = IntStream.range(0, n).boxed().map(i->zero).collect(Collectors.toList());
        BiFunction<Integer,Integer,Fraction> f = (i,j) ->
        		this.coeficiente(i).multiplyBy(other.coeficiente(j));
        BiConsumer<Integer,Integer> bc = (i,j)-> coef.set(i+j,coef.get(i+j).add(f.apply(i, j)));
        Stream2.allPairs(n1,n2).forEach(p->bc.accept(p.first(),p.second()));
        return PolinomioF1.of(coef);
	}
        
	@Override
	public PolinomioF1 pow(Integer n){
		assert n >=0:String.format("El exponente no puede ser negativo y es %d",n);
        Stream<PolinomioF1> r = IntStream.range(0,n).boxed().map(i->this);
        return r.collect(Collectors.reducing(PolinomioF1.one(),(x,y)->x.multiply(y)));
	}
    
	@Override
	public PolinomioF1 derivada() {
        List<Fraction> coef = IntStream.range(1, this.grado()+1).boxed()
        		.map(i->this.coeficiente(i).multiplyBy(Fraction.getFraction(i, 1))).toList();
        return PolinomioF1.of(coef);
	}
    
	@Override
	public PolinomioF1 integral(Fraction v) {
		List<Fraction> coef = IntStream.range(1, this.grado()+1).boxed()
				.map(i->this.coeficiente(i).multiplyBy(Fraction.getFraction(1,i+1))).collect(Collectors.toList());
		coef.add(0,v);
        return PolinomioF1.of(coef);
	}
	
	@Override
	public String toString() {
		BiFunction<Fraction, Integer, String> pf = (f, i) -> {
			String s = (this.coeficiente(i).getDenominator() == 1)
					? String.format("%d", this.coeficiente(i).getNumerator())
					: String.format("%s", this.coeficiente(i));
			s = (i > 0) ? String.format("%s x^%d", s, i) : s;
			return s;
		};

		Integer n = this.coeficientes.size();
		return IntStream.range(0, n).boxed()
				.map(i->n-i-1)
				.filter(i->!this.coeficiente(i).equals(Fraction.ZERO))
				.map(i -> pf.apply(this.coeficiente(i), i))
				.collect(Collectors.joining(" + "));
	}

	public static void main(String[] args) {
		PolinomioF1 p0 = PolinomioF1.of(Fraction.getFraction(1,1),Fraction.getFraction(1,1));
	    PolinomioF1 p1 = PolinomioF1.of(Fraction.getFraction(3,1),Fraction.getFraction(-4,1),
	    		Fraction.getFraction(0,1),Fraction.getFraction(7,1));
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
		
