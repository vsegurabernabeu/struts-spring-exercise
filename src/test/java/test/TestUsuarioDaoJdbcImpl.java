package test;

import static org.junit.Assert.assertEquals;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml", "classpath:applicationContext.xml" })
public class TestUsuarioDaoJdbcImpl {

	static final Log logger = LogFactory.getLog("TestUsuarioDaoJdbcImpl");

	@Autowired
	UsuarioDao usuarioDao;

	@Test
	public void testGetUsuarioById() {
		try {
			System.out.println();
			logger.info("Inicio - testGetUsuarioById");
			Usuario usuario = usuarioDao.getUsuarioById(1);

			// Comprobamos si es correcto
			assertEquals("pepito", usuario.getUsername());

			logger.info("Usuario recuperado con id=1 : " + usuario);

			logger.info("Fin - testGetUsuarioById");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	
	@Test
	public void testGetUsuarioByUsername() {
		try {
			System.out.println();
			logger.info("Inicio - testGetUsuarioByUsername");
			Usuario usuario = usuarioDao.getUsuarioByUsername("lucia");

			// Comprobamos si es correcto
			assertEquals("lucia", usuario.getUsername());

			logger.info("Usuario recuperado con username=lucia : " + usuario);

			logger.info("Fin - testGetUsuarioByUsername");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	@Test
	public void testGetUsuarioByPersonaId() {
		try {
			System.out.println();
			logger.info("Inicio - testGetUsuarioByPersonaId");
			List<Usuario> listaUsuarios = usuarioDao.getUsuarioByPersonaId(1);

			// Usuarios obtenidos con el idPersona 1
			logger.info("Lista obtenida con idPersona = 1:");
			int contadorUsuarios = this.desplegarUsuarios(listaUsuarios);

			// Comprobamos si es correcto
			assertEquals(contadorUsuarios, listaUsuarios.size());
			assertEquals("pepito", listaUsuarios.get(0).getUsername());
			assertEquals("lucia", listaUsuarios.get(1).getUsername());

			logger.info("Fin - testGetUsuarioByPersonaId");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	private int desplegarUsuarios(List<Usuario> lista) {

		int cont = 0;
		for (Usuario u : lista) {
			logger.info("Usuario :" + u);
			cont++;
		}

		return cont;
	}

	@Test
	public void testListAllUsuarios() {
		try {
			System.out.println();
			logger.info("Inicio - test testListAllUsuarios");

			List<Usuario> lista = usuarioDao.listAllUsuarios();
			logger.info("Lista obtenida:");
			int contador = this.desplegarUsuarios(lista);

			// Comprobamos el tamaño
			assertEquals(contador, lista.size());

			logger.info("Fin - test testListAllUsuarios");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	
	@Test
	@Transactional
	public void testInsertUsuario() {
		try {
			System.out.println();
			logger.info("Inicio - test testInsertUsuario");

			Usuario usuario = new Usuario();
			usuario.setPersona(new Persona(2));
			usuario.setUsername("Ángel");
			usuario.setPassword("KlsJdm2");

			usuarioDao.insertUsuario(usuario);

			logger.info("Lista obtenida tras la inserción");

			List<Usuario> lista = usuarioDao.listAllUsuarios();
			int contador = this.desplegarUsuarios(lista);

			assertEquals(4, lista.size());
			logger.info("Fin - test testInsertUsuario");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	
	@Test
	@Transactional
	public void testUpdateUsuario() {
		try {
			System.out.println();
			logger.info("Inicio - test testUpdateUsuario");

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(2);
			usuario.setPersona(new Persona(1));
			usuario.setUsername("sofia");
			usuario.setPassword("KKKKKK");

			usuarioDao.updateUsuario(usuario);

			logger.info("Lista obtenida tras el update");

			List<Usuario> lista = usuarioDao.listAllUsuarios();
			int contador = this.desplegarUsuarios(lista);

			assertEquals(3, lista.size());
			logger.info("Fin - test testUpdateUsuario");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	
	@Test
	@Transactional
	public void testDeleteUsuario() {
		try {
			System.out.println();
			logger.info("Inicio - test testDeleteUsuario");

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(2);

			usuarioDao.deleteUsuario(usuario);

			logger.info("Lista obtenida tras el delete");

			List<Usuario> lista = usuarioDao.listAllUsuarios();
			int contador = this.desplegarUsuarios(lista);

			assertEquals(2, lista.size());
			logger.info("Fin - test testDeleteUsuario");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}	
	
}
