import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserStorage implements UserStorage {
	
	private Map<String,User> users;
	
	public InMemoryUserStorage() {
		users = new HashMap<>();
	}

	@Override
	public ArrayList<User> getUsers() throws StorageException {
		return new ArrayList<>(users.values());
	}

	@Override
	public User findUserByEmail(String email) throws StorageException {
		return users.get(email);
	}

	@Override
	public void addUser(User u) throws DuplicateEmailException, StorageException {
		if (users.get(u.getEmail()) != null)
		{
			throw new DuplicateEmailException(u.getEmail() + " is already registered with Chirp");
		}
		else
		{
			users.put(u.getEmail(), u);
		}
	}

	@Override
	public void updateUser(User u) throws StorageException {
		users.put(u.getEmail(), u);
	}

	@Override
	public void deleteUser(String string) throws StorageException {
		users.remove(string);
	}

}
