package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:datasource-test.xml")
public class TestJdbcBasico {

	private static Log logger = LogFactory.getLog("TestJdbcBasico");

	//Aqui el autowired inyecta el datasource en el constructor? sin el <context:component-scan package="..." ?? />
	//POR QUÉ FUNCIONA ESTO????
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void testJdbcBasico() {
		System.out.println();
		logger.info("Inicio del test testJdbcBasico");

		int noPersonas = jdbcTemplate.queryForObject("select count(*) from persona", Integer.class);

		logger.info("noPersonas: " + noPersonas);
		assertEquals(3, noPersonas);

		int noUsuarios = jdbcTemplate.queryForObject("select count(*) from usuario", Integer.class);

		logger.info("noUsuarios: " + noUsuarios);
		assertEquals(3, noUsuarios);

		logger.info("Comprobamos que la clave ajena está bien definida ...");

		List<String> strings = (List<String>) jdbcTemplate.queryForList(
				"select u.username from usuario u,persona p where p.id_persona=u.id_persona and u.id_persona=1", String.class);

		for(String s:strings){
			System.out.println("Resultado:"+s);
		}
		
		logger.info("Fin del test testJdbcBasico");
		System.out.println();
	}
}
