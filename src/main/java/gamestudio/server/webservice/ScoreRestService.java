package gamestudio.server.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import gamestudio.entity.Score;
import gamestudio.service.ScoreService;

@Path("/score")
public class ScoreRestService {
	@Autowired
	private ScoreService scoreService;

	// @GET
	// public String hello() {
	// return "Hello World";
	// }

	// http://localhost:8080/rest/score/mines
	@POST
	@Consumes("application/json")
	public Response addScore(Score score) {
		scoreService.addScore(score);
		return Response.ok().build();
	}

	@GET
	@Path("/{game}")
	@Produces("application/json")	
	public List<Score> getTopScores(@PathParam("game") String game) {
		return scoreService.getTopScore(game);
		// return "Hello " + game;
	}
}
