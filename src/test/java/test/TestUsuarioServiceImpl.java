package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gm.com.capadatos.UsuarioDao;
import mx.gm.com.capadatos.domain.Persona;
import mx.gm.com.capadatos.domain.Usuario;
import mx.gm.com.capaservicio.UsuarioServicio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:datasource-test.xml" })

public class TestUsuarioServiceImpl {

	static final Log logger = LogFactory.getLog("TestUsuarioServiceImpl");

	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	UsuarioDao usuarioDao;
	
	@Test
	public void testListadoUsuario() {
		System.out.println();
		logger.info("Inicio del test testListadoUsuario");
		try {
			List<Usuario> lista = usuarioServicio.listadoUsuarios();

			int contPersonas = desplegarPersonas();
			assertEquals(lista.size(), contPersonas);
			assertEquals(lista.size(), 3);
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testListadoUsuario");
		System.out.println();
	}

	private int desplegarPersonas() {
		List<Usuario> listaUsuarios = usuarioDao.listAllUsuarios();

		int contUsuarios = 0;
		for (Usuario u : listaUsuarios) {
			logger.info("Usuario :" + u);
			contUsuarios++;
		}
		logger.info("Hay " + contUsuarios + " usuarios.");
		return contUsuarios;
	}
	
	@Test
	public void testObtieneUsuarioPorId() {
		System.out.println();
		logger.info("Inicio del test testObtieneUsuarioPorId");
		try {
			int idUsuario = 1;
			Usuario usuario = usuarioServicio.obtieneUsuarioPorId(idUsuario);

			logger.info("Usuario recuperada por id:" + idUsuario + "=" + usuario);
			assertEquals(usuario.getUsername(), "pepito");
			
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testObtieneUsuarioPorId");
		System.out.println();
	}

	
	
	
	@Test
	public void testObtieneUsuarioPorUsername() {
		System.out.println();
		logger.info("Inicio del test testObtieneUsuarioPorUsername");
		try {
			String username = "pepito";
			Usuario usuario = usuarioServicio.obtieneUsuarioPorUsername(username);

			logger.info("Usuario recuperada por username:" + username + "=" + usuario);
			assertEquals(usuario.getUsername(), "pepito");
			
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testObtieneUsuarioPorUsername");
		System.out.println();
	}
	
	@Test
	public void testObtieneUsuarioPorPersona() {
		System.out.println();
		logger.info("Inicio del test testObtieneUsuarioPorPersona");
		try {
			int idPersona=1;
			List<Usuario> listaUsuarios = usuarioServicio.obtieneUsuarioPorPersona(idPersona);
			
			assertEquals(2,listaUsuarios.size());
			assertEquals(listaUsuarios.get(0).getUsername(),"pepito");
			assertEquals(listaUsuarios.get(1).getUsername(),"lucia");
			
			//lista de usuarios obtenida por idPersona=1
			logger.info("lista de usuarios obtenida por idPersona=1");
			for(Usuario u : listaUsuarios) {
				logger.info("Usuario: "+u);
			}
			
		} catch(Exception e){
			logger.error("Error Servicio",e);
		}
		
		logger.info("Fin del test testObtieneUsuarioPorPersona");
		System.out.println();
	}
	
	
	@Test
	@Transactional
	public void testOperacionesUsuario() {
		try {
			System.out.println();
			logger.info("Inicio del test testOperacionesUsuario");

			Usuario usuario = new Usuario();
			usuario.setPersona(new Persona(1));
			usuario.setUsername("alcohol");
			usuario.setPassword("Pdjsn76.");

			// Insertamos -------------------------------------------------
			usuarioServicio.agregarUsuario(usuario);

			// Comprobamos que se ha insertado correctamente
			int contPersonas = desplegarPersonas();
			assertEquals(4, contPersonas);

			// Comprobamos que son los datos correctos
			Usuario usuario2;// = new Usuario();
			usuario2 = usuarioServicio.obtieneUsuarioPorUsername("alcohol");
			assertEquals(usuario2.getUsername(), "alcohol");
			assertEquals(usuario2.getPassword(), "Pdjsn76.");

			// Modificamos -------------------------------------------------
			usuario2.setUsername("Flavio");
			usuario2.setPassword("XdzajH11.");
			usuarioServicio.modificarUsuario(usuario2);

			// Comprobamos que son los datos correctos
			Usuario usuario3 = new Usuario();
			usuario3 = usuarioServicio.obtieneUsuarioPorUsername("Flavio");
			assertEquals(usuario3.getUsername(), "Flavio");
			assertEquals(usuario3.getPassword(), "XdzajH11.");
			logger.info("Persona modificada con username: 'Flavio' = "+usuario3);

			
			// Borramos ----------------------------------------------------
			usuarioServicio.borrarUsuario(usuario3);
			Usuario usuario4 = null;
			usuario4 = usuarioServicio.obtieneUsuarioPorUsername("Flavio");
			assertNull(usuario4);

			logger.info("Persona borrada con username:Flavio");
			
			// Comprobamos que se ha insertado correctamente
			contPersonas = desplegarPersonas();
			assertEquals(3, contPersonas);
			
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testOperacionesUsuario");
		System.out.println();
	}
}
