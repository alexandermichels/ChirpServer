import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class AmazonDBUserStorage implements UserStorage
{
	
	public AmazonDBUserStorage(AmazonDynamoDB db)
	{
		
	}
	
	private Table getTable()
	{
		return DynamoDBConnector.get().getDB().getTable("UserTable");
	}

	@Override
	public List<User> getUsers() throws StorageException {
		Table t = getTable();
		ItemCollection<ScanOutcome> c = t.scan();
		ArrayList<User> users = new ArrayList<>();
		for(Item item: c) 
		{
			users.add(User.fromItem(item));
		}
		return users;
	}

	@Override
	public User findUserByEmail(String email) throws StorageException {
		Table t = getTable();
		return User.fromItem(t.getItem("email", email));
	}

	@Override
	public void addUser(User u) throws DuplicateEmailException, StorageException {
		Table t = getTable();
		try
		{
			findUserByEmail(u.getEmail());
			throw new DuplicateEmailException(u.getEmail() + " is already registered with Chirp");
		}
		catch(StorageException e)
		{
			Item i = u.toItem();
			t.putItem(i);
		}
	}

	@Override
	public void updateUser(User u) throws StorageException {
		Table t = getTable();
		AttributeUpdate h = new AttributeUpdate("handle").put(u.getHandle());
		AttributeUpdate f = new AttributeUpdate("following").put(u.getFollowing());
		AttributeUpdate p = new AttributeUpdate("hash").put(u.getHash());
		t.updateItem(new PrimaryKey("email", u.getEmail()), h, f, p);
	}

	@Override
	public void deleteUser(String email) throws StorageException {
		Table t = getTable();
		t.deleteItem(new PrimaryKey("email", email));
	}

}
