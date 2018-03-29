import java.util.Date;

public class Tweet 
{
	private String creatorEmail;
	private Date time;
	private String message;
	
	public Tweet(String email, String m)
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
	
	public String getMessage()
	{
		return message;
	}
}
