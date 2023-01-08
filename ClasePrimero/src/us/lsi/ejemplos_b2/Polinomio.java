package us.lsi.ejemplos_b2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.math3.fraction.Fraction;

import us.lsi.tools.Preconditions;

public record Polinomio(List<Fraction> coeficientes) {
      
    public static  Polinomio of(List<Fraction> coeficientes){
        return new Polinomio(coeficientes);
    }

    public static Polinomio of(Fraction... coeficientes) {
        List<Fraction> coef = Arrays.asList(coeficientes);
        return new Polinomio(coef);
    }
    
   
    public static Polinomio zero(){
        return new Polinomio(List.of(Fraction.getReducedFraction(0, 1)));
    }
    
    public static Polinomio one(){
        return new Polinomio(List.of(Fraction.getReducedFraction(1, 1)));
    }
    
    public Integer grado() {
        return this.coeficientes.size() - 1;
    }
    
    public Fraction coeficiente(Integer i) {
        Preconditions.checkPositionIndex(i,this.grado());
        return this.coeficientes.get(i);
    }
    
    public Boolean constainsCoeficienteZero() {
        return  this.coeficientes.contains(Fraction.getReducedFraction(0, 0));
    }
    
    public Fraction value(Fraction v) {
    	Fraction p = Fraction.getReducedFraction(1, 1);
        Fraction s = this.coeficiente(0);
        for(int i=1; i< this.grado()+1;i++) {
            p = p.multiply(v);
            s = s.add(p.multiply(this.coeficiente(i))); 
        }
        return s;
    }
    
    public Polinomio add(Polinomio other) {
    	Fraction zero = Fraction.getReducedFraction(0, 1);
        int n = Math.max(this.grado(),other.grado());
        List<Fraction> p1 = new ArrayList<>(this.coeficientes());
        p1.addAll(IntStream.range(0,n-this.grado()).boxed().map(i->zero).toList());
        List<Fraction> p2 = new ArrayList<>(other.coeficientes());
        p2.addAll(IntStream.range(0,n-other.grado()).boxed().map(i->zero).toList());
        List<Fraction> p = IntStream.range(0,n+1).boxed().map(i->p1.get(i).add(p2.get(i))).toList();
        return Polinomio.of(p);
    }
        
    public Polinomio subtract(Polinomio other) {
    	Fraction zero = Fraction.getReducedFraction(0, 1);
        int n = Math.max(this.grado(),other.grado());
        List<Fraction> p1 = new ArrayList<>(this.coeficientes());
        p1.addAll(IntStream.range(0,n-this.grado()).boxed().map(i->zero).toList());
        List<Fraction> p2 = new ArrayList<>(other.coeficientes());
        p2.addAll(IntStream.range(0,n-other.grado()).boxed().map(i->zero).toList());
        List<Fraction> p = IntStream.range(0,n+1).boxed().map(i->p1.get(i).subtract(p2.get(i))).toList();
        return Polinomio.of(p);
    }
    
    public Polinomio multiply(Fraction other) {
            List<Fraction> coef = IntStream.range(0,this.grado()+1).boxed()
            		.map(i->other.multiply(this.coeficiente(i))).toList();
            return Polinomio.of(coef);
    }
    
	public Polinomio multiply(Polinomio other) {
		Fraction zero = Fraction.getReducedFraction(0, 1);
		int n = this.grado()+other.grado();
        List<Fraction> p = IntStream.range(0,n+1).boxed()
        		.map(i->zero)
        		.collect(Collectors.toList());
        for(int i=0; i < this.grado()+1;i++) {
        	for(int j=0; j < this.grado()+1;j++) {
        		p.set(i+j,p.get(i+j).add(this.coeficiente(i).multiply(other.coeficiente(j))));
        	}
        }
        return Polinomio.of(p);
	}
        
	public Polinomio pow(Integer n){
        Preconditions.checkArgument(n >=0,String.format("El exponente no puede ser negativo y es %d",n));
        Polinomio r = Polinomio.one();
        for(int i=0; i<n; i++) {
            r = r.multiply(this);
        }
        return r;
	}
    
	public Polinomio derivada() {
        List<Fraction> coef = IntStream.range(1, this.grado()+1).boxed()
        		.map(i->this.coeficiente(i).multiply(i)).toList();
        return Polinomio.of(coef);
	}
    
	public Polinomio integral(Fraction v) {
		List<Fraction> coef = IntStream.range(1, this.grado()+1).boxed()
				.map(i->this.coeficiente(i).multiply(1/(i+1))).collect(Collectors.toList());
		coef.add(0,v);
        return Polinomio.of(coef);
	}
	
	@Override
    public String toString() {
		Fraction zero = Fraction.getReducedFraction(0, 1);
		Integer n = this.grado();
		return IntStream.range(0,n+1).map(i->n-i).boxed()
				.filter(i->!this.coeficiente(i).equals(zero))
				.map(i->this.coeficiente(i).toString()+(i>0?" x^"+i:""))
				.collect(Collectors.joining(" + "));
	}
	
	public static void main(String[] args) {
		Polinomio p0 = Polinomio.of(Fraction.getReducedFraction(1,1),Fraction.getReducedFraction(1,1));
	    Polinomio p1 = Polinomio.of(Fraction.getReducedFraction(3,1),Fraction.getReducedFraction(-4,1),Fraction.getReducedFraction(0,1),Fraction.getReducedFraction(7,1));
	    System.out.println(String.format("p0 = %s",p0));
	    System.out.println(String.format("p1 = %s",p1));
	    System.out.println(String.format("Derivada de p1 = %s",p1.derivada()));
	    System.out.println(String.format("Integral de p1 = %s",p1.integral(Fraction.getReducedFraction(0, 1))));
	    System.out.println(String.format("Valor de p0 en 1 = %s",p0.value(Fraction.getReducedFraction(1,1))));

	    System.out.println(String.format("p0*p1 = %s",p0.multiply(p1)));
	    System.out.println(String.format("p0+p1 = %s",p0.add(p1)));
	    System.out.println(String.format("p0**2 = %s",p0.pow(2)));

	}
}
		
