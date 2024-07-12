package us.lsi.fecha;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FechaTest {

    @Test
    void shouldReturnCorrectDayOfWeek() {
        FechaI fecha = FechaI.of(2022, 2, 28);
        assertEquals("Lunes", fecha.diaSemana());
    }

    @Test
    void shouldReturnCorrectMonthName() {
        FechaI fecha = FechaI.of(2022, 2, 28);
        assertEquals("Febrero", fecha.nombreMes());
    }

    @Test
    void shouldReturnTrueForLeapYear() {
        FechaI fecha = FechaI.of(2020, 2, 28);
        assertTrue(fecha.esAñoBisiesto());
    }

    @Test
    void shouldReturnFalseForNonLeapYear() {
        FechaI fecha = FechaI.of(2021, 2, 28);
        assertFalse(fecha.esAñoBisiesto());
    }

    @Test
    void shouldAddDaysCorrectly() {
        FechaI fecha = FechaI.of(2022, 2, 28);
        FechaI expected = FechaI.of(2022, 3, 1);
        assertEquals(expected, fecha.sumarDias(1));
    }

    @Test
    void shouldSubtractDaysCorrectly() {
        FechaI fecha = FechaI.of(2022, 3, 1);
        FechaI expected = FechaI.of(2022, 2, 28);
        assertEquals(expected, fecha.restarDias(1));
    }
    
    @Test
    void shouldSubtractDaysCorrectly2() {
        FechaI fecha = FechaI.of(2024, 3, 1);
        FechaI expected = FechaI.of(2024, 2, 29);
        assertEquals(expected, fecha.restarDias(1));
    }
    
    @Test
    void shouldSubtractDaysCorrectly3() {
        FechaI fecha = FechaI.of(2025, 1, 1);
        FechaI expected = FechaI.of(2024, 12, 31);
        assertEquals(expected, fecha.restarDias(1));
    }

    @Test
    void shouldCalculateDifferenceInDaysCorrectly() {
        FechaI fecha1 = FechaI.of(2022, 2, 28);
        FechaI fecha2 = FechaI.of(2022, 3, 1);
        assertEquals(1, fecha2.diferenciaDias(fecha1));
    }

    @Test
    void shouldParseDateCorrectly() {
        FechaI expected = FechaI.of(2022, 2, 28);
        assertEquals(expected, FechaI.parse("2022/02/28"));
    }
}


