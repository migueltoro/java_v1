package us.lsi.fecha;


import java.util.List;

import us.lsi.tools.Preconditions;

import java.util.ArrayList;

public record FechaI(Integer año, Integer mes, Integer dia,Integer numDiaDesde1900) implements Fecha {
	
	private static Integer desde = 1900;
	private static Integer hasta = 2100;
	
	public static FechaI of(Integer año, Integer mes, Integer dia) {
		Preconditions.checkArgument(año>=FechaI.desde && año < FechaI.hasta, String.format("Años limitados entre %d y %d",1900, 2100));
		Integer da = FechaI.diasAcumuladosEnAños.get(año-1900);
		Integer dm = FechaI.diasAcumuladosEnMeses(año).get(mes);
		Integer diasDesde1900 = da+dm+dia;
		return new FechaI(año, mes, dia,diasDesde1900);
	}
	
	public static FechaI of(Integer numDiaDesde1900) {
		Integer na = FechaI.index(FechaI.diasAcumuladosEnAños, numDiaDesde1900);
		Integer año = 1900+na;
		Integer rd = numDiaDesde1900-FechaI.diasAcumuladosEnAños.get(na);
		Integer mes = FechaI.index(FechaI.diasAcumuladosEnMeses(año),rd);
		Integer dia = rd-FechaI.diasAcumuladosEnMeses(año).get(mes);
		return new FechaI(año, mes, dia,numDiaDesde1900);
	}
	
	public static FechaI parse(String text) {
		String[] p = text.split("/");
		return FechaI.of(Integer.parseInt(p[0]),Integer.parseInt(p[1]),Integer.parseInt(p[2]));
	}
	
	private static List<String> meses = 
			List.of("","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre",
					"Octubre","Noviembre","Diciembre");
	
	private static List<String> dias = List.of("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo");
	
	private static List<Integer> diasAcumuladosEnMeses = List.of(0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365);
	private static List<Integer> diasAcumuladosEnMesesEnBisiesto = List.of(0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366);

	
	public static List<Integer> diasAcumuladosEnAños = List.of(0, 365, 730, 1095, 1460, 1826, 2191, 2556, 
				2921, 3287, 3652, 4017, 4382, 4748, 5113, 5478, 5843, 6209, 6574, 6939, 7304, 7670, 8035, 
				8400, 8765, 9131, 9496, 9861, 10226, 10592, 10957, 11322, 11687, 12053, 12418, 12783, 13148, 
				13514, 13879, 14244, 14609, 14975, 
				15340, 15705, 16070, 16436, 16801, 17166, 17531, 17897, 18262, 18627, 18992, 19358, 19723, 
				20088, 20453, 20819, 21184, 21549, 21914, 22280, 22645, 23010, 23375, 23741, 24106, 24471, 
				24836, 25202, 25567, 25932, 26297, 26663, 27028, 27393, 27758, 28124, 28489, 28854, 29219, 
				29585, 29950, 30315, 30680, 31046, 31411, 31776, 32141, 32507, 32872, 33237, 33602, 33968, 
				34333, 34698, 35063, 35429, 35794, 36159, 36524, 36890, 37255, 37620, 37985, 38351, 38716, 
				39081, 39446, 39812, 40177, 40542, 40907, 41273, 41638, 42003, 42368, 42734, 43099, 43464, 
				43829, 44195, 44560, 44925, 45290, 45656, 46021, 46386, 46751, 47117, 47482, 47847, 48212, 
				48578, 48943, 49308, 49673, 50039, 50404, 50769, 51134, 51500, 51865, 52230, 52595, 52961, 
				53326, 53691, 54056, 54422, 54787, 55152, 55517, 55883, 56248, 56613, 56978, 57344, 57709, 
				58074, 58439, 58805, 59170, 59535, 59900, 60266, 60631, 60996, 61361, 61727, 62092, 62457, 
				62822, 63188, 63553, 63918, 64283, 64649, 65014, 65379, 65744, 66110, 66475, 66840, 67205, 
				67571, 67936, 68301, 68666, 69032, 69397, 69762, 70127, 70493, 70858, 71223, 71588, 71954, 
				72319, 72684, 73049);
	
	public static List<Integer> diasAcumuladosEnAños(Integer hasta) {
		List<Integer> ls = new ArrayList<>();
		Integer a = 0;
		ls.add(a);
		for(int i=1900;i<2100;i++) {
			if(FechaI.esBisiesto(i)) {
				a = a + 366;
			} else {
				a = a + 365;
			}
			ls.add(a);
		}
		return ls;
	}
	
	public static Boolean esBisiesto(Integer anyo) {
		return (anyo%4==0 && anyo%100!=0) || (anyo%4==0 && anyo%400==0);
	}
	
	public static Integer index(List<Integer> ls, Integer value) {
		Integer n = ls.size();
		Integer i = 0;
		Integer j = n;
		Integer r = -1;
		while(j-i>1) {
			Integer k = (i+j)/2;
			if(value <= ls.get(k)) {
				j=k;
			} else {
				i=k;
			}			
		}
		if(value >= ls.get(i) && value < ls.get(j)) r = i;
		return r;
	}
	
	private static List<Integer> diasAcumuladosEnMeses(Integer año) {
		return FechaI.esBisiesto(año)?FechaI.diasAcumuladosEnMesesEnBisiesto:FechaI.diasAcumuladosEnMeses;
	}
	
	private Integer zeller() {
		Integer anyo = this.año();
		Integer mes = this.mes();
		Integer dia = this.dia();
		if (this.mes() < 3) {
			anyo = anyo -1;
			mes = mes +12;
		}
		Integer K = anyo%100;
		Integer J = anyo/100;
		Integer h = (dia + 13 * (mes + 1) / 5 + K + K / 4 + J / 4 + 5 * J) % 7;
		return h;
	}
	
	@Override
	public String nombreMes() {
		return FechaI.meses.get(this.mes());
	}
	
	@Override
	public String diaSemana() {
		Integer z = this.zeller();
		z = (z+5)%7;
		return FechaI.dias.get(z);
	}
	
	@Override
	public Boolean esAñoBisiesto() {
		return FechaI.esBisiesto(this.año());
	}

	@Override
	public Fecha sumarDias(Integer n) {
		return FechaI.of(this.numDiaDesde1900+n);
	}

	@Override
	public Fecha restarDias(Integer n) {
		return FechaI.of(this.numDiaDesde1900-n);
	}

	@Override
	public Integer diferenciaDias(Fecha f) {
		FechaI fi = (FechaI) f;
		return this.numDiaDesde1900()-fi.numDiaDesde1900();
	}

	
	public String toString() {
		return String.format("%4d/%02d/%02d",this.año(),this.mes(),this.dia());
	}

	@Override
	public int compareTo(Fecha f) {
		FechaI fi = (FechaI) f;
		return this.numDiaDesde1900.compareTo(fi.numDiaDesde1900);
	}
	
	
	
}
