import java.util.Date;
import java.util.List;

public interface ChirpStorage 
{
	List<Chirp> findTweetsByEmail(String email);
	Chirp findTweetByEmailAndDate(String email, Date date) throws ChirpNotFoundException;
	void add(Chirp t);
	void remove(String email, Date date) throws ChirpNotFoundException;
}
