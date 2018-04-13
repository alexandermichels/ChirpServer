import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.*;
import com.amazonaws.services.dynamodbv2.document.Item;

public class ServerTests 
{
	@Test
	public void userTests()
	{
		User u = new User("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
		assert(u.getEmail().equals("test@example.com"));
		assert(u.getHandle().equals("test"));
		Item i = u.toItem();
		u = User.fromItem(i);
		assert(u.getEmail().equals("test@example.com"));
		assert(u.getHandle().equals("test"));
	}
	
	@Test
	public void userTests2()
	{
		User u = new User("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
		u.addFollowing("no one");
		u.addFollowing("someone");
		String [] f = {"no one", "someone"};
		assertArrayEquals(u.getFollowing().toArray(), f);
		Item i = u.toItem();
		u = User.fromItem(i);
		assertArrayEquals(u.getFollowing().toArray(), f);
	}
	
	@Test
	public void chirpTest1()
	{
		Chirp c = new Chirp("test@example.com", "Chirping is fun");
		assert(c.getCreator().equals("test@example.com"));
		assert(c.getMessage().equals("Chirping is fun"));
		assert(c.getID().equals(c.getCreator()+c.getTime().getTime()));
		Item i = c.toItem();
		c = Chirp.fromItem(i);
		assert(c.getCreator().equals("test@example.com"));
		assert(c.getMessage().equals("Chirping is fun"));
		assert(c.getID().equals(c.getCreator()+c.getTime().getTime()));
	}
	
	@Test
	public void userServiceInMemoryCreateUser() throws DuplicateEmailException, StorageException
	{
		UserServiceImpl serv = new UserServiceImpl(new InMemoryUserStorage());
		serv.createUser("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
	}
	
	@Test(expected = DuplicateEmailException.class)
	public void userServiceInMemoryDuplicateEmailExceptionWorks() throws DuplicateEmailException, StorageException
	{
		UserServiceImpl serv = new UserServiceImpl(new InMemoryUserStorage());
		serv.createUser("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
		serv.createUser("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test1");
	}
	
	@Test
	public void userServiceInMemoryUpdateUser() throws UserAppException
	{
		UserServiceImpl serv = new UserServiceImpl(new InMemoryUserStorage());
		serv.createUser("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
		serv.updateUser(new User("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test2"));
		User u = serv.findUserByEmail("test@example.com");
		assert(u.getEmail().equals("test@example.com"));
		assert(u.getHash().equals(StringUtil.applySha256("test@example.compassword")));
		assert(u.getHandle().equals("test2"));
	}
	
	@Test
	public void userServerInMemorySampleData() throws UserAppException
	{
		InMemoryUserStorage stor = new InMemoryUserStorage();
		SampleData.addUsers(stor);
		UserServiceImpl serv = new UserServiceImpl(stor);
	}
	
	@Test
	public void userServerInMemoryGetUsers() throws UserAppException
	{
		InMemoryUserStorage stor = new InMemoryUserStorage();
		SampleData.addUsers(stor);
		UserServiceImpl serv = new UserServiceImpl(stor);
		ArrayList<User> u = serv.getUsers();
		assert(u.size() == 10);
		User [] users = new User[10];
		for (int i = 0; i < 10; i++)
		{
			users[i] = new User("test" + i + "@example.com", StringUtil.applySha256("test" + i + "@example.com" + "password" + i), "test" + i);
			for (int j = 0; j < i; j++)
			{
				users[i].addFollowing(users[j].getEmail());
			}
		}
		
		for (User a : u)
		{
			boolean matched = false;
			for (User b : users)
			{
				if (a.equals(b))
				{
					matched = true;
				}
			}
			
			assert(matched);
		}
	}
}
