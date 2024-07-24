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
    
    private Empleados(String file) {
		super();
		this.empleados = File2.streamDeFichero(file,"UTF-8")
    			.map(ln->Empleado.parse(ln))
    			.collect(Collectors.toSet());;
		this.empleadosDni = this.empleados.stream().collect(Collectors.toMap(e->e.dni(),e->e));
	}
    
    public static Empleados of() {
    	return Empleados.of("bancos/empleados.txt");
    }
    
    public static Empleados of(String file) {
        if(Empleados.gestorDeEmpleados == null)
            Empleados.gestorDeEmpleados = new Empleados(file);
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
		Empleados empleados = Empleados.of();
		System.out.println(empleados);
	    System.out.println(empleados.empleadoDni("52184462S").get());
	    System.out.println(Personas.of().personaDni("52184462S").get());
	}
}
