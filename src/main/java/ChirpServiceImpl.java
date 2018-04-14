import java.util.ArrayList;
import java.util.Date;

public class ChirpServiceImpl implements ChirpService {
	
	private ChirpStorage storage;
	
	public ChirpServiceImpl(ChirpStorage storage)
	{
		this.storage = storage;
	}

	@Override
	public ArrayList<Chirp> findChirpsByEmail(String email) {
		return storage.findChirpsByEmail(email);
	}

	@Override
	public Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException {
		return storage.findChirpByEmailAndDate(email,date);
	}
	
	@Override
	public ArrayList<Chirp> findChirpsWithMentions(String handle)
	{
		return storage.findChirpsWithMentions(handle);
	}

	@Override
	public void createChirp(String email, String m) {
		Chirp t = new Chirp(email, m);
		storage.add(t);
	}

	@Override
	public void deleteChirp(Chirp c) throws ChirpNotFoundException {
		storage.remove(c);
	}

	

}
