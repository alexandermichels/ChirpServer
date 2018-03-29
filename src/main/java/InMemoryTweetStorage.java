import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InMemoryTweetStorage implements TweetStorage 
{
	HashMap<String, ArrayList<Tweet>> userTweets;

	public InMemoryTweetStorage()
	{
		userTweets = new HashMap<>();
	}
	
	@Override
	public List<Tweet> findTweetsByEmail(String email) 
	{
		return userTweets.get(email);
	}

	@Override
	public Tweet findTweetByEmailAndDate(String email, Date date) throws TweetNotFoundException 
	{
		ArrayList<Tweet> theirTweets = userTweets.get(email);
		for (Tweet t : theirTweets)
		{
			if (date.equals(t.getTime()))
			{
				return t;
			}
		}
		throw new TweetNotFoundException(email, date);
	}

	@Override
	public void add(Tweet t) 
	{
		if (userTweets.get(t.getCreator()) == null)
		{
			userTweets.put(t.getCreator(), new ArrayList<Tweet>());
			userTweets.get(t.getCreator()).add(t);
		}
		else
		{
			userTweets.get(t.getCreator()).add(t);
		}
	}

	@Override
	public void remove(String email, Date date) throws TweetNotFoundException 
	{
		ArrayList<Tweet> theirTweets = userTweets.get(email);
		for (Tweet t : theirTweets)
		{
			if (date.equals(t.getTime()))
			{
				theirTweets.remove(t);
			}
		}
		throw new TweetNotFoundException(email, date);
	}

}
