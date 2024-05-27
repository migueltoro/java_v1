package us.lsi.bancos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.ejemplos_b1_tipos.Persona;
import us.lsi.tools.Preconditions;
import us.lsi.tools.Stream2;

public class Questions {
	
//	Vencimiento de los prestamos de un cliente

	public static Set<LocalDate> vencimientoDePrestamosDeCliente(Banco banco,String dni) {
		return banco.prestamos().todos().stream()
				.filter(p->p.dniCliente().equals(dni))
				.map(p->p.fechaVencimiento())
				.collect(Collectors.toSet());
	}

	
//	Persona con más prestamos


	public static Persona clienteConMasPrestamos(Banco banco) {
	    Map<String, Integer> c = Stream2.groupingSize(banco.prestamos().todos().stream(),p->p.dniCliente());
	    String dni = c.keySet().stream().max(Comparator.comparing(k->c.get(k))).get();	
	    System.out.println(dni);
	    Optional<Persona> p = banco.personas().personaDni(dni);
	    Preconditions.checkArgument(p.isPresent(), String.format("La persona con dni %s no existe",dni));
	    return p.get();
	}

//	Cantidad total de los crétditos gestionados por un empleado


	public static Double cantidadPrestamosPmpledado(Banco banco,String dni) {
		return banco.prestamos().todos().stream().mapToDouble(p->p.cantidad()).sum();
	}
	
	
//	Empleado más longevo

	public static Persona empleadoMasLongevo(Banco banco) {
		Empleado emp = banco.empleados().todos().stream()
				.max(Comparator.comparing(e->e.mesesContratado()))
				.get();
	    return emp.persona();
	}

	
//	Interés mínimo, máximo y medio de los préstamos
	
	public static record Info(Double min, Double max, Double mean) {
		public String toString() {
			return String.format("(%.2f,%.2f,%.2f)",this.min(),this.max(),this.mean());
		}
	}

	public static  Info rangoDeIntereseDePrestamos(Banco banco) {
		Double min = banco.prestamos().todos().stream().mapToDouble(p->p.interes()).min().getAsDouble();
		Double max = banco.prestamos().todos().stream().mapToDouble(p->p.interes()).max().getAsDouble();
		Double mean = banco.prestamos().todos().stream().mapToDouble(p->p.interes()).average().getAsDouble();
		return new Info(min,max,mean);
	}
	
//	Número de préstamos por mes y año
	
	public static record Info2(Integer mes, Integer año) {
		public String toString() {
			return String.format("(%d,%d)",this.mes(),this.año());
		}
	}
	
	public static Map<Info2,Integer> numPrestamosPorMesAño(Banco banco) {
		return Stream2.groupingSize(banco.prestamos().todos().stream(),
				p->new Info2(p.fechaComienzo().getMonthValue(),p.fechaComienzo().getYear()));
	}
}
