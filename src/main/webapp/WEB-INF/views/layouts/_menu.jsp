<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import = "com.vit.Schedule.service.impl.*" %>

<%
	request.setAttribute("schools", new SchoolServiceImpl().findAll());
%>

<ul class="menu">
	<li><a href="<c:url value="/"/>"><i class="fa fa-fw fa-home"></i> Главная</a></li>
	
	<c:forEach items="${schools}" var="school">
		<li><a href="${pageContext.request.contextPath}/school/${school.getUrl()}">${school.getTitle()}</a></li>
	</c:forEach>
</ul>