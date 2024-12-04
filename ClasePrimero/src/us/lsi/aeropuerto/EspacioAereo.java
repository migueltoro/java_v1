package us.lsi.aeropuerto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EspacioAereo {
	    
	    private static EspacioAereo gestorDeEspacioAereo = null;
	    
	    private Aerolineas aerolineas;
		private Vuelos vuelos;
		private OcupacionesVuelos ocupacionesVuelos;
		private Aeropuertos aeropuertos;
	    
	    private EspacioAereo(String faerolineas,String fvuelos,String focupacionesVuelos,
	    		String faeropuertos) {
	    	this.aerolineas = Aerolineas.of(faerolineas);
			this.vuelos = Vuelos.of(fvuelos);
            this.ocupacionesVuelos = OcupacionesVuelos.of(focupacionesVuelos);
			this.aeropuertos = Aeropuertos.of(faeropuertos);;
	    }
	    
	    
	    public static EspacioAereo of() {
	    	return EspacioAereo.of("aeropuertos/aerolineas.csv",
        			"aeropuertos/vuelos.csv",
        			"aeropuertos/ocupacionesVuelos.csv",
        			"aeropuertos/aeropuertos.csv");
	    }
	    
	    public static EspacioAereo of(String faerolineas,String fvuelos,String focupaciones_vuelos,
	    		String faeropuertos) {
	        if(EspacioAereo.gestorDeEspacioAereo == null) {      
	            EspacioAereo.gestorDeEspacioAereo = 
	            		new EspacioAereo(faerolineas, fvuelos, focupaciones_vuelos, faeropuertos);
	        }
	        return EspacioAereo.gestorDeEspacioAereo;
	    }
	 
	    public Aerolineas aerolineas() {
	        return this.aerolineas;
	    }
	    
	    public Vuelos vuelos() {
	        return this.vuelos;
	    }
	    
	    public OcupacionesVuelos ocupacionesVuelos() {
	        return this.ocupacionesVuelos;
	    }
	    
	    public Aeropuertos aeropuertos() {
	        return this.aeropuertos;
	    }
	    
		public static Set<String> obtenerAeropuertosDestino(String codigoOrigen, LocalDateTime fecha, int n) {
			if (codigoOrigen == null) {
				throw new IllegalArgumentException("El código de origen no puede ser null");
			}
			if (fecha == null) {
				throw new IllegalArgumentException("La fecha no puede ser null");
			}
			if (n < 0) {
				throw new IllegalArgumentException("El numero de plazas debe ser mayor o igual a cero");
			}
			if (fecha.isBefore(LocalDateTime.now())) {
				throw new IllegalArgumentException("La fecha debe estar en el futuro");
			}
			Vuelos vuelos = Vuelos.of();
			OcupacionesVuelos ocupaciones = OcupacionesVuelos.of();
			return vuelos.todos().stream().filter(
					vuelo -> vuelo.codigoOrigen().equals(codigoOrigen) && vuelo.hora().isAfter(fecha.toLocalTime()))
					.filter(vuelo -> {
						OcupacionVuelo ocupacion = ocupaciones.ocupacionVuelo(vuelo.codigo(), fecha).orElse(null);
						return ocupacion == null || (vuelo.numPlazas() - ocupacion.numPasajeros() > n);
					})
					.map(Vuelo::codigoDestino)
					.collect(Collectors.toSet());
		}

	   
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
