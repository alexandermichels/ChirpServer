import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

public class AmazonDBUserStorage implements UserStorage
{
	private AmazonDynamoDB db;
	
	public AmazonDBUserStorage(AmazonDynamoDB db)
	{
		this.db = db;
	}

	@Override
	public List<User> getUsers() throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByEmail(String email) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User u) throws StorageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(String email, String name) throws StorageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String email) throws StorageException {
		// TODO Auto-generated method stub
		
	}

}
