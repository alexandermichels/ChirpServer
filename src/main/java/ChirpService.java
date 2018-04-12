import java.util.Date;
import java.util.List;

public interface ChirpService 
{
	public List<Chirp> findTweetsByEmail(String email);
	public Chirp findTweetByEmailAndDate(String email, Date date) throws ChirpNotFoundException;
	public void createTweet(String email, String m);
	public void deleteTweet(String email, Date date) throws ChirpNotFoundException;
}
