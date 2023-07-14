package us.lsi.aeropuerto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

public class PreguntasImperativo implements Preguntas {

	PreguntasImperativo() {
	}
	
	//1. Dada una cadena de caracteres s devuelve el n�mero total de pasajeros a
	// ciudades destino que tienen
	// como prefijo s (esto es, comienzan por s).
	
	public Integer numeroDepasajeros(String prefix) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		Integer sum = 0;
		for(OcupacionVuelo ocp:ls.todas()) {
			if(ocp.vuelo().ciudadDestino().startsWith(prefix)) {
				Integer numPasajeros = ocp.numPasajeros();
				sum = sum + numPasajeros;
			}
		}
		return sum;
	}
	
	//2.  Dado un conjunto de ciudades destino s y una fecha f devuelve cierto si
	// existe un vuelo en la fecha f con destino en s.
	
	public Boolean hayDestino(Set<String> destinos, LocalDate f) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		Boolean a = false;
		for(OcupacionVuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f)) {
				if(destinos.contains(ocp.vuelo().ciudadDestino())) {
					a = true;
					break;
				}
			}
		}
		return a;
	}

	//3. Dada una fecha f devuelve el conjunto de ciudades destino diferentes de todos
	// los vuelos de fecha f
	
	public Set<String> destinosDiferentes(LocalDate f) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		Set<String> a = new HashSet<>();
		for(OcupacionVuelo ocp:ls.todas()) {
			if(ocp.fecha().toLocalDate().equals(f)) {
				String ciudadDestino = ocp.vuelo().ciudadDestino();
				a.add(ciudadDestino);			
			}
		}
		return a;
	}
	
	//4. Dado un anyo devuelve un SortedMap que relacione cada destino con el
	// total de pasajeros a ese destino en el a�o anyo
	
	public SortedMap<String, Integer> totalPasajerosADestino(Integer any) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		SortedMap<String,Integer> a = new TreeMap<String, Integer>(Comparator.reverseOrder());
		for(OcupacionVuelo ocp:ls.todas()) {
			if(ocp.fecha().getYear() == any) {
				String key = ocp.vuelo().ciudadDestino();
				if(a.containsKey(key)) {
					Integer numPasajeros = a.get(key)+ocp.numPasajeros();
					a.put(key,numPasajeros);
				} else {
					a.put(key,ocp.numPasajeros());
				}
			}
		}
		return a;
	}
	
	//5. Dado un destino devuelve el c�digo de la aerolinea del primer vuelo con plazas libres a ese
	// destino
	
	public String primerVuelo(String destino) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		OcupacionVuelo a = null;
		for(OcupacionVuelo ocp:ls.todas()) {
			if(ocp.vuelo().ciudadDestino().equals(destino) &&
			   ocp.vuelo().numPlazas() > ocp.numPasajeros() &&	
			   ocp.fecha().isAfter(LocalDateTime.now())) {
			   if(a==null || ocp.fecha().isBefore(a.fecha())) {
				   a = ocp;
			   }
			}
		}
		if(a==null) throw new IllegalArgumentException("La lista est� vac�a");
		return a.vuelo().codigoAerolinea();
	}

	//6. Devuelve para los vuelos completos un Map que haga corresponder a cada ciudad
	// destino la media de los precios de los vuelos a ese destino.
	
	private Double preM(List<OcupacionVuelo> ls){
		Double sum = 0.;
		Integer n = 0;
		for(OcupacionVuelo ocp:ls) {
			sum = sum + ocp.vuelo().precio();
			n = n +1;
		}
		if(n==0) throw new IllegalArgumentException("El grupo est� vac�o");
		return sum/n;
	}
	
	public Map<String, Double> precioMedio(Integer n) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		Map<String, List<OcupacionVuelo>> a = new HashMap<>();
		for(OcupacionVuelo ocp:ls.todas()) {
			if(ocp.vuelo().numPlazas()-ocp.numPasajeros()==n) {
				String key = ocp.vuelo().ciudadDestino();
				if(a.containsKey(key)) {
					a.get(key).add(ocp);
				} else {
					List<OcupacionVuelo> lsn = new ArrayList<>();
					lsn.add(ocp);
					a.put(key, lsn);
				}
			}
		}
		Map<String, Double> r = new HashMap<>();
		for(String key:a.keySet()) {
			r.put(key,this.preM(a.get(key)));
		}
		return r;
	}
	
	//7. Devuelve un Map tal que dado un entero n haga corresponder
	// a cada fecha la lista de los n destinos con los vuelos de mayor duraci�n.
	
	@Override
	public Map<LocalDate, List<String>> destinosConMayorDuracion(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//8. Dada una fecha f devuelve el precio medio de los vuelos con salida posterior
	// a f. Si no hubiera vuelos devuelve 0.0
	
	public Double precioMedio(LocalDateTime f) {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		Double sum = 0.;
		Integer n = 0;
		for(OcupacionVuelo ocp: ls.todas()) {
			if(ocp.fecha().isAfter(f)) {
				Double precio = ocp.vuelo().precio();
				sum = sum +precio;
				n= n+1;
			}
		}
		return n==0?sum/n:0.0;
	}
	
	//9. Devuelve un Map que haga corresponder a cada destino un conjunto con las
	// fechas de los vuelos a ese destino.
	
	public Map<String, Set<LocalDate>> fechasADestino() {
		OcupacionesVuelos ls = OcupacionesVuelos.of();
		Map<String, Set<LocalDate>> a = new HashMap<>();
		for(OcupacionVuelo ocp: ls.todas()) {
			String key = ocp.vuelo().ciudadDestino();
			LocalDate fecha = ocp.fechaSalida();
			if(a.containsKey(key)) {
				a.get(key).add(fecha);
			} else {
				Set<LocalDate> s = new HashSet<>();
				s.add(fecha);
				a.put(key, s);
			}
		}
		return a;
	}
	
	//10. Devuelve el destino con mayor n�mero de vuelos
	
	@Override
	public String destinoConMasVuelos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//11. Dado un entero m devuelve un conjunto ordenado con las duraciones de todos los vuelos cuya duraci�n es mayor que m minutos.

	@Override
	public SortedSet<Duration> duraciones(Integer m) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//12. Dado un n�mero n devuelve un conjunto con los destinos de los vuelos que est�n entre los n que m�s duraci�n tienen.
	
	@Override
	public Set<String> destinosMayorDuracion(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//13. Dado un n�mero n devuelve un conjunto con los n destinos con m�s vuelos
	
	@Override
	public Set<String> entreLosMasVuelos(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 14. Dado un n�mero entero n devuelve una lista con los destinos que tienen m�s de n vuelos
	
	@Override
	public List<String> masDeNVuelos(Integer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 15. Devuelve un Map que relaci�n cada destino con el porcentaje de los vuelos del total que van a ese destino.
	
	@Override
	public Map<String, Double> porcentajeADestino() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Double> porcentajeADestinoOcupacionesVuelos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 16. Devuelve un Map que haga corresponder a cada ciudad destino el vuelo de m�s barato
	
	@Override
	public Map<String, Vuelo> masBarato() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 17. Devuelve un Map que haga corresponder a cada destino el n�mero de fechas
	// distintas en las que hay vuelos a ese destino.
	
	@Override
	public Map<String, Integer> fechasDistintas() {
		// TODO Auto-generated method stub
		return null;
	}


}
