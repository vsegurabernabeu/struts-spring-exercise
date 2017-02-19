package mx.gm.com.capadatos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.gm.com.capadatos.domain.Persona;
import mx.gm.com.capadatos.domain.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario>{

	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(rs.getInt("id_usuario"));
		usuario.setUsername(rs.getString("username"));
		usuario.setPassword(rs.getString("password"));

		Persona persona = new Persona();
		persona.setIdPersona(rs.getInt("id_persona"));
		persona.setNombre(rs.getString("nombre"));
		persona.setApellido(rs.getString("apellido"));
		
		usuario.setPersona(persona);
		return usuario;
	}

}
