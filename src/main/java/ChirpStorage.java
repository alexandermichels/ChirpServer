import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ChirpStorage 
{
	ArrayList<Chirp> findChirpsByEmail(String email);
	Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException;
	ArrayList<Chirp> findChirpsWithMentions(String handle);
	void add(Chirp c);
	void remove(Chirp c) throws ChirpNotFoundException;
}
