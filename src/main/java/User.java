import java.util.ArrayList;
import java.util.Arrays;

import com.amazonaws.services.dynamodbv2.document.Item;

public class User 
{
	
	private String email;
	private String handle;
	private ArrayList<String> following;
	
	// Gson wants a default constructor
	public User() 
	{

	}
	
	public User(String email, String handle) {
		this.email = email;
		this.handle = handle;
	}
	
	@Override
	public String toString() {
		return email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public ArrayList<String> getFollowing() {
		return following;
	}

	public void setFollowing(ArrayList<String> following) {
		this.following = following;
	}
	
	public void addFollowing(String f)
	{
		getFollowing().add(f);
	}
	
	public static User fromItem(Item item)
	{
		User u = new User(item.getString("email"),item.getString("handle"));
		u.setFollowing(new ArrayList<String>(item.getList("following")));
		return u;
	}
	
	public Item toItem()
	{
		Item i = new Item();
		i.withPrimaryKey("email", this.getEmail()).withString("handle", this.getHandle()).withList("following", this.getFollowing());
		return i;
	}

}
