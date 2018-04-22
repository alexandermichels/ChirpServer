import static spark.Spark.get;
import static spark.Spark.post;
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
		
		get("/login", (req, res) -> {
			try
			{
				USER_EMAIL = req.params("username");
			}
			catch (Exception e)
			{
				return null;
			}
			
			if (req.params("authenticated").equals("true"))
			{
				return uService.findUserByEmail(USER_EMAIL);
			}
			else if (req.params("hash").equals(uService.findUserByEmail(req.params("username")).getHash()))
			{
				return uService.findUserByEmail(USER_EMAIL);
			}
			else
			{
				return null;
			}
			
		}, json());
		
		put("/register", (req, res) -> {
			try
			{
				uService.createUser(req.params("username"), req.params("hash"), req.params("handle"));
				USER_EMAIL = req.params("username");
				return uService.findUserByEmail(USER_EMAIL);
			}
			catch (Exception e)
			{
				return null;
			}
			
		}, json());
		
		get("/:username", (req, res) -> {
			return cService.findChirpsByEmail(req.params(":username"));
		}, json());
		
		post("/:username/follow", (req, res) -> {
			User u;
			try
			{
				 u = uService.findUserByEmail(USER_EMAIL);
			}
			catch(Exception e)
			{
				return false;
			}
			u.addFollowing(req.params(":username"));
			return true;
		}, json());
		
		get("/users", (req, res) -> {
			return uService.getUsers();
		}, json());
		
	}

	private static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	private static ResponseTransformer json() {
		return Controller::toJson;
	}

}
