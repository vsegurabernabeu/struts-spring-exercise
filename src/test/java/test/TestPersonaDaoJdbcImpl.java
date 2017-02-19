package test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gm.com.capadatos.PersonaDao;
import mx.gm.com.capadatos.domain.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml", "classpath:applicationContext.xml" })
public class TestPersonaDaoJdbcImpl {

	static final Log logger = LogFactory.getLog("TestPersonaDaoJdbc");

	@Autowired
	private PersonaDao personaDao;

	@Test
	@Transactional
	public void testlistAllPersonas() {
		try {
			System.out.println();
			logger.info("Inicio del test testlistAllPersonas");

			int contPersonas = desplegarPersonas();
			assertEquals(3, contPersonas);

			logger.info("Fin del test testlistAllPersonas");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	private int desplegarPersonas() {
		List<Persona> listaPersonas = personaDao.listAllPersonas();

		int contPersonas = 0;
		for (Persona p : listaPersonas) {
			logger.info("Persona :" + p);
			contPersonas++;
		}
		logger.info("Hay " + contPersonas + " personas.");
		return contPersonas;
	}

	@Test
	public void testGetPersonaById() {
		try {
			System.out.println();
			logger.info("Inicio del test testGetPersonaById");

			int idPersona = 1;
			Persona persona = personaDao.getPersonaById(idPersona);

			logger.info("Persona recuperada por id:" + idPersona + "=" + persona);

			assertEquals(persona.getNombre(), "Pepe");
			assertEquals(persona.getApellido(), "López");

			logger.info("Fin del test testGetPersonaById");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	@Test
	@Transactional
	public void testInsertPersona() {
		try {
			System.out.println();
			logger.info("Inicio del test testInsertPersona");

			Persona persona = new Persona();
			persona.setNombre("Alicia");
			persona.setApellido("Pardo");

			personaDao.insertarPersona(persona);

			// Comprobamos que se ha insertado correctamente
			int contPersonas = desplegarPersonas();
			assertEquals(4, contPersonas);

			// Comprobamos que son los datos correctos
			List<Persona> lista = personaDao.getPersonasByNombre("Alicia");
			assertTrue(lista.size() == 1);
			assertEquals(lista.get(0).getNombre(), "Alicia");
			logger.info("Persona recuperada: " + lista.get(0));

			logger.info("Fin del test testInsertPersona");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	@Test
	@Transactional
	public void testUpdatePersona() {
		try {
			System.out.println();
			logger.info("Inicio del test testUpdatePersona");

			Persona persona = new Persona();
			persona.setIdPersona(3);
			persona.setNombre("Luisa");
			persona.setApellido("Tormo");

			personaDao.updatePersona(persona);

			// Comprobamos que se ha insertado correctamente
			int contPersonas = desplegarPersonas();
			assertEquals(3, contPersonas);

			// Comprobamos que son los datos correctos
			Persona persona2 = personaDao.getPersonaById(3);
			assertEquals(persona2.getNombre(), "Luisa");
			assertEquals(persona2.getApellido(), "Tormo");

			logger.info("Fin del test testUpdatePersona");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	@Test
	@Transactional
	public void testDeletePersona() {
		try {
			System.out.println();
			logger.info("Inicio del test testDeletePersona");

			personaDao.deletePersona(new Persona(2));
			Persona persona = personaDao.getPersonaById(2);
			assertNull(persona);

			// Comprobamos que se ha borrado correctamente
			int contPersonas = desplegarPersonas();
			assertEquals(2, contPersonas);

			logger.info("Fin del test testDeletePersona");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	@Test
	@Transactional
	public void testInsert_O_Update() {
		try {
			System.out.println();
			logger.info("Inicio del test testInsert_O_Update");

			Persona persona = new Persona();
			persona.setIdPersona(1);
			persona.setNombre("Irene");
			personaDao.inserta_O_updatePersona(persona);

			// Comprobamos que se ha actualizado
			Persona p = personaDao.getPersonaById(1);
			assertEquals(p.getNombre(), "Irene");

			// Desplegamos la lista de personas
			int contPersonas = desplegarPersonas();
			assertEquals(3, contPersonas);

			// Cambiamos el id para hacer un insertar
			persona.setIdPersona(-1);
			persona.setNombre("Irene");
			personaDao.inserta_O_updatePersona(persona);

			// Desplegamos la lista de personas
			contPersonas = desplegarPersonas();
			assertEquals(4, contPersonas);

			logger.info("Fin del test testInsert_O_Update");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}
	}

	@Test
	@Transactional
	public void testDeberiaDevolverListaDePersonasConNombrePepe() {
		System.out.println();
		logger.info("Inicio del test testDeberiaDevolverListaDePersonasConNombrePepe");

		try {
			// Creamos otra persona con nombre Pepe
			Persona persona = new Persona();
			persona.setIdPersona(2);
			persona.setNombre("Pepe");
			personaDao.inserta_O_updatePersona(persona);

			// Desplegamos la lista de personas
			int contPersonas = desplegarPersonas();
			assertEquals(3, contPersonas);

			// Obtenemos todos los que se llamen Pepe
			List<Persona> lista = null;
			lista = personaDao.getPersonasByNombre("Pepe");

			assertTrue(lista.size() == 2);
			assertEquals(lista.get(0).getNombre(), "Pepe");
			assertEquals(lista.get(1).getNombre(), "Pepe");
			
			logger.info("recuperadas: "+lista);
			
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}

		logger.info("Fin del test testDeberiaDevolverListaDePersonasConNombrePepe");
		System.out.println();
	}

}
