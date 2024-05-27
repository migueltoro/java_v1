package us.lsi.bancos;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Empleados {
	
	private static Empleados gestorDeEmpleados = null;
	private Set<Empleado> empleados;
	private Map<String,Empleado> empleadosDni;
    
    private Empleados(Set<Empleado> empleados) {
		super();
		this.empleados = empleados;
		this.empleadosDni = this.empleados.stream().collect(Collectors.toMap(e->e.dni(),e->e));
	}
        
    
    public static Empleados of() {
        if(Empleados.gestorDeEmpleados == null)
            Empleados.gestorDeEmpleados = Empleados.parse("bancos/empleados.txt");
        return Empleados.gestorDeEmpleados;
    }          
    
    public static Empleados parse(String fichero) {
    	Set<Empleado> empleados = File2.streamDeFichero(fichero,"UTF-8")
    			.map(ln->Empleado.parse(ln))
    			.collect(Collectors.toSet());
        Empleados.gestorDeEmpleados = new Empleados(empleados);
        return Empleados.gestorDeEmpleados;
    }
    
   
    public Set<Empleado> todos() {
        return this.empleados;
    }

    public Optional<Empleado> empleadoDni(String dni) {
        return Optional.ofNullable(this.empleadosDni.getOrDefault(dni,null));
    }
    
    public Integer size() {
        return this.empleados.size();
    }
    
    public Empleado empleadoIndex(Integer index) {
		return this.empleados.stream().toList().get(index);
	}

	public String toString() {
		return this.empleados.stream()
				.map(p -> p.toString())
				.collect(Collectors.joining("\n\t", "Empleados\n\t", ""));
	}
    
	public static void main(String[] args) {
		Empleados empleados = Empleados.parse("bancos/empleados.txt");
		System.out.println(empleados);
	    System.out.println(empleados.empleadoDni("52184462S").get());
	    System.out.println(Personas.of().personaDni("52184462S").get());
	}
}
