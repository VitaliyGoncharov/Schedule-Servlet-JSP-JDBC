<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="layouts/_header.jsp"></jsp:include>

<c:if test="${schedulesByDays.isEmpty()}">
	<div align="center" style="font-size: 1.2em">
		<b>Расписание отсутствует!</b><br>
		<a href="<c:url value="/schedule/edit/${groupId}"/>">Добавить</a>
	</div>
</c:if>

<c:if test="${!schedulesByDays.isEmpty()}">
	<div id="schedule-container">
		<div id="schedule">
			<div style="position: absolute; top:0; left:102%;">
				<a href="<c:url value="/schedule/edit/${groupId}"/>">Изменить</a>
			</div>
			<table border="1" data-group-id="${groupId}">
				<tbody>
					<c:forEach items="${schedulesByDays.entrySet()}" var="dayMap">
						<c:set var="dayName" value="${dayMap.getKey().getName()}"/>
						<c:set var="subjectsMap" value="${dayMap.getValue()}"/>
						<c:set var="firstWasFound" value="${false}"/>
						<c:set var="rowsForDay" value="${0}"/>
						
						<!-- Vars for identifying cell -->
						<c:set var="dayNum" value="${dayMap.getKey().getOrderNum()}"/>
						
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
							<c:set var="subjectMap" value="${subjectsMap[lessonNum]}"/>
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
							
							<c:set var="upScheduleId" value="${upSubjectMap['scheduleId']}"/>
							<c:set var="downScheduleId" value="${downSubjectMap['scheduleId']}"/>
							
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
											<td data-schedule-id="${upScheduleId}">${upCell}</td>
										</c:when>
										<c:when test="${upSubject != null}">
											<td rowspan="2" align="center">${lessonPeriod}<br>${lessonNum}</td>
											<td data-schedule-id="${upScheduleId}">${upCell}</td>
										</c:when>
										<c:otherwise>
											<td rowspan="2" align="center">${lessonPeriod}<br>${lessonNum}</td>
											<td data-day-num="${dayNum}" data-lesson-num="${lessonNum}" data-week="up">-</td>
										</c:otherwise>
									</c:choose>
								</tr>
								
								<!-- This is down subject -->
								<!-- This row will be showed only if up subject and down subjects are different -->
								<c:if test="${!upSubject.equals(downSubject)}">
									<tr>
										<c:choose>
											<c:when test="${downSubject != null}">
												<td data-schedule-id="${downScheduleId}">${downCell}</td>	
											</c:when>
											<c:otherwise>
												<td data-schedule-id="${downScheduleId}">-</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:if>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</c:if>

<jsp:include page="layouts/_footer.jsp"></jsp:include>