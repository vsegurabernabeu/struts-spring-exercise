package mx.gm.com.capadatos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import mx.gm.com.capadatos.domain.Persona;

@Repository
public class PersonaDaoJdbcImpl implements PersonaDao {

	/*
	 * Ya no se utiliza el PersonaRowMapper porque utilizamos el PersonaExtractor para traernos además los datos de usuarios por cada Persona.
	 */
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final String SQL_INSERT = "INSERT INTO persona (nombre, apellido) VALUES (:nombre,:apellido)";
	private static final String SQL_UPDATE = "UPDATE persona SET nombre=:nombre, apellido=:apellido WHERE id_persona=:idPersona";
	private static final String SQL_DELETE = "DELETE FROM persona WHERE id_persona=:idPersona";

	public Persona getPersonaById(int idPersona) {
		Persona persona;

		List<Persona> listaPersonas = (List<Persona>) this.jdbcTemplate.query(
				"select persona.*,usuario.* from persona left join usuario on usuario.id_persona = persona.id_persona where persona.id_persona=?",
				new Object[] { idPersona }, new PersonaExtractor());

		/*
		 * try { persona = this.jdbcTemplate.
		 * queryForObject("select * from persona where id_persona=?", new
		 * Object[] { idPersona }, new PersonaRowMapper());
		 * 
		 * } catch (EmptyResultDataAccessException e) { persona = null; }
		 */

		if (listaPersonas.isEmpty()) {
			persona = null;
		} else {
			persona = listaPersonas.get(0);
		}

		return persona;
	}

	public List<Persona> listAllPersonas() {
		/*
		 * List<Persona> listaPersonas =
		 * (List<Persona>)this.jdbcTemplate.query("select * from persona", new
		 * PersonaRowMapper());
		 */
		List<Persona> listaPersonas = (List<Persona>) this.jdbcTemplate.query(
				"select persona.*,usuario.* from persona left join usuario on usuario.id_persona = persona.id_persona",
				new PersonaExtractor());

		return listaPersonas;

	}

	public void insertarPersona(Persona persona) {
		// SqlParameterSource namedParameters = new
		// BeanPropertySqlParameterSource(persona);

		MapSqlParameterSource source = new MapSqlParameterSource();

		source.addValue("idPersona", persona.getIdPersona()); //Este no se usa en la SQL
		source.addValue("nombre", persona.getNombre());
		source.addValue("apellido", persona.getApellido());
		
		SqlParameterSource namedParameters = source;

		this.namedParameterJdbcTemplate.update(SQL_INSERT, namedParameters);
	}

	public void updatePersona(Persona persona) {
		// SqlParameterSource namedParameters = new
		// BeanPropertySqlParameterSource(persona);

		MapSqlParameterSource source = new MapSqlParameterSource();

		source.addValue("idPersona", persona.getIdPersona());
		source.addValue("nombre", persona.getNombre());
		source.addValue("apellido", persona.getApellido());
		
		SqlParameterSource namedParameters = source;
		this.namedParameterJdbcTemplate.update(SQL_UPDATE, namedParameters);
	}

	public void deletePersona(Persona persona) {
		// SqlParameterSource namedParameters = new
		// BeanPropertySqlParameterSource(persona);

		MapSqlParameterSource source = new MapSqlParameterSource();

		source.addValue("idPersona", persona.getIdPersona());
		
		SqlParameterSource namedParameters = source;
		this.namedParameterJdbcTemplate.update(SQL_DELETE, namedParameters);
	}

	public void inserta_O_updatePersona(Persona persona) {
		if (this.getPersonaById(persona.getIdPersona()) == null) {
			this.insertarPersona(persona);
		} else {
			this.updatePersona(persona);
		}
	}

	public List<Persona> getPersonasByNombre(String nombre) {
		
		/*
		List<Persona> listaPersonas = (List<Persona>) this.jdbcTemplate
				.query("select * from persona where nombre LIKE ?", new PersonaRowMapper(), (String) nombre);
		*/
		
		List<Persona> listaPersonas = (List<Persona>) this.jdbcTemplate.query(
				"select persona.*,usuario.* from persona left join usuario on usuario.id_persona = persona.id_persona where persona.nombre LIKE ?",
				new Object[] { nombre }, new PersonaExtractor());
		
		return listaPersonas;
	}

}
