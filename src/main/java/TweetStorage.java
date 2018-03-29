import java.util.Date;
import java.util.List;

public interface TweetStorage 
{
	List<Tweet> findTweetsByEmail(String email);
	Tweet findTweetByEmailAndDate(String email, Date date) throws TweetNotFoundException;
	void add(Tweet t);
	void remove(String email, Date date) throws TweetNotFoundException;
}
