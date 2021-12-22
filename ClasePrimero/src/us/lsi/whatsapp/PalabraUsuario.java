package us.lsi.whatsapp;

public record PalabraUsuario(String palabra,String usuario) {
	
	public static PalabraUsuario of(String palabra, String usuario){
		return new PalabraUsuario(palabra,usuario);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)",palabra, usuario);
	}	
}
