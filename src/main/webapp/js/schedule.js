$(".edit-schedule-btn").on('click', function(e) {
	let target = e.target;
	let row = target.closest('tr');
	
	let scheduleId = row.dataset.scheduleId;
	
	let scheduleModelBody = $('.schedule-model-body');
	
	getSchedule(scheduleId)
		.then(
			schedule => {
				scheduleModelBody.attr('data-schedule-id', schedule.id);
				
				let lessonType;
				const NOT_STATED = 0;
				const LECTION = 1;
				const PRACTISE = 2;
				if (schedule.lessonType == null) lessonType = NOT_STATED;
				if (schedule.lessonType == 'Л') lessonType = LECTION;
				if (schedule.lessonType == 'П') lessonType = PRACTISE;
				
				$('#subject-types').val(lessonType);
				$('#classroom').val(schedule.classroom);
				
				Promise.all([
					insertSubjects(schedule.subject.id, "#subject-names"),
					insertTeachers(schedule.teacher.id, "#teachers")
				]).then(
					result => {
						$("#editScheduleModal").modal('show');
					},
					error => {
						console.err(error);
					});
			},
			error => {
				
			});
});

function getSchedule(id) {
	return new Promise((res, rej) => {
		$.ajax({
			type: 'GET',
			url: '/Schedule/api/schedule/info',
			data: {
				id: id
			},
			dataType: 'json',
			success: function(data) {
				return res(data);
			}
		});
	});
}

function sortSubjects(subjects) {
	return subjects.sort(function(a, b) {
		let title1 = a.title.toLowerCase(), title2 = b.title.toLowerCase();
		if (title1 < title2)
			return -1;
		if (title1 > title2)
			return 1;
		return 0;
	});
}
function sortTeachers(teachers) {
	return teachers.sort(function(a, b) {
		let teacher1 = a.lastname.toLowerCase(), teacher2 = b.lastname.toLowerCase();
		if (teacher1 < teacher2)
			return -1;
		if (teacher1 > teacher2)
			return 1;
		return 0;
	});
}

function insertSubjects(currentSubjectId, targetElem) {
	return new Promise(function(res,rej) {
		getSubjects()
			.then(
				subjects => {
					subjects = sortSubjects(subjects);
					for (let subject of subjects) {
						let preparedSubject = '<option value="' + subject.id + '"';
						if (subject.id == currentSubjectId) {
							preparedSubject += ' selected';
						}
						preparedSubject += '>' + subject.title + '</option';
						$(targetElem).append(preparedSubject);
					}
					return res();
				},
				error => {
					console.err(error)
				});
	});
}

function getSubjects(subjects) {
	return new Promise(function(res,rej) {
		$.ajax({
			type: 'GET',
			url: '/Schedule/api/subjects',
			dataType: 'json',
			success: function(subjects) {
				return res(subjects);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 500) {
					return rej("getSubjects() Internal error | Couldn't get subjects");
				} else {
					return rej("getSubjects() Unexpected error")
				}
			}
		})
	});
}

function insertTeachers(currentTeacherId, targetElem) {
	return new Promise(function(res, rej) {
		getTeachers()
			.then(
				teachers => {
					teachers = sortTeachers(teachers);
					if (currentTeacherId == 0) {
						let emptyOption = '<option value="0" selected>Выбрать преподавателя</option>';
						$(targetElem).append(emptyOption);
					} else {
						let emptyOption = '<option value="0">Выбрать преподавателя</option>';
						$(targetElem).append(emptyOption);
					}
					for (let teacher of teachers) {
						let preparedTeacher = '<option value="' + teacher.id + '"';
						if (teacher.id == currentTeacherId) {
							preparedTeacher += ' selected';
						}
						preparedTeacher += '>' + teacher.lastname + ' ' + teacher.firstname + ' ' + teacher.middlename;
						preparedTeacher += '</option>';
						$(targetElem).append(preparedTeacher);
					}
					
					return res();
				},
				error => {
					console.err(error);
					return rej();
				});
	});
}

function getTeachers() {
	return new Promise(function(res, rej) {
		$.ajax({
			type: 'GET',
			url: '/Schedule/api/teachers',
			dataType: 'json',
			success: function(teachers) {
				return res(teachers);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 500) {
					return rej("getTeachers() Internal error | Couldn't get teachers");
				} else {
					return rej("getTeachers() Unexpected error")
				}
			}
		})
	});
}

