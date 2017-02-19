
<%@ include file="taglibs.jsp"%>
<title>Listado de Personas</title>
<%@ include file="logout.jsp" %>
<a href="detalle.do?metodo=accionAgregar">Agregar Persona</a>
<br />
<table border="1">
	<thead>
		<tr>
			<th><bean:message key="persona.idPersona" /></th>
			<th><bean:message key="persona.nombre" /></th>
			<th><bean:message key="persona.apellido" /></th>
			<th><bean:message key="persona.usuarios" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="persona" items="${personas}">
			<tr>
				<td><a
					href="detalle.do?metodo=accionEditar&idPersona=${persona.idPersona}">${persona.idPersona}</a></td>
				<td>${persona.nombre}</td>
				<td>${persona.apellido}</td>
				<td><c:forEach var="usuario" items="${persona.usuarios}">
					<p><a href="detalleUsuario.do?metodo=accionEditarUsuario&idUsuario=${usuario.idUsuario}">${usuario.username}</a></p>				
					</c:forEach>
					<p><a href="detalleUsuario.do?metodo=accionAgregarUsuario&idPersona=${persona.idPersona}">[+]</a></p>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
