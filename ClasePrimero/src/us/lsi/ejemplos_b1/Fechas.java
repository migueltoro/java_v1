package us.lsi.ejemplos_b1;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Fechas {
	
	public static void ejemplos1() {
		LocalDateTime d1 = LocalDateTime.now();
		Period t = Period.of(2,5,3);
		LocalDateTime d2 = d1.plus(t);
		Duration d = Duration.ofMinutes(10);
		d2 = d2.plus(d);
		
		System.out.println(d1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println(d2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println(d1.getYear());
		System.out.println(d1.getMonth());
		System.out.println(d1.getMonthValue());
		System.out.println(d1.getSecond());
		System.out.println(d1.getDayOfWeek());
		System.out.println(Period.between(d1.toLocalDate(), d2.toLocalDate()).getYears());
		System.out.println(ChronoUnit.DAYS.between(d1.toLocalDate(), d2.toLocalDate()));
	}
	
	public static void ejemplos2() {
		LocalDateTime actual = LocalDateTime.now();
		LocalDateTime otro = LocalDateTime.of(2022,3,27,0,0);
		LocalDate d3 = actual.toLocalDate();
		LocalTime time = actual.toLocalTime();
		
		System.out.println(d3.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		System.out.println(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

		String dt1 = "2018-06-29 17:08:00"; 
		LocalDateTime ldt1 = LocalDateTime.parse(dt1,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
		String dt2 = "2018/06/29";
		LocalDate ldt2 = LocalDate.parse(dt2,DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		System.out.println(ldt1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println(ldt2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	
		System.out.println(otro.isAfter(actual));
	}
	

	public static void main(String[] args) {
		ejemplos1();
	}

}
