package us.lsi.centro;

import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.Set2;

public class Grupos {
	
	public static Grupos gestorDeGrupos = null;
	
	public static Grupos of() {
		if (Grupos.gestorDeGrupos == null)
			Grupos.gestorDeGrupos = Grupos.of(Centro.of());
        return Grupos.gestorDeGrupos;
	}
	
	public static Grupos of(Centro centro) {		
		return new Grupos(centro);
	}

	private Set<Grupo> grupos;
	private Centro centro;

	private Grupos(Centro centro) {
		super();
		this.centro = centro;
		this.grupos = centro.matriculas().todas()
				.stream().map(a->Grupo.of(a.ida(),a.idg())).collect(Collectors.toSet());;	
	}
    
    public Integer size() { 
        return this.grupos.size();
    }
    
    public Set<Grupo> todos() {
    	return grupos;
    }
    
    public Set<Grupo> gruposSinProfesor() {
    	Set<Grupo> gruposA = centro.asignaciones().todas()
    			.stream().map(a->Grupo.of(a.ida(),a.idg())).collect(Collectors.toSet());
    	return Set2.difference(this.grupos,gruposA);
    }
}
