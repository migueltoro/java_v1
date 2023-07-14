package us.lsi.ejemplos_b1_tipos;

public record Direccion(String calle, String ciudad, Integer zipCode) {
	    
	    public static Direccion parse(String text) {
	        String[] partes = text.split(";");
	        return new Direccion(partes[0],partes[1],Integer.parseInt(partes[2]));
	    }
	    
	    public String toString() {
	        return String.format("%s,%s",this.calle(),this.ciudad(),this.zipCode());
	    }

}
