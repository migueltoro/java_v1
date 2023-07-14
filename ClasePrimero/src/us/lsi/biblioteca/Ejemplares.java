package us.lsi.biblioteca;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.tools.File2;
import us.lsi.tools.Pair;

public class Ejemplares {
	
	public static Ejemplares gestorDeEjemplares = null;
	
	public static Ejemplares of() {
		if (Ejemplares.gestorDeEjemplares == null)
			Ejemplares.gestorDeEjemplares = Ejemplares.of("ficheros_biblioteca/Ejemplares.txt");
        return Ejemplares.gestorDeEjemplares;
	}
	
	public static Ejemplares of(String file) {
		Set<Ejemplar> ejemplares = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Ejemplar.parse(ln)).collect(Collectors.toSet());    	
		Ejemplares.gestorDeEjemplares = new Ejemplares(ejemplares);
		return Ejemplares.gestorDeEjemplares;
	}
	
	public static Ejemplares of(Set<Ejemplar> Ejemplares) {
		return new Ejemplares(Ejemplares);
	}

	private Set<Ejemplar> ejemplares;
	private Map<Pair<String,Integer>,Ejemplar> ejemplaresId; 

	private Ejemplares(Set<Ejemplar> Ejemplares) {
		super();
		this.ejemplares = Ejemplares;
		this.ejemplaresId = this.ejemplares.stream().collect(Collectors.toMap(p->Pair.of(p.isbn(),p.codigo()),p->p));
	}
    
    public Ejemplar ejemplar(String isbn,Integer codigo) { 
        return this.ejemplaresId.get(Pair.of(isbn,codigo));
    }
    
    public List<Ejemplar> ejemplares(Libro libro) {
		return this.ejemplares.stream().filter(ej->ej.isbn().equals(libro.isbn())).toList();
	}
    
    public Ejemplar get(Integer index) { 
        return this.ejemplares.stream().toList().get(index);
    }
    
    public Set<Ejemplar> todos() {
		return ejemplares;
	}
    
    public Integer size() { 
        return this.ejemplares.size();
    }
    
    public void addEjemplar(Ejemplar ejemplar) {
        this.ejemplares.add(ejemplar);
    }
    
    public void removeEjemplar(Ejemplar ejemplar) {
        this.ejemplares.remove(ejemplar);
    }
    
    private Integer codigoEjemplarLibre(String isbn) {
		return Stream.iterate(1,n->n+1)
				.map(i->Pair.of(isbn,i))
				.filter(lb->!this.ejemplaresId.keySet().contains(lb))
				.findFirst()
				.get()
				.second();
	}
    
    public Ejemplar addEjemplar(String isbn, LocalDate fecha) {
		Integer n = this.codigoEjemplarLibre(isbn);
		Ejemplar ejemplar = Ejemplar.of(isbn, n, fecha);
		this.ejemplaresId.put(Pair.of(isbn, n), ejemplar);
		return ejemplar;
	}
    
    public Ejemplar eliminaEjemplar(Ejemplar ejemplar) {	
		this.ejemplares.remove(ejemplar);
		this.ejemplaresId.remove(Pair.of(ejemplar.isbn(),ejemplar.codigo()));
		return ejemplar;
	}
    
    public Set<Ejemplar> ejemplares(String isbn) {
		return this.ejemplares.stream().filter(ej->ej.isbn().equals(isbn))
				.collect(Collectors.toSet());
	}
    
}
