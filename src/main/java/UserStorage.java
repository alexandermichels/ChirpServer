import java.util.List;

public interface UserStorage 
{
	public List<User> getUsers() throws StorageException;
	public List<User> findUserByName(String name) throws StorageException;
	public User findUserByEmail(String email) throws StorageException;
	public void addUser(User u) throws StorageException;
	public void updateUser(String email, String name) throws StorageException;
	public void deleteUser(String email) throws StorageException;

}
