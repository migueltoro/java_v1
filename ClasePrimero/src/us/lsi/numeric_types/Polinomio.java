package us.lsi.numeric_types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.fraction.Fraction;

import us.lsi.tools.Preconditions;

public record Polinomio<S extends FieldElement<S>>(List<S> coeficientes, Field<S> field) {
      
    public static <S extends FieldElement<S>> Polinomio<S> of(List<S> coeficientes){
        return new Polinomio<>(coeficientes,coeficientes.get(0).getField());
    }

    public static <S extends FieldElement<S>> Polinomio<S> of(@SuppressWarnings("unchecked") S... coeficientes) {
        List<S> coef = Arrays.asList(coeficientes);
        return new Polinomio<>(coef,coeficientes[0].getField());
    }
    
   
    public static <S extends FieldElement<S>> Polinomio<S> zero(Field<S> field){
        return new Polinomio<>(List.of(field.getZero()),field);
    }
    
    public static <S extends FieldElement<S>> Polinomio<S> one(Field<S> field){
        return new Polinomio<>(List.of(field.getOne()),field);
    }
    
    public Integer grado() {
        return this.coeficientes.size() - 1;
    }
    
    public S coeficiente(Integer i) {
        Preconditions.checkPositionIndex(i,this.grado());
        return this.coeficientes.get(i);
    }
    
    public Boolean constainsCoeficienteZero() {
        return  this.coeficientes.contains(this.field().getZero());
    }
    
    public S value(S v) {
        S p = this.field().getOne();
        S s = this.coeficiente(0);
        for(int i=1; i< this.grado()+1;i++) {
            p = p.multiply(v);
            s = s.add(p.multiply(this.coeficiente(i))); 
        }
        return s;
    }
    
    public Polinomio<S> add(Polinomio<S> other) {
        int n = Math.max(this.grado(),other.grado());
        List<S> p1 = new ArrayList<>(this.coeficientes());
        p1.addAll(IntStream.range(0,n-this.grado()).boxed().map(i->this.field().getZero()).toList());
        List<S> p2 = new ArrayList<>(other.coeficientes());
        p2.addAll(IntStream.range(0,n-other.grado()).boxed().map(i->this.field().getZero()).toList());
        List<S> p = IntStream.range(0,n+1).boxed().map(i->p1.get(i).add(p2.get(i))).toList();
        return Polinomio.of(p);
    }
        
    public Polinomio<S> subtract(Polinomio<S> other) {
        int n = Math.max(this.grado(),other.grado());
        List<S> p1 = new ArrayList<>(this.coeficientes());
        p1.addAll(IntStream.range(0,n-this.grado()).boxed().map(i->this.field().getZero()).toList());
        List<S> p2 = new ArrayList<>(other.coeficientes());
        p2.addAll(IntStream.range(0,n-other.grado()).boxed().map(i->this.field().getZero()).toList());
        List<S> p = IntStream.range(0,n+1).boxed().map(i->p1.get(i).subtract(p2.get(i))).toList();
        return Polinomio.of(p);
    }
    
    public Polinomio<S> multiply(S other) {
            List<S> coef = IntStream.range(0,this.grado()+1).boxed().map(i->other.multiply(this.coeficiente(i))).toList();
            return Polinomio.of(coef);
    }
    
	public Polinomio<S> multiply(Polinomio<S> other) {
		int n = this.grado()+other.grado();
        List<S> p = IntStream.range(0,n+1).boxed()
        		.map(i->this.field().getZero())
        		.collect(Collectors.toList());
        for(int i=0; i < this.grado()+1;i++) {
        	for(int j=0; j < this.grado()+1;j++) {
        		p.set(i+j,p.get(i+j).add(this.coeficiente(i).multiply(other.coeficiente(j))));
        	}
        }
        return Polinomio.of(p);
	}
        
	public Polinomio<S> pow(Integer n){
        Preconditions.checkArgument(n >=0,String.format("El exponente no puede ser negativo y es %d",n));
        Polinomio<S> r = Polinomio.one(this.field());
        for(int i=0; i<n; i++) {
            r = r.multiply(this);
        }
        return r;
	}
    
	public Polinomio<S> derivada() {
        List<S> coef = IntStream.range(1, this.grado()+1).boxed()
        		.map(i->this.coeficiente(i).multiply(i)).toList();
        return Polinomio.of(coef);
	}
    
	public Polinomio<S> integral(S v) {
		List<S> coef = IntStream.range(1, this.grado()+1).boxed()
				.map(i->this.coeficiente(i).multiply(1/(i+1))).collect(Collectors.toList());
		coef.add(0,v);
        return Polinomio.of(coef);
	}
	
	@Override
    public String toString() {
		Integer n = this.grado();
		return IntStream.range(0,n+1).map(i->n-i).boxed()
				.filter(i->!this.coeficiente(i).equals(this.field().getZero()))
				.map(i->this.coeficiente(i).toString()+(i>0?" x^"+i:""))
				.collect(Collectors.joining(" + "));
	}
	
	public static void main(String[] args) {
		Polinomio<Fraction> p0 = Polinomio.of(Fraction.getReducedFraction(1,1),Fraction.getReducedFraction(1,1));
	    Polinomio<Fraction> p1 = Polinomio.of(Fraction.getReducedFraction(3,1),Fraction.getReducedFraction(-4,1),Fraction.getReducedFraction(0,1),Fraction.getReducedFraction(7,1));
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
		
