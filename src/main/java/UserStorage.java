import java.util.List;

public interface UserStorage {
	
	public List<User> getUsers() throws StorageException;
	public User findUserById(String id) throws StorageException;
	public List<User> findUserByName(String name) throws StorageException;
	public List<User> findUserByEmail(String email) throws StorageException;
	public void addUser(User u) throws StorageException;
	public void updateUser(String string, String email, String name) throws StorageException;
	public void deleteUser(String string) throws StorageException;

}
