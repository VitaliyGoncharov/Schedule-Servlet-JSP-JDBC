<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<ul>
	<c:if test="${majors.isEmpty()}">
		<p align="left">Нет данных о направлениях подготовки!</p>
	</c:if>
	<c:if test="${!majors.isEmpty()}">
		<c:forEach items="${majors}" var="major">
			<li><a href="${pageContext.request.contextPath}/school/${major.getSchool().getUrl()}/${major.getUrl()}">${major.getTitle()}</a></li>
		</c:forEach>
	</c:if>
</ul>
<button id="show-add-major-modal-btn" class="btn btn-success">Добавить специальность</button>

<div class="modal" id="addMajorModal" tabindex="-1" role="dialog"
	aria-labelledby="addMajorModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="addMajorModalLabel">Добавление специальности</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body major-model-body" data-school-id="${schoolId}">
				<form>
					<div class="form-group">
						<label for="major-title" class="col-form-label">Специальность:</label>
						<input type="text" class="form-control" id="major-title">
					</div>
					<div class="form-group">
						<label for="major-duration" class="col-form-label">Длительность обучения:</label>
						<select class="custom-select" id="major-duration">
							<option value="1" selected>1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
						</select>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Отменить</button>
				<button type="button" class="btn btn-primary add-major-btn">Добавить</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="layouts/_footer.jsp"></jsp:include>

