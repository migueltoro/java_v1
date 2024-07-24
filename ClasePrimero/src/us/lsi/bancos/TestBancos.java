package us.lsi.bancos;

import java.util.Locale;

public class TestBancos {

	public static void main(String[] args) {
			Locale.setDefault(Locale.of("en", "US"));
			Banco banco = Banco.of();
		    System.out.println(banco.empleados().empleadoDni("52184462S"));
//		    System.out.println(Questions.vencimientoDePrestamosDeCliente(banco,"35529655Z"));
//		    System.out.println(Questions.clienteConMasPrestamos(banco));
//		    System.out.println(Questions.empleadoMasLongevo(banco));
//		    System.out.println(Questions.rangoDeIntereseDePrestamos(banco));
//		    System.out.println(Questions.numPrestamosPorMesAño(banco).entrySet()
//		    		.stream().map(e->e.toString()).collect(Collectors.joining("\n")));
		    System.out.println(banco.prestamosDeEmpleado("123"));
		    System.out.println(banco.prestamosDeCliente("123"));
		    System.out.println(banco.empleadoMasJoven());
	}

}
