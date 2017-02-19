package mx.gm.com.capaservicio;

import java.util.List;

import mx.gm.com.capadatos.domain.Persona;

public interface PersonaServicio {
	public Persona obtienePersonaPorId(int idPersona);

	public List<Persona> listadoPersonas();

	public void agregarPersona(Persona persona);

	public void modificarPersona(Persona persona);

	public void borrarPersona(Persona persona);
	
	public void guardaPersona(Persona persona);
	
	public List<Persona> obtienePersonasPorNombre(String nombre);
}
