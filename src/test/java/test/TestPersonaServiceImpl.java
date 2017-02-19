package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gm.com.capadatos.PersonaDao;
import mx.gm.com.capadatos.domain.Persona;
import mx.gm.com.capaservicio.PersonaServicio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:datasource-test.xml" })
public class TestPersonaServiceImpl {

	static final Log logger = LogFactory.getLog("TestPersonaServiceImpl");

	@Autowired
	PersonaServicio personaServicio;

	@Autowired
	PersonaDao personaDao;

	@Test
	public void testListadoPersonas() {
		System.out.println();
		logger.info("Inicio del test testListadoPersonas");
		try {
			List<Persona> lista = personaServicio.listadoPersonas();

			int contPersonas = desplegarPersonas();
			assertEquals(lista.size(), contPersonas);
			assertEquals(lista.size(), 3);
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testListadoPersonas");
		System.out.println();
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
	public void testObtienePersonaPorId() {
		System.out.println();
		logger.info("Inicio del test testObtienePersonaPorId");
		try {
			int idPersona = 1;
			Persona persona = personaServicio.obtienePersonaPorId(idPersona);

			logger.info("Persona recuperada por id:" + idPersona + "=" + persona);

			assertEquals(persona.getNombre(), "Pepe");
			assertEquals(persona.getApellido(), "López");
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testObtienePersonaPorId");
		System.out.println();
	}

	@Test
	@Transactional
	public void testOperacionesPersona() {

		try {
			System.out.println();
			logger.info("Inicio del test testOperacionesPersona");

			Persona persona = new Persona();
			persona.setNombre("Alicia");
			persona.setApellido("Pardo");

			// Insertamos -------------------------------------------------
			personaServicio.agregarPersona(persona);

			// Comprobamos que se ha insertado correctamente
			int contPersonas = desplegarPersonas();
			assertEquals(4, contPersonas);

			// Comprobamos que son los datos correctos
			List<Persona> lista = null;
			lista = personaServicio.obtienePersonasPorNombre("Alicia");

			assertTrue(lista.size() == 1);
			assertEquals(lista.get(0).getNombre(),"Alicia");
			logger.info("Persona insertada: "+lista.get(0));
			
			
			// Modificamos -------------------------------------------------
			lista.get(0).setNombre("Flavio");
			lista.get(0).setApellido("Carioca");
			personaServicio.modificarPersona(lista.get(0));

			// Comprobamos que son los datos correctos
			List<Persona> lista2 = null;
			lista2 = personaServicio.obtienePersonasPorNombre("Flavio");

			assertTrue(lista2.size() == 1);
			assertEquals(lista2.get(0).getNombre(),"Flavio");
			logger.info("Persona modificada con nombre:Flavio = "+lista2.get(0));

			// Borramos ----------------------------------------------------
			personaServicio.borrarPersona(lista2.get(0));
			
			List<Persona> lista3 = personaServicio.obtienePersonasPorNombre("Flavio");
			assertTrue(lista3.size()==0);

			logger.info("Persona borrada con nombre:Flavio");
			
			// Comprobamos que se ha insertado correctamente
			contPersonas = desplegarPersonas();
			assertEquals(3, contPersonas);
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
		logger.info("Fin del test testOperacionesPersona");
		System.out.println();
	}
	
	@Test
	@Transactional
	public void testInsert_O_UpdatePersona(){
		System.out.println();
		logger.info("Inicio del test testInsert_O_UpdatePersona");
		Persona persona = new Persona();
		persona.setIdPersona(1);
		persona.setNombre("Irene");
		personaServicio.guardaPersona(persona);

		// Comprobamos que se ha actualizado
		Persona p = personaDao.getPersonaById(1);
		assertEquals(p.getNombre(), "Irene");

		// Desplegamos la lista de personas
		int contPersonas = desplegarPersonas();
		assertEquals(3, contPersonas);

		// Cambiamos el id para hacer un insertar
		persona.setIdPersona(-1);
		persona.setNombre("Irene");
		personaServicio.guardaPersona(persona);

		// Desplegamos la lista de personas
		contPersonas = desplegarPersonas();
		assertEquals(4, contPersonas);
		
		logger.info("Fin del test testInsert_O_UpdatePersona");
		System.out.println();
	}
	
	@Test
	@Transactional
	public void testDeberiaDevolverListaDePersonasConNombrePepe() {
		System.out.println();
		logger.info("Inicio del test testDeberiaDevolverListaDePersonasConNombrePepe");

		try {
			List<Persona> lista = null;
			lista = personaServicio.obtienePersonasPorNombre("Pepe");

			assertTrue(lista.size() == 1);
			assertEquals(lista.get(0).getNombre(),"Pepe");
			logger.info("Persona recuperada: "+lista.get(0));
			
		} catch (Exception e) {
			logger.error("Error JDBC", e);
		}

		logger.info("Fin del test testDeberiaDevolverListaDePersonasConNombrePepe");
		System.out.println();

	}
	
	

}
