<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<c:if test="${courseToGroupsMap.isEmpty()}">
	<p>Нет данных о группах!</p>
</c:if>

<ul>
	<c:forEach items="${courseToGroupsMap.entrySet()}" var="courseToGroupEntry">
		<c:set var="courseNum" value="${courseToGroupEntry.getKey()}"/>
		<c:set var="groups" value="${courseToGroupEntry.getValue()}"/>
		
		<c:if test="${courseNum != null && groups != null}">
			<h3 style="margin-top: 1.1em">${courseNum} курс</h3>
			<c:forEach items="${groups}" var="group">
				<c:set var="scheduleLink" value="${pageContext.request.contextPath}/schedule/${group.getId()}"/>
				<li><a href="${scheduleLink}">${group.getTitle()}</a></li>	
			</c:forEach>
		</c:if>
	</c:forEach>
</ul>
<button id="show-add-group-modal" class="btn btn-success">Добавить группу</button>

<div class="modal" id="addGroupModal" tabindex="-1" role="dialog"
	aria-labelledby="addGroupModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="addGroupModalLabel">Добавление группы</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body group-model-body" data-major-id="${majorId}">
				<form>
					<div class="form-group">
						<label for="group-course-num" class="col-form-label">Курс:</label>
						<select class="custom-select" id="group-course-num">
							<option value="1" selected>1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
						</select>
					</div>
					<div class="form-group">
						<label for="group-title" class="col-form-label">Группа:</label>
						<input type="text" class="form-control" id="group-title">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Отменить</button>
				<button type="button" class="btn btn-primary add-group-btn">Добавить</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="layouts/_footer.jsp"></jsp:include>