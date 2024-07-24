package us.lsi.aeropuerto;

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
	   
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
