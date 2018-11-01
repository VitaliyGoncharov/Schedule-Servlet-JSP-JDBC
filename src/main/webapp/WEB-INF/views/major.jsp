<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<ul>
	<c:forEach items="${courseToGroupsMap.entrySet()}" var="courseToGroupEntry">
		<c:set var="courseNum" value="${courseToGroupEntry.getKey()}"/>
		<c:set var="groups" value="${courseToGroupEntry.getValue()}"/>
		
		<c:if test="${courseNum != null && groups != null}">
			<h3>${courseNum} курс</h3>
			<c:forEach items="${groups}" var="group">
				<c:set var="scheduleLink" value="${pageContext.request.contextPath}/schedule/${group.getId()}"/>
				<li><a href="${scheduleLink}">${group.getTitle()}</a></li>	
			</c:forEach>
		</c:if>
	</c:forEach>
</ul>

<jsp:include page="layouts/_footer.jsp"></jsp:include>