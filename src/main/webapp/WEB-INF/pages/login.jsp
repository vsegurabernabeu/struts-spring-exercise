<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
<title>Login Page</title>
</head>
<body>
    <div style="color:red">
    	<html:errors />
    </div>
    <html:form action="/Login" >
    <input type="hidden" name="metodo" value="accionListar" />
        User Name :<html:text name="LoginForm" property="userName" />
        Password  :<html:password name="LoginForm" property="password" />
        <html:submit value="Login" />
    </html:form>
</body>
</html>