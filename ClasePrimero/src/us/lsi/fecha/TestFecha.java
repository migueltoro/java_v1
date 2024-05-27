package us.lsi.fecha;

public class TestFecha {

	public static void main(String[] args) {
		Integer n = 143;
		Fecha f = Fecha.parse("2024/03/05");
		System.out.println(f);
		System.out.println(f.diaSemana());
		Fecha f2 = f.sumarDias(n);
		System.out.println(f2);
		System.out.println(f2.diaSemana());
		Fecha f3 = f2.restarDias(n);
		System.out.println(f3);
		System.out.println(f2.diferenciaDias(f));
//		System.out.println(f.diaSemana());
//		System.out.println(FechaI.esBisiesto(2004));
//		System.out.println(FechaI.diasAcumuladosPorAños(2100));
	}
}
