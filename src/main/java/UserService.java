import java.util.List;
import java.util.UUID;

public interface UserService {
	
	public List<User> getUsers() throws UserAppException;
	public List<User> findUserByName(String name) throws UserAppException;
	public User findUserByEmail(String email) throws UserAppException;
	public void createUser(String email, String name) throws UserAppException;
	public void updateUser(UUID id, String email, String name) throws UserAppException;
	public void deleteUser(UUID id) throws UserAppException;
	public List<Tweet> getUserTimeline(String email) throws UserAppException;

}
