<%@ include file="taglibs.jsp"%>
<title>Detalle Usuario</title>
<%@ include file="logout.jsp" %>
<p>Detalle del Usuario:</p>
<html:form action="/guardarUsuario?idPersona=${param.idPersona}" focus="usuario.username">
	<!-- onsubmit="return validateUserForm(this)"> -->
	<input type="hidden" name="metodo" value="accionGuardarUsuario" />
	<html:hidden property="usuario.idUsuario" />
	<table>
		<tr>
			<th><bean:message key="usuario.username" />:</th>
			<td><html:text property="usuario.username" /></td>
		</tr>
		<tr>
			<th><bean:message key="usuario.password" />:</th>
			<td><html:text property="usuario.password" /></td>
		</tr>
		<tr>
			<td>
				<html:submit styleClass="button">Guardar</html:submit>
				<c:if test="${not empty param.idUsuario}">
					<html:submit styleClass="button"
						onclick="this.form.metodo.value='accionEliminarUsuario'">Eliminar</html:submit>
				</c:if></td>
		</tr>
	</table>
</html:form>