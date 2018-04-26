
public class DBTest {
	
	public static void main(String[] args) throws StorageException {
		AmazonDBUserStorage storage = new AmazonDBUserStorage();
		System.out.println(storage.getUsers());
		UserServiceImpl service = new UserServiceImpl(storage);
		System.out.println(service.getUsers());
	}

}
