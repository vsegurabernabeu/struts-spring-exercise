package mx.gm.com.capaservicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gm.com.capadatos.PersonaDao;
import mx.gm.com.capadatos.domain.Persona;

@Service("personaServicio")
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class PersonaServicioImpl implements PersonaServicio {

	@Autowired
	PersonaDao personaDao;
	
	public Persona obtienePersonaPorId(int idPersona){
		return personaDao.getPersonaById(idPersona);
	}

	public List<Persona> listadoPersonas(){
		return personaDao.listAllPersonas();
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void agregarPersona(Persona persona){
		personaDao.insertarPersona(persona);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void modificarPersona(Persona persona){
		personaDao.updatePersona(persona);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void borrarPersona(Persona persona){
		personaDao.deletePersona(persona);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void guardaPersona(Persona persona) {
		personaDao.inserta_O_updatePersona(persona);
	}
	
	public List<Persona> obtienePersonasPorNombre(String nombre){		
		return personaDao.getPersonasByNombre(nombre);
	}
}
