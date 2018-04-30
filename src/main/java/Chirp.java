import java.util.Date;

import com.amazonaws.services.dynamodbv2.document.Item;


public class Chirp 
{
	private String creatorEmail;
	private Date time;
	private String message;
	private byte [] image;
	
	public Chirp(String email, String m, byte [] image)
	{
		this.creatorEmail = email;
		this.time = new Date();
		this.message = m;
		this.image = image;
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
	

	public byte [] getImage() { return image; }
	
	public String getID()
	{
		return (String)(this.getCreator()+this.getTime().getTime());
	}
	
	public static Chirp fromItem(Item item)
	{
		Chirp c;
		try
		{
			c = new Chirp(item.getString("creator"), item.getString("message"), item.getBinary("image"));
		}
		catch (Exception e)
		{
			c = new Chirp(item.getString("creator"), item.getString("message"), null);
		}
		c.setTime(item.getLong("date"));
		return c;
	}
	
	public Item toItem()
	{
		Item i = new Item();
		if (this.getImage() != null)
		{
			i.withPrimaryKey("chirpID",this.getID()).withString("creator", this.getCreator()).withString("message", this.getMessage()).withLong("date", this.getTime().getTime()).withBinary("image", image);
		}
		else
		{
			i.withPrimaryKey("chirpID",this.getID()).withString("creator", this.getCreator()).withString("message", this.getMessage()).withLong("date", this.getTime().getTime());
		}
		return i;
	}
}
