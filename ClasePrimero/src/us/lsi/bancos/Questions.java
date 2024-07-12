package us.lsi.bancos;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import us.lsi.ejemplos_b1_tipos.Persona;

public class Questions {
	
//	Vencimiento de los prestamos de un cliente

	public static Set<LocalDate> vencimientoDePrestamosDeCliente(Banco banco,String dni) {
		return null;
	}

	
//	Persona con más prestamos


	public static Persona clienteConMasPrestamos(Banco banco) {
	    return null;
	}

//	Cantidad total de los crétditos gestionados por un empleado


	public static Double cantidadPrestamosPmpledado(Banco banco,String dni) {
		return null;
	}
	
	
//	Empleado más longevo

	public static Persona empleadoMasLongevo(Banco banco) {
	    return null;
	}

	
//	Interés mínimo, máximo y medio de los préstamos
	
	public static record Info(Double min, Double max, Double mean) {
		public String toString() {
			return String.format("(%.2f,%.2f,%.2f)",this.min(),this.max(),this.mean());
		}
	}

	public static  Info rangoDeIntereseDePrestamos(Banco banco) {		
		return null;
	}
	
//	Número de préstamos por mes y año
	
	public static record Info2(Integer mes, Integer año) {
		public String toString() {
			return String.format("(%d,%d)",this.mes(),this.año());
		}
	}
	
	public static Map<Info2,Integer> numPrestamosPorMesAño(Banco banco) {
		return null;
	}
}
