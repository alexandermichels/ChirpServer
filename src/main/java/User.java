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
		following = new ArrayList<String>();
	}
	
	public User(String email, String shaHash, String handle) {
		this.email = email;
		this.hash = shaHash;
		this.handle = handle;
		this.following = new ArrayList<String>();
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
		getFollowing().add(f);
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
		i.withPrimaryKey("email", this.getEmail()).withString("hash", this.getHash()).withString("handle", this.getHandle()).withList("following", this.getFollowing());
		return i;
	}
	
	@Override
	public boolean equals(Object o)
	{
		User other = ((User)o);
		if (!this.getEmail().equals(other.getEmail()))
		{
			return false;
		}
		else if (!this.getHandle().equals(other.getHandle()))
		{
			return false;
		}
		else if (!this.getHash().equals(other.getHash()))
		{
			return false;
		}
		else
		{
			if (this.getFollowing() == null && other.getFollowing() == null)
			{
				
			}
			else if (this.getFollowing() == null || other.getFollowing() == null)
			{
				return false;
			}
			else
			{
				for (String s : this.getFollowing())
				{
					boolean matched = false;
					for (String t : other.getFollowing())
					{
						if (s.equals(t))
						{
							matched = true;
						}
					}
					
					if (!matched)
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}

}
