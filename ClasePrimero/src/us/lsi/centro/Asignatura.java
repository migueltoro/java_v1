package us.lsi.centro;

public record Asignatura(Integer ida, String nombre, Integer creditos, Integer numMaxGrupos) {
	    
	public static Asignatura parse(String text) {
		String[] partes = text.split(",");
		String p1 = partes[1].strip();
		String cp = p1.substring(0, 1).toUpperCase() + p1.substring(1);
		return new Asignatura(Integer.parseInt(partes[0].strip()), cp, 
				Integer.parseInt(partes[2].strip()),
				Integer.parseInt(partes[3].strip()));
	}
	        
	    public String toString() {
	        return String.format("%s, %d, %d",this.nombre(),this.creditos(),this.numMaxGrupos());
	    }
}
