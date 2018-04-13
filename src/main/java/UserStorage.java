import java.util.List;

public interface UserStorage 
{
	public List<User> getUsers() throws StorageException;
	public User findUserByEmail(String email) throws StorageException;
	public void addUser(User u) throws DuplicateEmailException, StorageException;
	public void updateUser(User u) throws StorageException;
	public void deleteUser(String email) throws StorageException;
}
