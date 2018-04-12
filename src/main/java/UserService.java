import java.util.List;

public interface UserService {
	
	public List<User> getUsers() throws UserAppException;
	public User findUserByEmail(String email) throws UserAppException;
	public void createUser(String email, String handle) throws UserAppException;
	public void updateUser(String email, String handle) throws UserAppException;
	public void deleteUser(String email) throws UserAppException;
}
