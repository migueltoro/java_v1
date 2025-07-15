package us.lsi.aeropuerto;

import us.lsi.tools.Utils;

public record Aeropuerto(String codigo, String ciudad, String pais, String nombre) {
	
	public static Aeropuerto parse(String text) {
		String[] campos = text.split(",");
		String codigo = campos[2];
		String ciudad = campos[3];
		String pais = campos[1];
		String nombre = campos[0];
		return Aeropuerto.of(codigo,ciudad,pais,nombre);
	}
	
	public static Aeropuerto of(String codigo,
			String ciudad,
			String pais,
			String nombre) {
		return new Aeropuerto(codigo,ciudad,pais,nombre);	
	}
	
	public Aeropuerto {
		assert Utils.allNotNull(codigo, ciudad, pais, nombre) : "Los campos no pueden ser null";
	}
	
}
