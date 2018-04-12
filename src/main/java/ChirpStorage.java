import java.util.Date;
import java.util.List;

public interface ChirpStorage 
{
	List<Chirp> findChirpsByEmail(String email);
	Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException;
	List<Chirp> findChirpsWithMentions(String handle);
	void add(Chirp c);
	void remove(Chirp c) throws ChirpNotFoundException;
}
