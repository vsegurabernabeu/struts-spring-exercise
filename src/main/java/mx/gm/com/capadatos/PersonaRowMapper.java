package mx.gm.com.capadatos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.gm.com.capadatos.domain.Persona;

public class PersonaRowMapper implements RowMapper<Persona>{

	public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Persona persona = new Persona();
		persona.setIdPersona(rs.getInt("id_persona"));
		persona.setNombre(rs.getString("nombre"));
		persona.setApellido(rs.getString("apellido"));
		return persona;
	}

}
