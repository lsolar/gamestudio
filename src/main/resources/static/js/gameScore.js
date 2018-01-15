drawScoreTable();


function drawScoreTable(){
	
	var pickedGame = document.getElementById("choseGame");
	var game = pickedGame.options[pickedGame.selectedIndex].value;
	
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/score/" + game,
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
	//console.log(score);
	var username = $("#username").val().trim();
	var pickedGame = document.getElementById("choseGame");
	var game = pickedGame.options[pickedGame.selectedIndex].value;
	
	if (isNaN(score)) {
		window.alert("Bad input");
		return;
	}
	
var data2send = {
        username: username,
        game: game,
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
