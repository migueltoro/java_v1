package us.lsi.biblioteca;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import us.lsi.tools.File2;

public class Usuarios {
	
	public static Usuarios gestorDeUsuarios = null;
	
	public static Usuarios of() {
		return Usuarios.of("biblioteca/usuarios.txt");		
	}
	
	public static Usuarios of(String file) {
		if (Usuarios.gestorDeUsuarios == null)
			Usuarios.gestorDeUsuarios = new Usuarios(file);
        return Usuarios.gestorDeUsuarios;
	}
	
	public static Usuarios parse(String file) {
		Set<Usuario> usuarios = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Usuario.parse(ln)).collect(Collectors.toSet());    	
		Usuarios.gestorDeUsuarios = new Usuarios(usuarios);
		return Usuarios.gestorDeUsuarios;
	}
	
	public static Usuarios of(Set<Usuario> Usuarios) {
		return new Usuarios(Usuarios);
	}

	private Set<Usuario> usuarios;
	private Map<String,Usuario> usuariosDni;
	
	private Usuarios(Set<Usuario> usuarios) {
        super();
        this.usuarios = usuarios;
        this.usuariosDni = this.usuarios.stream().collect(Collectors.toMap(e->e.dni(),e->e));
	}     		

	private Usuarios(String file) {
		super();
		this.usuarios = File2.streamDeFichero(file,"utf-8")
        		.map(ln->Usuario.parse(ln)).collect(Collectors.toSet());  
		this.usuariosDni = this.usuarios.stream().collect(Collectors.toMap(e->e.dni(),e->e));
	}
    
    public Usuario usuario(String dni) { 
        return this.usuariosDni.get(dni);
    }
    
    public Usuario get(Integer index) { 
        return this.usuarios.stream().toList().get(index);
    }
    
    public Set<Usuario> todos() {
		return usuarios;
	}
    
    public Integer size() { 
        return this.usuarios.size();
    }
    
    public void addUsuario(Usuario Usuario) {
        this.usuarios.add(Usuario);
    }
    
    public void removeUsuario(Usuario Usuario) {
        this.usuarios.remove(Usuario);
    }

}
