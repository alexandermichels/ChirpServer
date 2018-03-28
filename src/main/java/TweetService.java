import java.util.List;

public interface TweetService 
{
	public List<Tweet> findTweetsByEmail(String email);
	public Tweet findTweetByEmailAndNum(String email, int i) throws TweetNotFoundException;
	public void createTweet(String email, String m);
	public void deleteTweet(String email, int i) throws TweetNotFoundException;
}
