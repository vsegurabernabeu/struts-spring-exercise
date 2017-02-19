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
import mx.gm.com.capadatos.domain.Usuario;
import mx.gm.com.capaservicio.PersonaServicio;
import mx.gm.com.capaservicio.UsuarioServicio;

public class UsuarioAction extends DispatchAction {

	static final Log logger = LogFactory.getLog("UsuarioAction");

	// Atributo que será inyectado por Spring
	private UsuarioServicio usuarioServicio;

	// Atributo que será inyectado por Spring
	private PersonaServicio personaServicio;

	public void setUsuarioServicio(UsuarioServicio usuarioService) {
		this.usuarioServicio = usuarioService;
	}

	public void setPersonaServicio(PersonaServicio personaServicio) {
		this.personaServicio = personaServicio;
	}

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
		
	public ActionForward accionEditarUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		logger.info("Ejecutando método Editar Usuario");
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		// Buscamos el parametro enviado
		String idUsuarioS = req.getParameter("idUsuario");

		if (idUsuarioS != null) {
			int idUsuario = Integer.parseInt(idUsuarioS);
			Usuario usuario = this.usuarioServicio.obtieneUsuarioPorId(idUsuario);

			logger.info("usuario:" + usuario);

			// Compartimos el objeto encontrado
			DynaActionForm usuarioForm = (DynaActionForm) form;
			usuarioForm.set("usuario", usuario);

			logger.info("lo que le hemos puesto al formulario:->" + usuarioForm.getMap());

			return mapping.findForward("editarUsuario");
		} else {
			return mapping.findForward("listar");
		}
	}

	public ActionForward accionGuardarUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Guardar Usuario"); // Recuperamos la
															// persona
		// editada
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		
		DynaActionForm userForm = (DynaActionForm) form;

		logger.info("llegada->" + userForm.getMap());

		if (userForm != null) {

			Usuario usuario = (Usuario) userForm.get("usuario");

			/*
			 * Como no he encontrado una forma de hacer equivalente el objeto
			 * usuario con el form, que incluya también en la operación el
			 * objeto interno Persona, recupero la persona por el id de usuario
			 * y lo relleno aquí. Es un poco ñapa (mezclas conocimiento del
			 * modelo con la capa de presentación), pero es que no tengo tiempo
			 * de arreglarlo mejor porque de todas formas struts ya no se
			 * utiliza, tengo que ponerme con spring mvc, y de todas formas para
			 * este proyecto lo que importaba era spring, ... en fin, por una
			 * serie de cosas. Así se queda.
			 */

			String idPersona = req.getParameter("idPersona");

			if (idPersona == null || idPersona == "") {

				// Estamos editando el usuario, no nos viene el idPersona por la
				// url
				Usuario tmp = this.usuarioServicio.obtieneUsuarioPorId(usuario.getIdUsuario());
				usuario.setPersona(tmp.getPersona());

			} else {
				//Estamos agregando
				usuario.setPersona(new Persona(new Integer(idPersona).intValue()));				
			}

			logger.info("usuario:" + usuario);

			if (usuario != null && "" != usuario.getUsername() && "" != usuario.getPassword()) {
				// hace insert y/o update
				this.usuarioServicio.guardarUsuario(usuario);
				logger.info("Guardamos!");
			}
		}
		// Volvemos a consultar la lista de personas
		this.setListaPersonas(req);
		return mapping.findForward("listar");
	}

	private void setListaPersonas(HttpServletRequest req) {
		List<Persona> personas = this.personaServicio.listadoPersonas();
		// Compartimos la lista de objetos persona con el JSP
		req.setAttribute("personas", personas);
		// Desplegamos solo para validar
		for (Persona persona : personas) {
			logger.info("Persona:" + persona);
		}
	}

	public ActionForward accionAgregarUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Agregar Usuario");
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		// No hay parametro enviado, por que es una nueva persona
		return mapping.findForward("editarUsuario");
	}

	public ActionForward accionEliminarUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		logger.info("Ejecutando método Eliminar Usuario");
		if (!compruebaSesion(req)) {
			return mapping.findForward("login");
		}
		// Buscamos el parametro enviado
		DynaActionForm userForm = (DynaActionForm) form;
		if (userForm != null) {

			Usuario usuario = (Usuario) userForm.get("usuario");
			if (usuario != null /* && 0 != usuario.getIdUsuario() */) {
				try {
					this.usuarioServicio.borrarUsuario(usuario);
				} catch (Exception e) {
					System.out.println("Excepción al eliminar Usuario, continua el flujo...");
					System.out.println(e);
				}
			}
		}
		// Volvemos a consultar la lista de personas
		this.setListaPersonas(req);
		return mapping.findForward("listar");
	}

}
