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

		get("/:username", (req, res) -> {
			return cService.findChirpsByEmail(req.headers(":username"));
		}, json());

		post("/:username/follow", (req, res) -> {
			User u;
			u = uService.findUserByEmail(req.headers("username"));
			u.addFollowing(req.headers(":username"));
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
