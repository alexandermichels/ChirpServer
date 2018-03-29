public class SampleData {
	
	public static void addUsers(UserStorage us) throws StorageException {
		us.addUser(new User("ff@here.there", "Fred"));
		us.addUser(new User("br@here.there", "Barney Rubble"));
	}

}
