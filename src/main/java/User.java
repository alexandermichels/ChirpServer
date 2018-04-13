import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.document.Item;

public class User 
{
	
	private String email;
	private String hash;
	private String handle;
	private ArrayList<String> following;
	
	// Gson wants a default constructor
	public User() 
	{

	}
	
	public User(String email, String shaHash, String handle) {
		this.email = email;
		this.hash = shaHash;
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
		if (getFollowing() != null)
		{
			getFollowing().add(f);
		}
		else
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add(f);
			setFollowing(a);
		}
	}
	
	public static User fromItem(Item item)
	{
		User u = new User(item.getString("email"),item.getString("hash"),item.getString("handle"));
		u.setFollowing(new ArrayList<String>(item.getList("following")));
		return u;
	}
	
	public Item toItem()
	{
		Item i = new Item();
		if (following != null)
		{
			i.withPrimaryKey("email", this.getEmail()).withString("hash", this.getHash()).withString("handle", this.getHandle()).withList("following", this.getFollowing());
		}
		else
		{
			i.withPrimaryKey("email", this.getEmail()).withString("hash", this.getHash()).withString("handle", this.getHandle()).withList("following", new ArrayList<String>());
		}
		return i;
	}

}
