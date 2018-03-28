import java.util.UUID;

public class User {
	
	private UUID id;
	private String email;
	private String fullName;
	
	// Gson wants a default constructor
	public User() 
	{

	}
	
	public User(UUID id, String email, String fullName) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
	}
	
	@Override
	public String toString() {
		return id.toString()+": "+email+" "+fullName;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
