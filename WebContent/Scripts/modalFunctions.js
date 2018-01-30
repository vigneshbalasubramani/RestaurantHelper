var addFood, modal, span;
window.onload = function onLoad() {
	modal = document.getElementById('myModal');
	span = document.getElementsByClassName("close")[0];	
	
	span.onclick = function() {
		modal.style.display = "none";
	}
	
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
}
