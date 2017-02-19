package mx.gm.com.capaweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import mx.gm.com.capadatos.domain.Usuario;
import mx.gm.com.capaservicio.UsuarioServicio;

/**
 *
 * @author eswar@vaannila.com
 */
public class LoginAction extends org.apache.struts.action.Action {

	private static final Log logger = LogFactory.getLog("LoginAction");

	/* forward name="success" path="" */
	private final static String SUCCESS = "listar";
	private final static String FAILURE = "failure";

	// Atributo que será inyectado por Spring
	private UsuarioServicio usuarioServicio;

	public void setUsuarioServicio(UsuarioServicio usuarioService) {
		this.usuarioServicio = usuarioService;
	}

	/**
	 * This is the action called from the Struts framework.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession sesion;
		LoginForm loginForm = (LoginForm) form;

		Usuario usuario;

		if (this.usuarioServicio == null) {
			logger.info("Es null!!!!");
		}

		usuario = this.usuarioServicio.obtieneUsuarioPorUsername(loginForm.getUserName());

		if (usuario == null) {
			return mapping.findForward(FAILURE);
		} else {
			if (loginForm.getPassword().equals(usuario.getPassword())) {
				sesion = request.getSession();
				sesion.setAttribute("user", loginForm.getUserName());
				return mapping.findForward(SUCCESS);
			} else {
				return mapping.findForward(FAILURE);
			}
		}
	}
}