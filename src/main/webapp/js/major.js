$("#show-add-group-modal").on('click', function(e) {
	$("#addGroupModal").modal('show');
});

$(".add-group-btn").on('click', function(e) {
	let target = e.target;
	let majorId = $(".group-model-body").attr("data-major-id");
	let groupTitle = $("#group-title").val();
	let courseNum = $("#group-course-num").val();
	
	let obj = {
		groupTitle: groupTitle,
		courseNum: courseNum,
		majorId: majorId
	};
	
	addGroupReq(obj)
		.then(
			success => {
				console.log(success);
				$("#addGroupModal").modal('hide');				
			},
			error => {
				console.error(error);
			});
});

function addGroupReq(obj) {
	return new Promise((res,rej) => {
		$.ajax({
			type: 'POST',
			url: '/Schedule/api/group/add',
			data: obj,
			dataType: 'json',
			success: function(response) {
				return res(response);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 500) {
					return rej("addGroupReq(obj) Internal error | Couldn't add group!");
				} else {
					return rej("addGroupReq(obj) Unexpected error");
				}
			}
		});
	});
}