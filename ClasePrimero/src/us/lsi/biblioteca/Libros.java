package us.lsi.biblioteca;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Libros {

	public static Libros gestorDeLibros = null;
	
	public static Libros of() {
		return Libros.of("");
	}
	
	public static Libros of(String root) {
		if (Libros.gestorDeLibros == null)
			Libros.gestorDeLibros = 
				Libros.parse(File2.absolute_path("biblioteca/Libros.txt",root));
        return Libros.gestorDeLibros;
	}
	
	public static Libros parse(String file) {
		Set<Libro> libros = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Libro.parse(ln)).collect(Collectors.toSet());    	
		Libros.gestorDeLibros = new Libros(libros);
		return Libros.gestorDeLibros;
	}
	
	public static Libros of(Set<Libro> Libros) {
		return new Libros(Libros);
	}

	private Set<Libro> libros;
	private Map<String,Libro> librosIsbn;

	private Libros(Set<Libro> Libros) {
		super();
		this.libros = Libros;
		this.librosIsbn = this.libros.stream().collect(Collectors.toMap(p->p.isbn(),p->p));
	}
    
    public Libro libro(String dni) { 
        return this.librosIsbn.get(dni);
    }
    
    public Libro get(Integer index) { 
        return this.libros.stream().toList().get(index);
    }
    
    public Set<Libro> todos() {
		return libros;
	}
    
    public Integer size() { 
        return this.libros.size();
    }
    
    public void addLibro(Libro Libro) {
        this.libros.add(Libro);
    }
    
    public void removeLibro(Libro Libro) {
        this.libros.remove(Libro);
    }
    
    public Set<Libro> librosDeAutor(String autor) {
		return this.libros.stream()
				.filter(lb->lb.autor().equals(autor))
				.collect(Collectors.toSet());
	}
    
    @Override
    public String toString() {
		return this.libros.stream()
				.map(lb ->lb.toString())
				.collect(Collectors.joining("\n"));
	}

}
