package mx.gm.com.capadatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import mx.gm.com.capadatos.domain.Persona;
import mx.gm.com.capadatos.domain.Usuario;

class PersonaExtractor implements ResultSetExtractor<ArrayList<Persona>> {

	public ArrayList<Persona> extractData(ResultSet rs) throws SQLException, DataAccessException {

		Map<Integer, Persona> map = new HashMap<Integer, Persona>();

		while (rs.next()) {

			Integer id = rs.getInt("id_persona");
			Persona persona = map.get(id);

			if (persona == null) {
				persona = new Persona();
				persona.setIdPersona(id.intValue());
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				map.put(id, persona);
			}

			List<Usuario> usuariosList = persona.getUsuarios();
			if (usuariosList == null) {
				usuariosList = new ArrayList<Usuario>();
				persona.setUsuarios(usuariosList);
			}
			
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(rs.getInt("id_usuario"));
			usuario.setUsername(rs.getString("username"));
			usuario.setPassword(rs.getString("password"));
			usuariosList.add(usuario);
		}
		return new ArrayList<Persona>(map.values());
	}
}