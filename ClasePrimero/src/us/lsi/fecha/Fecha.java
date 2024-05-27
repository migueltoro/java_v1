package us.lsi.fecha;

public interface Fecha extends Comparable<Fecha> {
	
	public static Fecha	of(Integer año, Integer mes, Integer dia) {
		return FechaI.of(año,mes,dia);
	}
	
	public static Fecha	parse(String text) {
		return FechaI.parse(text);
	}
	
	Integer año();
	
	Integer mes();
	
	Integer dia();

	String nombreMes();

	String diaSemana();
	
	Boolean esAñoBisiesto();
	
	Fecha sumarDias(Integer n);
	
	Fecha restarDias(Integer n);
	
	Integer diferenciaDias(Fecha f);

}