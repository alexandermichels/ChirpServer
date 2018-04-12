import java.util.Date;
import java.util.List;

public class ChirpServiceImpl implements ChirpService {
	
	private ChirpStorage storage;
	
	public ChirpServiceImpl(ChirpStorage storage)
	{
		this.storage = storage;
	}

	@Override
	public List<Chirp> findTweetsByEmail(String email) {
		return storage.findTweetsByEmail(email);
	}

	@Override
	public Chirp findTweetByEmailAndDate(String email, Date date) throws ChirpNotFoundException {
		return storage.findTweetByEmailAndDate(email,date);
	}

	@Override
	public void createTweet(String email, String m) {
		Chirp t = new Chirp(email, m);
		storage.add(t);
	}

	@Override
	public void deleteTweet(String email, Date date) throws ChirpNotFoundException {
		storage.remove(email, date);
	}

	

}
