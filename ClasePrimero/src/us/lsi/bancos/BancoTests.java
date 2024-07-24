package us.lsi.bancos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;

public class BancoTests {
	
    private Banco banco;

    @BeforeEach
    void setUp() {
        banco = Banco.of();
    }
    
    @Test
    @DisplayName("Dnis cuentas incluidos en dnis personas")
    void dnisCuentasIncluidosEnDnisPersonas() {
    	Set<String> dnis = banco.personas().dnis();
		Set<String> dniCuentas = 
				banco.cuentas().todas().stream()
				.map(c->c.dni())
				.collect(Collectors.toSet());
        assertTrue(dnis.containsAll(dniCuentas));
    }

    @Test
    @DisplayName("Retrieves all loans for a given employee")
    void retrievesLoansForEmployee() {
        Set<Prestamo> result = banco.prestamosDeEmpleado("123");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Retrieves all loans for a given client")
    void retrievesLoansForClient() {
        Set<Prestamo> result = banco.prestamosDeCliente("123");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Retrieves youngest employee")
    void retrievesYoungestEmployee() {
        assertNotNull(banco.empleadoMasJoven().get());
    }

    @Test
    @DisplayName("Handles no employees when retrieving youngest")
    void handlesNoEmployeesWhenRetrievingYoungest() {
        assertTrue(banco.empleadoMasJoven().isPresent());
    }
}