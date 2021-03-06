public class SampleData 
{
	
	public static void addUsers(UserStorage us) throws DuplicateEmailException, StorageException 
	{
		User [] users = new User[10];
		for (int i = 0; i < 10; i++)
		{
			users[i] = new User("test" + i + "@example.com", StringUtil.applySha256("test" + i + "@example.com" + "password" + i), "test" + i);
			for (int j = 0; j < i; j++)
			{
				users[i].addFollowing(users[j].getEmail());
			}
			us.addUser(users[i]);
		}
	}

	public static void addChirps(ChirpStorage cs) throws StorageException
	{
		for(int i = 0; i < 10; i++)
		{
			cs.add(new Chirp("test" + i + "@example.com", "Random words", null));
			cs.add(new Chirp("test" + i + "@example.com", "I really like &test" + ((i+4)%10) + ", they're a cool person", null));
			cs.add(new Chirp("test" + i + "@example.com", "&test" + ((i+7)%10) + " is an asshole", null));
			cs.add(new Chirp("test" + i + "@example.com", "Someone really boring: &test" + (i+2)%10, null));
		}
	}
}
