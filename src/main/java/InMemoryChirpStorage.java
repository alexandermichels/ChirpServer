import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InMemoryChirpStorage implements ChirpStorage 
{
	HashMap<String, ArrayList<Chirp>> userChirps;

	public InMemoryChirpStorage()
	{
		userChirps = new HashMap<>();
	}
	
	@Override
	public List<Chirp> findChirpsByEmail(String email) 
	{
		return userChirps.get(email);
	}

	@Override
	public Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException 
	{
		ArrayList<Chirp> theirTweets = userChirps.get(email);
		for (Chirp t : theirTweets)
		{
			if (date.equals(t.getTime()))
			{
				return t;
			}
		}
		throw new ChirpNotFoundException(email, date);
	}
	
	@Override
	public List<Chirp> findChirpsWithMentions(String handle)
	{
		ArrayList<Chirp> chirps = new ArrayList<>();
		for (ArrayList<Chirp> c : userChirps.values())
		{
			for (Chirp p : c)
			{
				for (int i = 0; i < p.getMessage().length()-handle.length()-1; i++)
				{
					if (p.getMessage().substring(i, i+handle.length()+1).equals("&"+handle))
					{
						chirps.add(p);
					}		
				}
			}
		}
		return chirps;
	}

	@Override
	public void add(Chirp t) 
	{
		if (userChirps.get(t.getCreator()) == null)
		{
			userChirps.put(t.getCreator(), new ArrayList<Chirp>());
			userChirps.get(t.getCreator()).add(t);
		}
		else
		{
			userChirps.get(t.getCreator()).add(t);
		}
	}

	@Override
	public void remove(Chirp c) throws ChirpNotFoundException 
	{
		ArrayList<Chirp> theirTweets = userChirps.get(c.getCreator());
		for (Chirp t : theirTweets)
		{
			if (c.getTime().equals(t.getTime()))
			{
				theirTweets.remove(t);
			}
		}
		throw new ChirpNotFoundException(c.getCreator(), c.getTime());
	}

}
