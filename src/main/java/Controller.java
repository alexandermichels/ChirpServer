import static spark.Spark.get;
import static spark.Spark.put;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;

import spark.Request;
import spark.ResponseTransformer;

public class Controller {

	// HTTP verb -> REST meaning
	// GET -> read
	// PUT -> create
	// POST -> update
	// DELETE -> delete
	private static String USER_EMAIL;
	
	public Controller(final UserService uService, final ChirpService cService) {
		get("/", (req, res) -> {
	        ArrayList<String> following = uService.findUserByEmail(USER_EMAIL).getFollowing();
	        ArrayList<Chirp> timeline = new ArrayList<Chirp>();
	        for (String s : following)
	        {
	        	for (Chirp t : cService.findChirpsByEmail(s))
	        	{
	        		timeline.add(t);
	        	}
	        	String handle = uService.findUserByEmail(USER_EMAIL).getHandle();
	        	timeline.addAll(cService.findChirpsWithMentions(handle));
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
