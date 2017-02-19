package mx.gm.com.capadatos;

import java.util.List;

import mx.gm.com.capadatos.domain.Usuario;

public interface UsuarioDao {

	public Usuario getUsuarioById(int idUsuario);
	
	public Usuario getUsuarioByUsername(String username);

	public List<Usuario> getUsuarioByPersonaId(int idPersona);
	
	public List<Usuario> listAllUsuarios();
	
	public void insertUsuario(Usuario usuario);
	
	public void updateUsuario(Usuario usuario);
	
	public void deleteUsuario(Usuario usuario);	
	
	public void inserta_O_updateUsuario(Usuario usuario);
	
}
