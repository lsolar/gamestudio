drawScoreTable();
function drawScoreTable(){

	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/score/mines",
		dataType : "json"
	}).done(function(receivedData) {
		var tmplScores = $("#tmplScores").html();
		var table = Mustache.render(tmplScores, receivedData);
		$("#scoreTable").html(table);
	})

	.fail(function() {
		$("#scoreTable").html("<p>Sorry, unable to get data</p>");
	});
}
$("#btSaveScore").click(function() {
	var score = parseInt($("#inNewScore").val().trim());
	console.log(score);

	if (isNaN(score)) {
		window.alert("Bad input");
		return;
	}
var data2send = {
        username: "Alojz",
        game: "Battlefield4",
        value: score
}	


	$.ajax({
		type : "POST",
		url : "http://localhost:8080/rest/score",
		contentType : "application/json",
		data: JSON.stringify(data2send)
	})
	.done(function(receivedData) {
		drawScoreTable();
	})

	.fail(function() {
		drawScoreTable();
	});
});
