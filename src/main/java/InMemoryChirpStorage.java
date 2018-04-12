import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InMemoryChirpStorage implements ChirpStorage 
{
	HashMap<String, ArrayList<Chirp>> userTweets;

	public InMemoryChirpStorage()
	{
		userTweets = new HashMap<>();
	}
	
	@Override
	public List<Chirp> findTweetsByEmail(String email) 
	{
		return userTweets.get(email);
	}

	@Override
	public Chirp findTweetByEmailAndDate(String email, Date date) throws ChirpNotFoundException 
	{
		ArrayList<Chirp> theirTweets = userTweets.get(email);
		for (Chirp t : theirTweets)
		{
			if (date.equals(t.getTime()))
			{
				return t;
			}
		}
		throw new ChirpNotFoundException(email, date);
	}

	@Override
	public void add(Chirp t) 
	{
		if (userTweets.get(t.getCreator()) == null)
		{
			userTweets.put(t.getCreator(), new ArrayList<Chirp>());
			userTweets.get(t.getCreator()).add(t);
		}
		else
		{
			userTweets.get(t.getCreator()).add(t);
		}
	}

	@Override
	public void remove(String email, Date date) throws ChirpNotFoundException 
	{
		ArrayList<Chirp> theirTweets = userTweets.get(email);
		for (Chirp t : theirTweets)
		{
			if (date.equals(t.getTime()))
			{
				theirTweets.remove(t);
			}
		}
		throw new ChirpNotFoundException(email, date);
	}

}
