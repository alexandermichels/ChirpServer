import java.util.Date;

import com.amazonaws.services.dynamodbv2.document.Item;

public class Chirp 
{
	private String creatorEmail;
	private Date time;
	private String message;
	
	public Chirp(String email, String m)
	{
		this.creatorEmail = email;
		this.time = new Date();
		this.message = m;
	}
	
	public String getCreator()
	{
		return creatorEmail;
	}
	
	public Date getTime()
	{
		return time;
	}
	
	public void setTime(long i)
	{
		this.time = new Date(i);
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getID()
	{
		return (String)(this.getCreator()+this.getTime().getTime());
	}
	
	public static Chirp fromItem(Item item)
	{
		Chirp c = new Chirp(item.getString("email"),item.getString("message"));
		c.setTime(item.getLong("date"));
		return c;
	}
	
	public Item toItem()
	{
		Item i = new Item();
		i.withPrimaryKey("chirpID",this.getID()).withString("creator", this.getCreator()).withString("message", this.getMessage()).withLong("date", this.getTime().getTime());
		return i;
	}
}
