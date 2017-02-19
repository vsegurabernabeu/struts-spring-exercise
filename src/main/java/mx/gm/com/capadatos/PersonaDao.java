package mx.gm.com.capadatos;

import java.util.List;

import mx.gm.com.capadatos.domain.Persona;

public interface PersonaDao {

	public Persona getPersonaById(int idPersona);
	
	public List<Persona> listAllPersonas();
	
	public void insertarPersona(Persona persona);
	
	public void updatePersona(Persona persona);
	
	public void deletePersona(Persona persona);
	
	public void inserta_O_updatePersona(Persona persona);
	
	public List<Persona> getPersonasByNombre(String nombre);
}
