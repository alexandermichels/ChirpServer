import java.util.List;

public interface UserService {
	
	public List<User> getUsers() throws UserAppException;
	public User findUserByEmail(String email) throws UserAppException;
	public void createUser(String email, String hash, String handle) throws UserAppException;
	public void updateUser(User u) throws UserAppException;
	public void deleteUser(String email) throws UserAppException;
}
