package us.lsi.aeropuerto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EspacioAereo {
	    
	    private static EspacioAereo gestorDeEspacioAereo = null;
	    
	    private Aerolineas aerolineas;
		private VuelosProgramados vuelos;
		private Vuelos ocupacionesVuelos;
		private Aeropuertos aeropuertos;
	    
	    private EspacioAereo(String faerolineas,String fvuelos,String focupacionesVuelos,
	    		String faeropuertos) {
	    	this.aerolineas = Aerolineas.of(faerolineas);
			this.vuelos = VuelosProgramados.of(fvuelos);
            this.ocupacionesVuelos = Vuelos.of(focupacionesVuelos);
			this.aeropuertos = Aeropuertos.of(faeropuertos);;
	    }
	    
	    
	    public static EspacioAereo of() {
	    	return EspacioAereo.of("aeropuertos/aerolineas.csv",
        			"aeropuertos/vuelosProgramados.csv",
        			"aeropuertos/vuelos.csv",
        			"aeropuertos/aeropuertos.csv");
	    }
	    
	    public static EspacioAereo of(String root) {    	
	    	return EspacioAereo.of(root+"/"+"aeropuertos/aerolineas.csv",
	    			root+"/"+"aeropuertos/vuelosProgramados.csv",
	    			root+"/"+"aeropuertos/vuelos.csv",
	    			root+"/"+"aeropuertos/aeropuertos.csv");
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
	    
	    public VuelosProgramados vuelosProgramados() {
	        return this.vuelos;
	    }
	    
	    public Vuelos vuelos() {
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
			VuelosProgramados vuelos = VuelosProgramados.of();
			Vuelos ocupaciones = Vuelos.of();
			return vuelos.todos().stream().filter(
					vuelo -> vuelo.codigoOrigen().equals(codigoOrigen) && vuelo.hora().isAfter(fecha.toLocalTime()))
					.filter(vuelo -> {
						Vuelo ocupacion = ocupaciones.ocupacionVuelo(vuelo.codigo(), fecha).orElse(null);
						return ocupacion == null || (vuelo.numPlazas() - ocupacion.numPasajeros() > n);
					})
					.map(VueloProgramado::codigoDestino)
					.collect(Collectors.toSet());
		}

	   
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
