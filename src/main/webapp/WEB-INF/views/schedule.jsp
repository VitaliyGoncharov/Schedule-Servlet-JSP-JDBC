<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<c:if test="${schedulesByDays.isEmpty()}">
	<div align="center" style="font-size: 1.2em"><b>Расписание отсутствует!</b></div>
</c:if>

<c:if test="${!schedulesByDays.isEmpty()}">
	<div class="schedule-container" style="position: absolute; left:50%; transform: translate(-50%,0)">
		<table border="1">
			<tbody>
				<c:forEach items="${schedulesByDays.entrySet()}" var="dayMap">
					<c:set var="dayName" value="${dayMap.getKey().getName()}"/>
					<c:set var="subjectsMap" value="${dayMap.getValue()}"/>
					<c:set var="firstWasFound" value="${false}"/>
					<c:set var="rowsForDay" value="${0}"/>
					
					<!-- Vars for identifying cell -->
					<c:set var="dayId" value="${dayMap.getKey().getId()}"/>
					
					<!-- Count how many rows will be reserved in a day to store all subjects -->
					<c:forEach items="${subjectsMap.entrySet()}" var="subjectsMapEntry">
						<c:set var="subjectMap" value="${subjectsMapEntry.getValue()}"/>
						<c:set var="upSubject" value="${subjectMap['up']['subject']}"/>
						<c:set var="downSubject" value="${subjectMap['down']['subject']}"/>
						
						<c:choose>
							<c:when test="${upSubject.equals(downSubject)}">
								<c:set var="rowsForDay" value="${rowsForDay+1}"/>
							</c:when>
							<c:otherwise>
								<c:set var="rowsForDay" value="${rowsForDay+2}"/>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<!-- Iterate over lessons numbers and get subjects related to lesson number -->
					<!-- bellSchedule keeps lesson and its period (Ex.: 1 => "8.30-10.00") -->
					<c:forEach items="${bellSchedule.entrySet()}" var="bellScheduleEntry">
						<c:set var="lessonNum" value="${bellScheduleEntry.getKey()}"/>
						<c:set var="lessonPeriod" value="${bellScheduleEntry.getValue()}"/>
						<c:set var="subjectMap" value="${subjectsMap[(lessonNum).intValue()]}"/>
						<c:set var="upSubjectMap" value="${subjectMap['up']}"/>
						<c:set var="downSubjectMap" value="${subjectMap['down']}"/>
						
						<c:set var="upSubject" value="${upSubjectMap['subject']}"/>
						<c:set var="downSubject" value="${downSubjectMap['subject']}"/>
						<c:set var="upSubjectTeacher" value="${upSubjectMap['teacher']}"/>
						<c:set var="downSubjectTeacher" value="${downSubjectMap['teacher']}"/>
						<c:set var="upLessonType" value="${upSubjectMap['lessonType']}"/>
						<c:set var="downLessonType" value="${downSubjectMap['lessonType']}"/>
						<c:set var="upSubjectClassroom" value="${upSubjectMap['classroom']}"/>
						<c:set var="downSubjectClassroom" value="${downSubjectMap['classroom']}"/>
						
						<!-- Prepare data for up and down cells -->
						<c:set var="upCell" value="${upSubject}<br>${upSubjectTeacher} ${upLessonType} ${upSubjectClassroom}"/>
						<c:set var="downCell" value="${downSubject}<br>${downSubjectTeacher} ${downLessonType} ${downSubjectClassroom}"/>
						
						<c:if test="${subjectMap != null}">
							<tr>
								<!-- If this is first iteration in a day then show dayName (Ex.:Monday) -->
								<c:if test="${!firstWasFound}">
									<td rowspan="${rowsForDay}">${dayName}</td>
									<c:set var="firstWasFound" value="true"/>
								</c:if>
								
								<!-- This is up subject -->
								<!-- If up subject equals to down subject then reserve only ONE row -->
								<!-- If up subject not equals to down subject then reserve TWO rows -->
								<c:choose>
									<c:when test="${upSubject.equals(downSubject)}">
										<td rowspan="1" align="center">${lessonPeriod}<br>${lessonNum}</td>
										<td data-day-id="${dayId}" data-lesson-num="${lessonNum}" data-week="up">${upCell}</td>
									</c:when>
									<c:when test="${upSubject != null}">
										<td rowspan="2" align="center">${lessonPeriod}<br>${lessonNum}</td>
										<td data-day-id="${dayId}" data-lesson-num="${lessonNum}" data-week="up">${upCell}</td>
									</c:when>
									<c:otherwise>
										<td rowspan="2" align="center">${lessonPeriod}<br>${lessonNum}</td>
										<td data-day-id="${dayId}" data-lesson-num="${lessonNum}" data-week="up">-</td>
									</c:otherwise>
								</c:choose>
							</tr>
							
							<!-- This is down subject -->
							<!-- This row will be showed only if up subject and down subjects are different -->
							<c:if test="${!upSubject.equals(downSubject)}">
								<tr>
									<c:choose>
										<c:when test="${downSubject != null}">
											<td data-day-id="${dayId}" data-lesson-num="${lessonNum}" data-week="down">${downCell}</td>	
										</c:when>
										<c:otherwise>
											<td data-day-id="${dayId}" data-lesson-num="${lessonNum}" data-week="up">-</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:if>
						</c:if>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
		
		<div style="position: absolute; top:0; left:102%;">
			<a href="#">Изменить</a>	
		</div>
	</div>
</c:if>

<div class="modal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Редактор предмета</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="subject-name" class="col-form-label">Предмет:</label>
            <input type="text" class="form-control" id="subject-name" value="Экономическая безопасность">
          </div>
          <div class="form-group">
            <label for="subject-type" class="col-form-label">Тип предмета:</label>
            <input type="text" class="form-control" id="subject-type" value="Лекция">
          </div>
          <div class="form-group">
          	<label for="teacher" class="col-form-label">Преподаватель:</label>
          	<input type="text" class="form-control" id="teacher" value="Ефремова П.В.">
          </div>
          <div class="form-group">
          	<label for="classroom" class="col-form-label">Аудитория:</label>
          	<input type="text" class="form-control" id="classroom" value="G501">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отменить</button>
        <button type="button" class="btn btn-danger" data-dismiss="modal">Удалить</button>
        <button type="button" class="btn btn-primary">Сохранить</button>
      </div>
    </div>
  </div>
</div>

<jsp:include page="layouts/_footer.jsp"></jsp:include>