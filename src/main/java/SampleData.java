import java.util.UUID;

public class SampleData {
	
	public static void addUsers(UserStorage us) throws StorageException {
		us.addUser(new User(UUID.randomUUID(), "ff@here.there", "Fred"));
		us.addUser(new User(UUID.randomUUID(), "br@here.there", "Barney Rubble"));
	}

}
