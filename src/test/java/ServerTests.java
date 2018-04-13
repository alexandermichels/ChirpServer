import static org.junit.Assert.assertArrayEquals;

import org.junit.*;
import com.amazonaws.services.dynamodbv2.document.Item;

public class ServerTests 
{
	@Test
	public void userTests()
	{
		User u = new User("test@example.com", "test");
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
		User u = new User("test@example.com", "test");
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
	public void 
	
}
