package us.lsi.ejemplos_b1;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Copilot {
	
	/**
	 * Calculates the number of days between two LocalDateTime objects.
	 *
	 * @param begin The start date as a LocalDateTime object.
	 * @param end The end date as a LocalDateTime object.
	 * @return The number of days between the start and end date.
	 */
	public static Long calculateDaysBetweenDates(LocalDateTime begin, LocalDateTime end) {
		return ChronoUnit.DAYS.between(begin.toLocalDate(), end.toLocalDate());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
