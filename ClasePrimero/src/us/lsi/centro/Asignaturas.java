package us.lsi.centro;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Asignaturas {
	
	public static Asignaturas gestorDeAsignaturas = null;
	
	public static Asignaturas of() {
		return Asignaturas.of("centro/asignaturas.txt");
	}
	
	public static Asignaturas of(String file) {
		if (Asignaturas.gestorDeAsignaturas == null)
			Asignaturas.gestorDeAsignaturas = new Asignaturas(file);
        return Asignaturas.gestorDeAsignaturas;
	}
	
	public static Asignaturas of(Set<Asignatura> Asignaturas) {
		return new Asignaturas(Asignaturas);
	}

	public Set<Asignatura> todas() {
		return asignaturas;
	}

	private Set<Asignatura> asignaturas;
	private Map<Integer,Asignatura> asignaturasId;
	
	private Asignaturas(Set<Asignatura> asignaturas) {
		super();
		this.asignaturas = asignaturas;
		this.asignaturasId = this.asignaturas.stream().collect(Collectors.toMap(p -> p.ida(), p -> p));
	}

	private Asignaturas(String file) {
		super();
		this.asignaturas = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Asignatura.parse(ln)).collect(Collectors.toSet());
		this.asignaturasId = this.asignaturas.stream().collect(Collectors.toMap(p->p.ida(),p->p));
	}
    
    public Asignatura asignatura(Integer id) { 
        return this.asignaturasId.get(id);
    }
    
    public Asignatura get(Integer index) { 
        return this.asignaturas.stream().toList().get(index);
    }
    
    public Integer size() { 
        return this.asignaturas.size();
    }
}
