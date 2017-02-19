package mx.gm.com.capadatos.domain;

import java.util.List;

public class Persona {
	private int idPersona;
	private String nombre;
	private String apellido;
	
	
	private List<Usuario> usuarios=null;
	
	public Persona(){
		
	}
	
	public Persona(int idPersona){
		this.idPersona = idPersona;
	}
	
	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		String r="Persona [idPersona=" + idPersona + ", nombre=" + nombre + ", apellido=" + apellido + "]";
		
		if(this.getUsuarios()!=null){
			
			r=r+"[";
			for(Usuario u:usuarios)
			{
				r=r+"["+u.getUsername()+"]";
			}
			r=r+"]";
		}else{
			r=r+"[usuarios: null]";
		}
		return r;
	}

}
