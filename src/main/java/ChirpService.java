import java.util.ArrayList;
import java.util.Date;

public interface ChirpService 
{
	public ArrayList<Chirp> findChirpsByEmail(String email);
	public Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException;
	public ArrayList<Chirp> findChirpsWithMentions(String handle);
	public void createChirp(String email, String m);
	public void deleteChirp(Chirp c) throws ChirpNotFoundException;
}
