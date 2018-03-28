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
	public List<User> getUsers() throws StorageException {
		return new ArrayList<>(users.values());
	}

	@Override
	public User findUserById(String id) throws StorageException {
		return users.get(id);
	}

	@Override
	public List<User> findUserByName(String name) throws StorageException {
		List<User> result = new ArrayList<>();
		for(User u: users.values()) if (u.getFullName().equals(name)) result.add(u);
		return result;
	}

	@Override
	public List<User> findUserByEmail(String email) throws StorageException {
		List<User> result = new ArrayList<>();
		for(User u: users.values()) if (u.getEmail().equals(email)) result.add(u);
		return result;
	}

	@Override
	public void addUser(User u) throws StorageException {
		 users.put(u.getId().toString(), u);
	}

	@Override
	public void updateUser(String string, String email, String name) throws StorageException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String string) throws StorageException {
		// TODO Auto-generated method stub

	}

}
