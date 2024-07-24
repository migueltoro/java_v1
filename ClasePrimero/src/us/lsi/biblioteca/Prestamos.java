package us.lsi.biblioteca;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.biblioteca.Prestamo.TipoPrestamo;
import us.lsi.tools.File2;

public class Prestamos {
	
	public static Prestamos gestorDePrestamos = null;
	
	public static Prestamos of() {		
        return Prestamos.of("biblioteca/Prestamos.txt");		
	}
	
	public static Prestamos of(String file) {
		if (Prestamos.gestorDePrestamos == null)
			Prestamos.gestorDePrestamos = new Prestamos(file);
        return Prestamos.gestorDePrestamos;
	}
	
	public static Prestamos of(Set<Prestamo> Prestamos) {
		return new Prestamos(Prestamos);
	}

	private Set<Prestamo> prestamos;
	private Map<Integer,Prestamo> prestamosId;
	
	private Prestamos(Set<Prestamo> prestamos) {
		super();
		this.prestamos = prestamos;
		this.prestamosId = this.prestamos.stream().collect(Collectors.toMap(p -> p.codigo(), p -> p));
	}

	private Prestamos(String file) {
		super();
		this.prestamos = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Prestamo.parse(ln)).collect(Collectors.toSet());;
		this.prestamosId = this.prestamos.stream().collect(Collectors.toMap(p->p.codigo(),p->p));
	}
    
    public Prestamo prestamo(Integer codigo) { 
        return this.prestamosId.get(codigo);
    }
    
    public Set<Prestamo> todos() {
		return prestamos;
	}
    
    public Integer size() { 
        return this.prestamos.size();
    }
    
    public void addPrestamo(Prestamo Prestamo) {
        this.prestamos.add(Prestamo);
    }
    
    public void removePrestamo(Prestamo Prestamo) {
        this.prestamos.remove(Prestamo);
    }


    public Integer codigoPrestamoLibre() {
		return Stream.iterate(1,n->n+1)
				.filter(i->!this.prestamosId.keySet().contains(i))			
				.findFirst()
				.get();
	}
    
    public Prestamo nuevoPrestamo(Ejemplar ejemplar, Usuario usuario, LocalDate fecha, TipoPrestamo tipo) {
		Integer codigo = codigoPrestamoLibre();
		Prestamo p = Prestamo.of(codigo,ejemplar.isbn(),ejemplar.codigo(), usuario.dni(), fecha, tipo);
		this.prestamos.add(p);
		this.prestamosId.put(codigo,p);
		return p;
	}
    
    public Prestamo devuelvePrestamo(Prestamo p) {
		this.prestamos.remove(p);
		this.prestamosId.remove(p.codigo());
		return p;
	}
    
    public Boolean algunEjemplarPrestado(String isbn) {
		return this.prestamosId.entrySet().stream()
				.anyMatch(p->p.getValue().isbn().equals(isbn));
	}
    
    public Prestamo get(Integer index) { 
        return this.prestamos.stream().toList().get(index);
    }
}
