<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<ul>
	<c:if test="${!majors.isEmpty()}">
		<c:forEach items="${majors}" var="major">
			<li><a href="${pageContext.request.contextPath}/school/${major.getSchool().getUrl()}/${major.getUrl()}">${major.getTitle()}</a></li>
		</c:forEach>
	</c:if>
</ul>

<jsp:include page="layouts/_footer.jsp"></jsp:include>

