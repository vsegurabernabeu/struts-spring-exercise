package mx.gm.com.capaservicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gm.com.capadatos.UsuarioDao;
import mx.gm.com.capadatos.domain.Usuario;

@Service("usuarioServicio")
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UsuarioServicioImpl implements UsuarioServicio {

	@Autowired
	UsuarioDao usuarioDao;
	
	public Usuario obtieneUsuarioPorId(int idUsuario) {
		return usuarioDao.getUsuarioById(idUsuario);
	}

	public Usuario obtieneUsuarioPorUsername(String username) {
		return usuarioDao.getUsuarioByUsername(username);
	}

	public List<Usuario> obtieneUsuarioPorPersona(int idPersona) {
		return usuarioDao.getUsuarioByPersonaId(idPersona);
	}

	public List<Usuario> listadoUsuarios() {
		return usuarioDao.listAllUsuarios();
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void agregarUsuario(Usuario usuario) {
		usuarioDao.insertUsuario(usuario);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void modificarUsuario(Usuario usuario) {
		usuarioDao.updateUsuario(usuario);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void borrarUsuario(Usuario usuario) {
		usuarioDao.deleteUsuario(usuario);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void guardarUsuario(Usuario usuario) {
		usuarioDao.inserta_O_updateUsuario(usuario);
	}
	
}
