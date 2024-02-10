package us.lsi.aeropuerto;

import us.lsi.tools.File2;

public class EspacioAereo {
	    
	    private static EspacioAereo gestorDeEspacioAereo = null;
	    
	    private Aerolineas aerolineas;
		private Vuelos vuelos;
		private OcupacionesVuelos ocupacionesVuelos;
		private Aeropuertos aeropuertos;
	    
	    private EspacioAereo(Aerolineas aerolineas,Vuelos vuelos,OcupacionesVuelos ocupacionesVuelos,
	    		Aeropuertos aeropuertos) {
	    	this.aerolineas = aerolineas;
			this.vuelos = vuelos;
			this.ocupacionesVuelos = ocupacionesVuelos;
			this.aeropuertos = aeropuertos;
	    }
	    
	    public static EspacioAereo of(Aerolineas aerolineas, Vuelos vuelos, OcupacionesVuelos ocupacionesVuelos,
				Aeropuertos aeropuertos) {
			return new EspacioAereo(aerolineas, vuelos, ocupacionesVuelos, aeropuertos);
		}
	    
	    public static EspacioAereo of() {
	    	return EspacioAereo.of(File2.root_project());
	    }
	    
	    public static EspacioAereo of(String root) {
	        if(EspacioAereo.gestorDeEspacioAereo == null) {
	            String faeropuertos =File2.absolute_path("aeropuertos/aeropuertos.csv",root);
	            String faerolineas =File2.absolute_path("aeropuertos/aerolineas.csv",root);         
	            String fvuelos = File2.absolute_path("aeropuertos/vuelos.csv",root);
	            String focupacionesVuelos = File2.absolute_path("aeropuertos/ocupacionesVuelos.csv",root);
	            Aeropuertos aeropuertos = Aeropuertos.parse(faeropuertos);
	            Aerolineas aerolineas = Aerolineas.parse(faerolineas);
	            Vuelos vuelos =  Vuelos.parse(fvuelos);
	            OcupacionesVuelos ocupaciones_vuelos = OcupacionesVuelos.parse(focupacionesVuelos);       
	            EspacioAereo.gestorDeEspacioAereo = 
	            		EspacioAereo.of(aerolineas, vuelos, ocupaciones_vuelos, aeropuertos);
	        }
	        return EspacioAereo.gestorDeEspacioAereo;
	    }
	    
	    
	    public static EspacioAereo parse(String faeropuertos,String faerolineas,String fvuelos,
	    		String focupacionesVuelos) {
	        Aeropuertos aeropuertos = Aeropuertos.parse(faeropuertos);
	        Aerolineas aerolineas = Aerolineas.parse(faerolineas);
	        Vuelos vuelos =  Vuelos.parse(fvuelos);
	        OcupacionesVuelos ocupacionesVuelos = OcupacionesVuelos.parse(focupacionesVuelos);     
	        return EspacioAereo.of(aerolineas, vuelos, ocupacionesVuelos, aeropuertos);
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
