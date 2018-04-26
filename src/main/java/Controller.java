import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import java.util.ArrayList;
import java.util.Date;
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

	public Controller(final UserService uService, final ChirpService cService) {
		get("/", (req, res) -> {
			ArrayList<String> following = uService.findUserByEmail(req.headers("username")).getFollowing();
			ArrayList<Chirp> timeline = new ArrayList<Chirp>();
			for (String s : following)
			{
				for (Chirp t : cService.findChirpsByEmail(s))
				{
					timeline.add(t);
				}
				String handle = uService.findUserByEmail(req.headers("username")).getHandle();
				timeline.addAll(cService.findChirpsWithMentions(handle));
			}
			return timeline;
		}, json());

		get("/login", (req, res) -> {
			if (req.headers("authenticated").equals("true"))
			{
				return uService.findUserByEmail(req.headers("username"));
			}
			else if (req.headers("hash").equals(uService.findUserByEmail(req.headers("username")).getHash()))
			{
				return uService.findUserByEmail(req.headers("username"));
			}
			else
			{
				throw new UserAppException("You don't have the correct credentials");
			}

		}, json());

		put("/register", (req, res) -> {

			uService.createUser(req.headers("username"), req.headers("hash"), req.headers("handle"));
			return uService.findUserByEmail(req.headers("username"));

		}, json());

		get("/users/:username", (req, res) -> {
			return cService.findChirpsByEmail(req.headers(":username"));
		}, json());

		post("/users/:username/follow", (req, res) -> {
			User u;
			u = uService.findUserByEmail(req.headers("username"));
			u.addFollowing(req.headers(":username"));
			return true;
		}, json());
		
		post("/users/:username/unfollow", (req, res) -> {
			User u;
			u = uService.findUserByEmail(req.headers("username"));
			u.getFollowing().remove(req.headers(":username"));
			return true;
		}, json());

		get("/users", (req, res) -> {
			return uService.getUsers();
		}, json());
		
		put("/createChirp", (req, res) -> {
			cService.createChirp(new Chirp(req.headers("username"), req.headers("message")));
			return true;
		}, json());
		
		post("/deleteChirp", (req, res) -> {
			Chirp c = cService.findChirpByEmailAndDate(req.headers("creator"), new Date(Long.parseLong(req.headers("date"))));
			cService.deleteChirp(c);
			return true;
		}, json());

	}

	private static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	private static ResponseTransformer json() {
		return Controller::toJson;
	}

}