$(".modal").on('hidden.bs.modal', function(e) {
	$("#subject-names").empty();
	$("#teachers").empty();
	$("#subject-types option").prop('selected','selected');
	$("#subject-types option").removeAttr("selected");
});

$(".save-schedule").on('click', function (e) {
	let scheduleModelBody = $('.schedule-model-body');
	let scheduleId = scheduleModelBody.attr('data-schedule-id');
	let subjectId = $('#subject-names').val();
	let lessonType = $('#subject-types').val();
	let teacherId = $('#teachers').val();
	let classroom = $('#classroom').val();
	
	
	let scheduleObj = { 
		scheduleId: scheduleId,
		subjectId: subjectId,
		lessonType: lessonType,
		teacherId: teacherId,
		classroom: classroom
	};
	
	editScheduleReq(scheduleObj)
});

function editScheduleReq(scheduleObj) {
	return new Promise((res,rej) => {
		$.ajax({
			type: 'POST',
			url: '/Schedule/api/schedule/edit',
			data: scheduleObj,
			dataType: 'json',
			success: function(data) {
				console.log(data);
				$(".modal").modal('hide');
				return res();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 500) {
					return rej("editScheduleReq() Internal error | Couldn't edit schedule");
				} else {
					return rej("editScheduleReq() Unexpected error");
				}
			}
		});
	});
}

$(".delete-schedule-btn").on('click', function(e) {
	let target = e.target;
	let row = target.closest("tr");
	
	let scheduleId = row.dataset.scheduleId;
	deleteScheduleReq(scheduleId)
		.then(
			response => {
				console.log(response);
			},
			error => {
				console.err(error);
			});
});

function deleteScheduleReq(id) {
	return new Promise((res, rej) => {
		$.ajax({
			type: 'POST',
			url: '/Schedule/api/schedule/delete',
			data: {id: id},
			dataType: 'json',
			success: function(response) {
				return res(response);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR == 500) {
					return rej("deleteScheduleReq() Internal error | Couldn't delete schedule");
				} else {
					return rej("deleteSchedulReq() Unexpected error");
				}
			}
		});
	});
}

$(".show-add-schedule-form").on('click', function(e) {
	let target = e.target;
	$('#add-schedule_subject-types').val(0);
	let dayNum = target.closest("table").dataset.dayNum;
	$(".addScheduleModelBody").attr('data-day-num', dayNum);
	
	Promise.all([
		insertSubjects(0, "#add-schedule_subject-names"),
		insertTeachers(0, "#add-schedule_teachers")
	]).then(
		result => {
			$("#addScheduleModal").modal('show');
		},
		error => {
			console.err(error);
		});
});

$(".add-schedule-btn").on('click', function(e) {
	let target = e.target;
	let dayNum = $(".addScheduleModelBody").attr("data-day-num");
	let groupId = $(".addScheduleModelBody").attr("data-group-id");
	let week = $("#add-schedule_week-type").val();
	let lessonNum = $("#add-schedule_lesson-order-number").val();
	let subject = $("#add-schedule_subject-names").val();
	let lessonType = $("#add-schedule_subject-types").val();
	let teacher = $("#add-schedule_teachers").val();
	let classroom = $("#add-schedule_classroom").val();
	
	let obj = {
		group: groupId,
		day: dayNum,
		week: week,
		lessonNum: lessonNum,
		subject: subject,
		lessonType: lessonType,
		teacher: teacher,
		classroom: classroom
	};
	
	addScheduleReq(obj)
		.then(
			success => {
				console.log("Success");
				$("#addScheduleModal").modal('hide');
			},
			error => {
				console.error(error);
			});
});

function addScheduleReq(obj) {
	return new Promise((res,rej) => {
		$.ajax({
			type: 'POST',
			url: '/Schedule/api/schedule/add',
			data: obj,
			dataType: 'json',
			success: function(response) {
				return res(response);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 500) {
					return rej("addScheduleReq(obj) Internal error | Couldn't add schedule!");
				} else {
					return rej("addScheduleReq(obj) Unexpected error");
				}
			}
		});
	});
}