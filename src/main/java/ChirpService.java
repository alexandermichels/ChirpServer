import java.util.Date;
import java.util.List;

public interface ChirpService 
{
	public List<Chirp> findChirpsByEmail(String email);
	public Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException;
	public List<Chirp> findChirpsWithMentions(String handle);
	public void createChirp(String email, String m);
	public void deleteChirp(Chirp c) throws ChirpNotFoundException;
}
