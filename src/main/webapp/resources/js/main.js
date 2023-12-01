window.onload = function() {
	setInitialImage();
};

function validatePhoneNumber() {
	const input = document.getElementById('phone');
	const value = input.value.trim();

	if (value.length !== 11 || isNaN(value)) {
		input.setCustomValidity("This is not a valid phone number\n(09xxxxxxxxx)");
	} else {
		input.setCustomValidity('');
	}
}

function addLink(id) {
	document.getElementById("deleteLink").setAttribute("href", href = "delete?id=" + id);
}

function previewImage() {
	var input = document.getElementById('profile');
	var preview = document.getElementById('image-preview');
	var container = document.getElementById('image-container');

	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			preview.src = e.target.result;
		};

		reader.readAsDataURL(input.files[0]);
	} else {
		preview.src = "/ServletBulletinBoard/resources/img/profile.png";
	}
}

function setInitialImage() {
	var img = document.getElementById('img');
	var imgName = img.value.split("/")[4];
	if (imgName != "") {
		var preview = document.getElementById('image-preview');
		var container = document.getElementById('image-container');

		if (img) {
			preview.src = img.value;
		} else {
			preview.src = "/ServletBulletinBoard/resources/img/profile.png";
		}
	}
}

function redirectToPage(link) {
	window.location.href = link;
}

$(document).ready(function() {
	$('#msg').delay(5000).hide(0);

	[...document.querySelectorAll('[data-bs-toggle="tooltip"]')].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))

	var total = $('#total').val();
	var pageNum = $('#pageNum').val();
	var limit = $('#limit').val();
	var type = $('#type').val();
	var pagination = new Pagination({
		container: $("#pagination"),
		pageClickUrl: type + "?pageNumber={{page}}"
	});
	pagination.make(total, limit, pageNum);
});