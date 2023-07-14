package us.lsi.centro;

public record Direccion(String calle, String ciudad, Integer zip_code) {
	
	public static Direccion parse(String text) {
        String[] partes = text.split(";");
        return new Direccion(partes[0],partes[1],Integer.parseInt(partes[2]));
	}

}
