package us.lsi.bancos;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Cuentas {
	
	private static Cuentas gestorDeCuentas = null;
	private Set<Cuenta> cuentas;
    private Map<String,Cuenta> cuentasIban;
    
    private Cuentas(String file) {
        this.cuentas = File2.streamDeFichero(file,"UTF-8")
        		.map(ln->Cuenta.parse(ln))
        		.collect(Collectors.toSet());
        this.cuentasIban = this.cuentas.stream().collect(Collectors.toMap(c->c.iban(),c->c));
    }   
    
    public static Cuentas of() {
		return Cuentas.of("bancos/cuentas.txt");
	}
    
    
    public static Cuentas of(String file) {
        if(Cuentas.gestorDeCuentas == null)
            Cuentas.gestorDeCuentas = new Cuentas(file);
        return Cuentas.gestorDeCuentas;
    }
    
    public Set<Cuenta> todas() {
        return this.cuentas;
    }

    public Optional<Cuenta> cuentaIban(String iban) {
        return Optional.ofNullable(this.cuentasIban.getOrDefault(iban,null));
    }
    
   
    public Integer size() {
        return this.cuentas.size();
    }
    
    public Cuenta cuentaIndex(Integer index) {
        return this.cuentas.stream().toList().get(index);
    }
    
    public String  toString() {
    	return this.cuentas.stream().map(c->c.toString()).collect(Collectors.joining("\n\t","Cuentas\n\t",""));
    }
    
    public static void main(String[] args) {	
    	Cuentas cuentas = Cuentas.of();
        System.out.println(cuentas);
        System.out.println("______________");
        System.out.println(cuentas.cuentaIban("ES5267093500351659831393"));
    }

}
