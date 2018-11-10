<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<c:out value="${schedulesByDays}" />
<c:forEach items="${schedules.entrySet()}" var="dayEntry">
	<c:set var="day" value="${dayEntry.getKey()}"/>
	<c:set var="schedules" value="${dayEntry.getValue()}"/>
	
	<table class="table table-sm table-striped">
		<thead>
			<tr>
				<th scope="col" colspan="8">${day['name']}</th>
			</tr>
			<tr>
				<th scope="col">#Пара</th>
				<th scope="col">Время</th>
				<th scope="col">Неделя</th>
				<th scope="col">Название</th>
				<th scope="col">Тип</th>
				<th scope="col">Преподаватель</th>
				<th scope="col">Аудитория</th>
				<th scope="col" colspan="2">Действия</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${schedules}" var="schedule">
				
				<tr data-schedule-id="${schedule['id']}">
					<th scope="row">${schedule['subject_num']}</th>
					<td>${bellSchedule[schedule['subject_num']]}</td>
					<td>${schedule['week']}</td>
					<td>${schedule['subject']['title']}</td>
					<td>${schedule['lessonType']}</td>
					<td>${schedule['teacher']['lastname']} ${schedule['teacher']['firstname'].charAt(0)}.${schedule['teacher']['middlename'].charAt(0)}.</td>
					<td>${schedule['classroom']}</td>
					<td class="schedule-actions">
						<button type="button" class="btn btn-primary edit-schedule-btn"><i class="fas fa-edit"></i></button>
						<button type="button" class="btn btn-danger delete-schedule-btn"><i class="far fa-trash-alt"></i></button>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8"><button type="button" class="btn btn-warning">Добавить</button></td>
			</tr>
		</tbody>
	</table>
</c:forEach>

<div class="modal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Редактор предмета</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body schedule-model-body">
        <form>
          <div class="form-group" id="subject">
            <label for="subject-names" class="col-form-label">Предмет:</label>
			<select class="custom-select" id="subject-names">
			</select>
          </div>
          <div class="form-group">
            <label for="subject-types" class="col-form-label">Тип предмета:</label>
            <select class="custom-select" id="subject-types">
            	<option value="0">Не назначено</option>
            	<option value="1">Лекция</option>
            	<option value="2">Практика</option>
			</select>
          </div>
          <div class="form-group">
          	<label for="teachers" class="col-form-label">Преподаватель:</label>
          	<select class="custom-select" id="teachers">
          	</select>
          </div>
          <div class="form-group">
          	<label for="classroom" class="col-form-label">Аудитория:</label>
          	<input type="text" class="form-control" id="classroom" value="">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отменить</button>
        <button type="button" class="btn btn-primary save-schedule">Сохранить</button>
      </div>
    </div>
  </div>
</div>

<jsp:include page="layouts/_footer.jsp"></jsp:include>