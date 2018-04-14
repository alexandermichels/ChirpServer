import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
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
	public void userServiceInMemorySampleData() throws UserAppException
	{
		InMemoryUserStorage stor = new InMemoryUserStorage();
		SampleData.addUsers(stor);
		UserServiceImpl serv = new UserServiceImpl(stor);
	}
	
	@Test
	public void userServiceInMemoryGetUsers() throws UserAppException
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
	
	@Test
	public void chirpServiceInMemoryCreateChirp()
	{
		ChirpServiceImpl serv = new ChirpServiceImpl(new InMemoryChirpStorage());
		serv.createChirp("test@example.com", "test");
	}
	
	@Test
	public void chirpServiceInMemorySampleData() throws StorageException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);
	}
	
	@Test(expected = ChirpNotFoundException.class)
	public void chirpServiceInMemoryDeleteChirp1() throws StorageException, ChirpNotFoundException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);
		serv.deleteChirp(new Chirp("test10@example", "I know you can't find this one"));
	}
	
	@Test(expected = ChirpNotFoundException.class)
	public void chirpServiceInMemoryDeleteChirp2() throws StorageException, ChirpNotFoundException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);
		serv.deleteChirp(new Chirp("test2@example", "I know you can't find this one"));
	}
	
	@Test
	public void chirpServiceInMemoryFindMentions() throws StorageException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);
		Chirp [][] mentions = new Chirp[10][3];
		for (int i = 0; i < 10; i++)
		{
			ArrayList<Chirp> c = serv.findChirpsWithMentions("test" + i);
			for (int j = 0; j < c.size(); j++)
			{
				mentions[i][j]=c.get(j);
			}
			assert(c.size() == 3);
		}
	}
	
	@Test
	public void chirpServiceInMemoryFindChirpsByEmail() throws StorageException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);
		Chirp [][] mentions = new Chirp[10][4];
		for (int i = 0; i < 10; i++)
		{
			ArrayList<Chirp> c = serv.findChirpsByEmail("test" + i + "@example.com");
			for (int j = 0; j < c.size(); j++)
			{
				mentions[i][j]=c.get(j);
			}
			assert(c.size() == 4);
		}
	}
	
	@Test
	public void chirpServiceInMemoryFindChirpByEmailAndDate() throws ChirpNotFoundException, StorageException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);
		Chirp [][] mentions = new Chirp[10][4];
		for (int i = 0; i < 10; i++)
		{
			ArrayList<Chirp> c = serv.findChirpsByEmail("test" + i + "@example.com");
			for (int j = 0; j < c.size(); j++)
			{
				mentions[i][j]=c.get(j);
			}
		}
		
		for (Chirp [] a : mentions)
		{
			for (Chirp c : a)
			{
				Chirp p = serv.findChirpByEmailAndDate(c.getCreator(), c.getTime());
			}
		}
	}
	
	@Test(expected = ChirpNotFoundException.class)
	public void chirpServiceInMemoryFindChirpByEmailAndDateFail() throws ChirpNotFoundException, StorageException
	{
		InMemoryChirpStorage stor = new InMemoryChirpStorage();
		SampleData.addChirps(stor);
		ChirpServiceImpl serv = new ChirpServiceImpl(stor);		
		Chirp c = serv.findChirpByEmailAndDate("test0@example.com", new Date(00000));
	}
	
	@Test
	public void userServiceAmazonDBCreateUser() throws DuplicateEmailException, StorageException
	{
		BasicConfigurator.configure();
		UserServiceImpl serv = new UserServiceImpl(new AmazonDBUserStorage());
		serv.createUser("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
	}
	
	@Test(expected = DuplicateEmailException.class)
	public void userServiceAmazonDBCreateUserFails() throws DuplicateEmailException, StorageException
	{
		BasicConfigurator.configure();
		UserServiceImpl serv = new UserServiceImpl(new AmazonDBUserStorage());
		serv.createUser("test@example.com", StringUtil.applySha256("test@example.com" + "password"), "test");
	}
	
	@Test
	public void userServiceAmazonDBFindUserByEmail() throws StorageException, DuplicateEmailException
	{
		BasicConfigurator.configure();
		UserServiceImpl serv = new UserServiceImpl(new AmazonDBUserStorage());
		serv.createUser("hello@hi.com", StringUtil.applySha256("hello@hi.comhello"), "Hello");
		User u = serv.findUserByEmail("hello@hi.com");
		assert(u.getEmail().equals("hello@hi.com"));
		assert(u.getHandle().equals("Hello"));
		assert(u.getHash().equals(StringUtil.applySha256("hello@hi.comhello")));
		serv.deleteUser("hello@hi.com");
	}
	
	@Test
	public void userServiceAmazonDBDeleteUser() throws StorageException
	{
		BasicConfigurator.configure();
		UserServiceImpl serv = new UserServiceImpl(new AmazonDBUserStorage());
		serv.deleteUser("test@example.com");
	}
	
	@Test
	public void userServiceAmazonDBSampleData() throws DuplicateEmailException, StorageException
	{
		BasicConfigurator.configure();
		AmazonDBUserStorage stor = new AmazonDBUserStorage();
		SampleData.addUsers(stor);
		UserServiceImpl serv = new UserServiceImpl(stor);
	}
	
	
	@Test
	public void userServiceAmazonDBGetUsers() throws DuplicateEmailException, StorageException
	{
		BasicConfigurator.configure();
		AmazonDBUserStorage stor = new AmazonDBUserStorage();
		UserServiceImpl serv = new UserServiceImpl(stor);
		ArrayList<User> users = serv.getUsers();
	}
	
	@After
	public void userServiceAmazonDBCleanUpSampleData() throws StorageException
	{
		BasicConfigurator.configure();
		AmazonDBUserStorage stor = new AmazonDBUserStorage();
		UserServiceImpl serv = new UserServiceImpl(stor);
		for (int i = 0; i < 10; i++)
		{
			serv.deleteUser("test"+i+"@example.com");
		}
	}
}
