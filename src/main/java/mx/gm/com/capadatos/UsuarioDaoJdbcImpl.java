package mx.gm.com.capadatos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import mx.gm.com.capadatos.domain.Persona;
import mx.gm.com.capadatos.domain.Usuario;

@Repository
public class UsuarioDaoJdbcImpl implements UsuarioDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final String SQL_INSERT = "INSERT INTO usuario (id_persona,username,password) VALUES (:idPersona,:username,:password)";
	private static final String SQL_UPDATE = "UPDATE usuario SET id_persona=:idPersona, id_usuario=:idUsuario, username=:username, password=:password "
			+ "WHERE id_usuario=:idUsuario";
	private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario=:idUsuario";

	public Usuario getUsuarioById(int idUsuario) {
		Usuario usuario;

		try {
			usuario = this.jdbcTemplate.queryForObject(
					"select persona.*,usuario.* from usuario inner join persona on usuario.id_persona = persona.id_persona where usuario.id_usuario=?",
					new Object[] { idUsuario }, new UsuarioRowMapper());			
		} catch (EmptyResultDataAccessException e) {
			usuario = null;
		}
		return usuario;
	}

	public Usuario getUsuarioByUsername(String username) {
		Usuario usuario;

		try {
			usuario = this.jdbcTemplate.queryForObject(
					"select persona.*,usuario.* from usuario inner join persona on usuario.id_persona = persona.id_persona where usuario.username LIKE ?",
					new Object[] { username }, new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			usuario = null;
		}
		return usuario;
	}

	public List<Usuario> getUsuarioByPersonaId(int idPersona) {
		List<Usuario> listaUsuarios;

		try {
			listaUsuarios = (List<Usuario>) this.jdbcTemplate.query(
					"select persona.*,usuario.* from usuario inner join persona on usuario.id_persona = persona.id_persona where persona.id_persona=?",
					new Object[] { idPersona }, new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			listaUsuarios = null;
		}
		return listaUsuarios;
	}

	public List<Usuario> listAllUsuarios() {
		List<Usuario> listaUsuarios;
		try {
			listaUsuarios = (List<Usuario>) this.jdbcTemplate.query(
					"select persona.*,usuario.* from usuario inner join persona on usuario.id_persona = persona.id_persona",
					new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			listaUsuarios = null;
		}
		return listaUsuarios;
	}

	public void insertUsuario(Usuario usuario) {

		// SqlParameterSource namedParameters = new
		// BeanPropertySqlParameterSource(usuario);
		MapSqlParameterSource source = new MapSqlParameterSource();

		source.addValue("idUsuario", usuario.getIdUsuario()); //Este no se usa en la SQL
		source.addValue("username", usuario.getUsername());
		source.addValue("password", usuario.getPassword());
		source.addValue("idPersona", usuario.getPersona().getIdPersona());

		SqlParameterSource namedParameters = source;

		this.namedParameterJdbcTemplate.update(SQL_INSERT, namedParameters);

	}

	public void updateUsuario(Usuario usuario) {
		// SqlParameterSource namedParameters = new
		// BeanPropertySqlParameterSource(usuario);
		MapSqlParameterSource source = new MapSqlParameterSource();

		source.addValue("idUsuario", usuario.getIdUsuario());
		source.addValue("username", usuario.getUsername());
		source.addValue("password", usuario.getPassword());
		source.addValue("idPersona", usuario.getPersona().getIdPersona());

		SqlParameterSource namedParameters = source;

		this.namedParameterJdbcTemplate.update(SQL_UPDATE, namedParameters);

	}

	public void deleteUsuario(Usuario usuario) {
		// SqlParameterSource namedParameters = new
		// BeanPropertySqlParameterSource(usuario);
		MapSqlParameterSource source = new MapSqlParameterSource();

		source.addValue("idUsuario", usuario.getIdUsuario());
		
		SqlParameterSource namedParameters = source;
		
		this.namedParameterJdbcTemplate.update(SQL_DELETE, namedParameters);
	}

	
	public void inserta_O_updateUsuario(Usuario usuario) {
		if (this.getUsuarioById(usuario.getIdUsuario()) == null) {
			this.insertUsuario(usuario);
		} else {
			this.updateUsuario(usuario);
		}
	}
	
}
