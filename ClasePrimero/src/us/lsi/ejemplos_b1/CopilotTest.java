package us.lsi.ejemplos_b1;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CopilotTest {

    @Test
    public void calculatesDaysBetweenDatesInSameMonth() {
        LocalDateTime start = LocalDateTime.of(2022, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 3, 31, 0, 0);
        long result = Copilot.calculateDaysBetweenDates(start, end);
        assertEquals(30, result);
    }

    @Test
    public void calculatesDaysBetweenDatesInDifferentMonths() {
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 2, 1, 0, 0);
        long result = Copilot.calculateDaysBetweenDates(start, end);
        assertEquals(31, result);
    }

    @Test
    public void calculatesDaysBetweenDatesInDifferentYears() {
        LocalDateTime start = LocalDateTime.of(2021, 12, 31, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 0, 0);
        long result = Copilot.calculateDaysBetweenDates(start, end);
        assertEquals(1, result);
    }

    @Test
    public void calculatesDaysBetweenSameDates() {
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 0, 0);
        long result = Copilot.calculateDaysBetweenDates(start, end);
        assertEquals(0, result);
    }
}
