package us.lsi.aeropuerto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public interface Preguntas {
	
	public static Preguntas funcional() {
		return new PreguntasFuncional();
	}

	
	public static Preguntas imperativo() {
		return new PreguntasImperativo();
	}
	
	//1. Dada una cadena de caracteres s devuelve el n�mero total de pasajeros a
	// ciudades destino que tienen
	// como prefijo s (esto es, comienzan por s).		
	public Integer numeroDepasajeros(String prefix);
	
	//2.  Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
	// existe un vuelo en la fecha f con destino en s.

	public Boolean hayDestino(Set<String> destinos, LocalDate f);
	

	//3. Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
	// los vuelos de fecha f

	public Set<String> destinosDiferentes(LocalDate f) ;
	
	//4. Dado un anyo devuelve un SortedMap que relacione cada destino con el
	// total de pasajeros a ese destino en el a�o anyo

	public SortedMap<String, Integer> totalPasajerosADestino(Integer a);
	
	//5. Dado un destino devuelve el c�digo de la aerolinea del primer vuelo con plazas libres a ese
	// destino

	public String primerVuelo(String destino);
	
	//6. Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
	// destino la media de los precios de los vuelos a ese destino.
	
	public Map<String, Double> precioMedio(Integer n);
	
	//7. Devuelve un Map tal que dado un entero n haga corresponder
	// a cada fecha la lista de los n destinos con los vuelos de mayor duraci�n.
	
	public Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n);
	
	//8. Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
	// a f. Si no hubiera vuelos devuelve 0.0

	public Double precioMedio(LocalDateTime f);
	
	//9. Devuelve un Map que haga corresponder a cada destino un conjunto con las
	// fechas de los vuelos a ese destino.

	public Map<String, Set<LocalDate>> fechasADestino();
	
	//10. Devuelve el destino con mayor n�mero de vuelos
	
	public String destinoConMasVuelos();
	
	//11. Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duraci�n es mayor que m minutos.
	
	public SortedSet<Duration> duraciones(Integer m);
	
	//12. Dado un n�mero n devuelve un conjunto con los destinos de los vuelos que est�n entre los n que m�s duraci�n tienen.
	
	public Set<String> destinosMayorDuracion(Integer n);
	
	//13. Dado un n�mero n devuelve un conjunto con los n destinos con m�s vuelos
	
	public Set<String> entreLosMasVuelos(Integer n);
	
	// 14. Dado un n�mero entero n devuelve una lista con los destinos que tienen m�s de n vuelos
	
	public List<String> masDeNVuelos(Integer n);
	
	// 15. Devuelve un Map que relaci�n cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	public Map<String,Double>  porcentajeADestino();
	public Map<String,Double>  porcentajeADestinoOcupacionesVuelos();
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de m�s barato
	
	public Map<String,VueloProgramado> masBarato();
	
	// 17. Devuelve un Map que haga corresponder a cada destino el n�mero de fechas
	// distintas en las que hay vuelos a ese destino.

	public Map<String, Integer> fechasDistintas();
	
	// 18. Calcula el número de vuelos atrasados por año
	
	public Map<Integer, Integer> vuelosAtrasados();
}