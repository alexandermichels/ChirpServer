import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TweetServiceImpl implements TweetService {
	
	private TweetStorage storage;
	
	public TweetServiceImpl(TweetStorage storage)
	{
		this.storage = storage;
	}

	@Override
	public List<Tweet> findTweetsByEmail(String email) {
		return storage.findTweetsByEmail(email);
	}

	@Override
	public Tweet findTweetByEmailAndDate(String email, Date date) throws TweetNotFoundException {
		return storage.findTweetByEmailAndDate(email,date);
	}

	@Override
	public void createTweet(String email, String m) {
		Tweet t = new Tweet(email, m);
		storage.add(t);
	}

	@Override
	public void deleteTweet(String email, Date date) throws TweetNotFoundException {
		storage.remove(email, date);
	}

	

}
