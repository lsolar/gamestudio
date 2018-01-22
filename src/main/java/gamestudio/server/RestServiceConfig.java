package gamestudio.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import gamestudio.server.webservice.RatingRestService;
import gamestudio.server.webservice.ScoreRestService;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/rest")
public class RestServiceConfig extends ResourceConfig {
	// Jersey
	public RestServiceConfig() {
		 register(ScoreRestService.class);
		 register(RatingRestService.class);
		 register(ScoreRestService.class);
		
//		packages("gamestudio.server");
	}
}
