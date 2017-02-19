<%@ include file="taglibs.jsp"%>
<title>Detalle Persona</title>
<%@ include file="logout.jsp" %>
<p>Detalle de la Persona:</p>
<html:form action="/guardar" focus="persona.nombre">
	<!-- onsubmit="return validateUserForm(this)"> -->
	<input type="hidden" name="metodo" value="accionGuardar" />
	<html:hidden property="persona.idPersona" />
	<table>
		<tr>
			<th><bean:message key="persona.nombre" />:</th>
			<td><html:text property="persona.nombre" /></td>
		</tr>
		<tr>
			<th><bean:message key="persona.apellido" />:</th>
			<td><html:text property="persona.apellido" /></td>
		</tr>
		<tr>
			<td><html:submit styleClass="button">Guardar</html:submit> <c:if
					test="${not empty param.idPersona}">
					<html:submit styleClass="button"
						onclick="this.form.metodo.value='accionEliminar'">Eliminar</html:submit>
				</c:if></td>
		</tr>
	</table>
</html:form>

