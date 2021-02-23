package us.lsi.aeropuerto;

public record EmpresaAerea(String codigo, String nombre) {
	
	public static EmpresaAerea parse(String text) {
		String[] campos = text.split(",");
		String codigo = campos[0];
		String nombre = campos[1].trim();
		return new EmpresaAerea(codigo,nombre);
	}
	
	public static EmpresaAerea of(String codigo, String nombre) {
		return new EmpresaAerea(codigo,nombre);
	}

}
