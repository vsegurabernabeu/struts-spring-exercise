<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page session="true" %>

    Hello <c:out value="${sessionScope.user}"/>. You can <a href="Logout.do">Logout</a>
    <br/>