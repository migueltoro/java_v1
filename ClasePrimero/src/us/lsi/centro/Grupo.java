package us.lsi.centro;

public record Grupo(Integer ida, Integer idg) {
	
	public static Grupo of(Integer ida, Integer idg) {
		return new Grupo(ida,idg);
	}
	
	public Grupo {
		assert ida != null && idg != null : "Los campos no pueden ser null";
	}
	
	public String toString() {
		return String.format("(Asignatura = %d, Grupo = %d)",this.ida(),this.idg());
	}

}
