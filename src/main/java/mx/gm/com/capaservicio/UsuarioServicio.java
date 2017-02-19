package mx.gm.com.capaservicio;

import java.util.List;

import mx.gm.com.capadatos.domain.Usuario;

public interface UsuarioServicio {
	
	public Usuario obtieneUsuarioPorId(int idUsuario);
	
	public Usuario obtieneUsuarioPorUsername(String username);

	public List<Usuario> obtieneUsuarioPorPersona(int idPersona);
	
	public List<Usuario> listadoUsuarios();
	
	public void agregarUsuario(Usuario usuario);
	
	public void modificarUsuario(Usuario usuario);
	
	public void borrarUsuario(Usuario usuario);	
	
	public void guardarUsuario(Usuario usuario);
}
