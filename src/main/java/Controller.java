import static spark.Spark.get;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;

import spark.Request;
import spark.ResponseTransformer;

public class Controller {
	
	private static final String USER_EMAIL;

	// HTTP verb -> REST meaning
	// GET -> read
	// PUT -> create
	// POST -> update
	// DELETE -> delete
	
	public Controller(final UserService uService, final TweetService tService) {
		get("/", (req, res) -> {
	        ArrayList<String> following = uService.findUserByEmail(USER_EMAIL).getFollowing();
	        ArrayList<Tweet> timeline = new ArrayList<Tweet>();
	        for (String s : following)
	        {
	        	for (Tweet t : tService.findTweetsByEmail(s))
	        	{
	        		timeline.add(t);
	        	}
	        }
	        return timeline;
	}, json());
		
		get("/users", (req, res) -> {
			return uService.getUsers();
		}, json());
		
		get("/users/email", (req, res) -> {
			return uService.findUserByEmail(req.attribute("email"));
		}, json());		
		
	}

	private static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	private static ResponseTransformer json() {
		return Controller::toJson;
	}

}
