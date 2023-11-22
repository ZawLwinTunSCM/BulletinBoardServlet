function addLink(id) {
	console.log(id);
	document.getElementById("deleteLink").setAttribute("href", href = "delete?id=" + id);
}

function getParameterByName(name, url) {
	if (!url) url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
		results = regex.exec(url);
	if (!results) return null;
	if (!results[2]) return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}
$(document).ready(function() {
	var total = $('#total').val();
	var pageNum = $('#pageNum').val();
	var type = $('#type').val();
	var pagination = new Pagination({
		container: $("#pagination"),
		pageClickUrl: type+"?pageNumber={{page}}",
		maxVisibleElements: 9,
	});
	pagination.make(total, 10, pageNum);
});