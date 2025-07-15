package us.lsi.aeropuerto;

import us.lsi.tools.Utils;

public record Aerolinea(String codigo, String nombre) {
	
	public static Aerolinea parse(String text) {
		String[] campos = text.split(",");
		String codigo = campos[0];
		String nombre = campos[1].trim();
		return new Aerolinea(codigo,nombre);
	}

	
	public static Aerolinea of(String codigo, String nombre) {
		return new Aerolinea(codigo,nombre);
	}
	
	public Aerolinea {
		assert Utils.allNotNull(codigo, nombre) : "Los campos no pueden ser null";
	}

}
