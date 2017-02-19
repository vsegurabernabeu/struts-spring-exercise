package mx.gm.com.capaweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import mx.gm.com.capadatos.domain.Persona;
import mx.gm.com.capaservicio.PersonaServicio;

public class PersonaAction extends DispatchAction {
	// Atributo que será inyectado por Spring
	private PersonaServicio personaService;

	public void setPersonaService(PersonaServicio personaService) {
		this.personaService = personaService;
	}

	private static Log logger = LogFactory.getLog("PersonaAction");

	public boolean compruebaSesion(HttpServletRequest req) {
		boolean r;

		logger.info("validamos sesion ...");
		HttpSession sesion = req.getSession();
		if (sesion.getAttribute("user") == null) {
			sesion.invalidate();
			r = false;
			logger.info("not ok!");
		} else {
			r = true;
			logger.info("ok!");
		}
		return r;
	}

	public ActionForward accionListar(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método listar");

		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}

		this.setListaPersonas(req);
		return mapping.findForward("listar");
	}

	private void setListaPersonas(HttpServletRequest req) {
		List<Persona> personas = this.personaService.listadoPersonas();
		// Compartimos la lista de objetos persona con el JSP
		req.setAttribute("personas", personas);
		// Desplegamos solo para validar
		for (Persona persona : personas) {
			logger.info("Persona:" + persona);
		}
	}

	public ActionForward accionAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Agregar");
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		// No hay parametro enviado, por que es una nueva persona
		return mapping.findForward("editar");
	}

	public ActionForward accionEditar(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Editar");
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		// Buscamos el parametro enviado
		String idPersonaS = req.getParameter("idPersona");

		if (idPersonaS != null) {
			int idPersona = Integer.parseInt(idPersonaS);
			Persona persona = this.personaService.obtienePersonaPorId(idPersona);
			// Compartimos el objeto encontrado
			DynaActionForm personaForm = (DynaActionForm) form;
			personaForm.set("persona", persona);
			return mapping.findForward("editar");
		} else {
			return mapping.findForward("listar");
		}
	}

	public ActionForward accionGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Guardar"); // Recuperamos la persona
													// editada
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		DynaActionForm userForm = (DynaActionForm) form;
		if (userForm != null) {
			Persona persona = (Persona) userForm.get("persona");

			logger.info("persona a guardar:" + persona);

			if (persona != null && "" != persona.getNombre() && "" != persona.getApellido()) {
				// hace insert y/o update
				this.personaService.guardaPersona(persona);
			}
		}
		// Volvemos a consultar la lista de personas
		this.setListaPersonas(req);
		return mapping.findForward("listar");
	}

	public ActionForward accionEliminar(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Eliminar");
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		// Buscamos el parametro enviado
		DynaActionForm userForm = (DynaActionForm) form;
		if (userForm != null) {
			Persona persona = (Persona) userForm.get("persona");
			if (persona != null && 0 != persona.getIdPersona()) {
				try {
					this.personaService.borrarPersona(persona);
				} catch (Exception e) {
					System.out.println("Excepción al elmininar, continua el flujo...");
					System.out.println(e);
				}
			}
		}
		// Volvemos a consultar la lista de personas
		this.setListaPersonas(req);
		return mapping.findForward("listar");
	}

}
