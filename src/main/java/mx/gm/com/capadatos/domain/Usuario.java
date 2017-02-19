package mx.gm.com.capadatos.domain;

public class Usuario {

	private int idUsuario;
	//private int idPersona;
	private Persona persona;
	private String username;
	private String password;
	
	public Usuario(){
		
	}
	
	public Usuario(int idUsuario){
		this.idUsuario = idUsuario;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/*
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}*/
	
	
	public String getUsername() {
		return username;
	}
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", persona=" + persona + ", username=" + username + ", password="
				+ password + "]";
	}
	
}
