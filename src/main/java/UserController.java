import static spark.Spark.get;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;

import spark.Request;
import spark.ResponseTransformer;

public class UserController {
	
	private static final String USER_EMAIL = null;

	// HTTP verb -> REST meaning
	// GET -> read
	// PUT -> create
	// POST -> update
	// DELETE -> delete
	
	public UserController(final UserService service) {
		get("/", (req, res) -> {
	        User user = getAuthenticatedUser(req);
	        return service.getUserTimeline(user);
	}, json());
		
		get("/users", (req, res) -> {
			return service.getUsers();
		}, json());
		
		get("/users/:id", (req, res) -> {
			return service.findUserByEmail(req.attribute(":id"));
		}, json());		
		
	}


	private User getAuthenticatedUser(Request request) 
	{
	    return request.session().attribute(USER_EMAIL);
	}


	private static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	private static ResponseTransformer json() {
		return UserController::toJson;
	}

}
