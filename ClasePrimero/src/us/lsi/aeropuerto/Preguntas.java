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
	
	//1. Dada una cadena de caracteres s devuelve el número total de pasajeros a
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
	// total de pasajeros a ese destino en el año anyo

	public SortedMap<String, Integer> totalPasajerosADestino(Integer a);
	
	//5. Dado un destino devuelve el código de la aerolinea del primer vuelo con plazas libres a ese
	// destino

	public String primerVuelo(String destino);
	
	//6. Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
	// destino la media de los precios de los vuelos a ese destino.
	
	public Map<String, Double> precioMedio();
	
	//7. Devuelve un Map tal que dado un entero n haga corresponder
	// a cada fecha la lista de los n destinos con los vuelos de mayor duración.
	
	public Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n);
	
	//8. Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
	// a f. Si no hubiera vuelos devuelve 0.0

	public Double precioMedio(LocalDateTime f);
	
	//9. Devuelve un Map que haga corresponder a cada destino un conjunto con las
	// fechas de los vuelos a ese destino.

	public Map<String, Set<LocalDate>> fechasADestino();
	
	//10. Devuelve el destino con mayor número de vuelos
	
	public String destinoConMasVuelos();
	
	//11. Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duración es mayor que m minutos.
	
	public SortedSet<Duration> duraciones(Integer m);
	
	//12. Dado un número n devuelve un conjunto con los destinos de los vuelos que están entre los n que más duración tienen.
	
	public Set<String> destinosMayorDuracion(Integer n);
	
	//13. Dado un número n devuelve un conjunto con los n destinos con más vuelos
	
	public Set<String> entreLosMasVuelos(Integer n);
	
	// 14. Dado un número entero n devuelve una lista con los destinos que tienen más de n vuelos
	
	public List<String> masDeNVuelos(Integer n);
	
	// 15. Devuelve un Map que relación cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	public Map<String,Double>  porcentajeADestino();
	public Map<String,Double>  porcentajeADestinoOcupacionesVuelos();
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de más barato
	
	public Map<String,Vuelo> masBarato();
	
	// 17. Devuelve un Map que haga corresponder a cada destino el número de fechas
	// distintas en las que hay vuelos a ese destino.

	public Map<String, Integer> fechasDistintas();
}