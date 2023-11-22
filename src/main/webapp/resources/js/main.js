window.onload = function() {
	setInitialImage();
};

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
			container.style.display = 'block';
		};

		reader.readAsDataURL(input.files[0]);
	} else {
		preview.src = '';
		container.style.display = 'none'; // Hide the image container if no file selected
	}
}

function setInitialImage() {
	var img = document.getElementById('img');
	if (img != null) {
		var preview = document.getElementById('image-preview');
		var container = document.getElementById('image-container');

		if (img) {
			preview.src = img.value;
			container.style.display = 'block';
		} else {
			preview.src = '';
			container.style.display = 'none';
		}
	}
}

$(document).ready(function() {
	var total = $('#total').val();
	var pageNum = $('#pageNum').val();
	var type = $('#type').val();
	var pagination = new Pagination({
		container: $("#pagination"),
		pageClickUrl: type + "?pageNumber={{page}}",
		maxVisibleElements: 9,
	});
	pagination.make(total, 10, pageNum);
});