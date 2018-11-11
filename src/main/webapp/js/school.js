$("#show-add-major-model-btn").on('click', function(e) {
	$("#addMajorModal").modal('show');
});

$(".add-major-btn").on('click', function(e) {
	let target = e.target;
	let schoolId = $(".major-model-body").attr("data-school-id");
	let major = $("#major-title").val();
	let duration = $("#major-duration").val();
	
	let obj = {
		school: schoolId,
		major: major,
		duration: duration
	};
	
	addMajorReq(obj)
		.then(
			success => {
				$("#addMajorModal").modal('hide');
				console.log(success);
			},
			error => {
				console.error(error);
			});
});

function addMajorReq(obj) {
	return new Promise((res,rej) => {
		$.ajax({
			type: 'POST',
			url: '/Schedule/api/major/add',
			data: obj,
			dataType: 'json',
			success: function(response) {
				return res(response);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 500) {
					return rej("addMajorReq(obj) Internal error | Couldn't add major");
				} else {
					return rej("addMajorReq(obj) Unexpected error!");
				}
			}
		});
	});
}