import java.util.Date;
import java.util.List;

public interface TweetService 
{
	public List<Tweet> findTweetsByEmail(String email);
	public Tweet findTweetByEmailAndDate(String email, Date date) throws TweetNotFoundException;
	public void createTweet(String email, String m);
	public void deleteTweet(String email, Date date) throws TweetNotFoundException;
}
