import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
	
	private UserStorage storage;

	public UserServiceImpl(UserStorage storage) {
		this.storage = storage;
	}

	@Override
	public List<User> getUsers() throws StorageException {
		return storage.getUsers();
	}

	@Override
	public List<User> findUserByName(String name) throws StorageException {
		return storage.findUserByName(name);
	}

	@Override
	public User findUserByEmail(String email) throws StorageException {
		return storage.findUserByEmail(email).get(0);
	}

	@Override
	public void createUser(String email, String name) throws DuplicateEmailException, StorageException {
		User u = new User(UUID.randomUUID(), email, name);
		storage.addUser(u);

	}

	@Override
	public void updateUser(UUID id, String email, String name) throws DuplicateEmailException, StorageException {
		storage.updateUser(id.toString(), email, name);
	}

	@Override
	public void deleteUser(UUID id) throws StorageException {
		storage.deleteUser(id.toString());
	}

	@Override
	public List<Tweet> getUserTimeline(String email) throws UserAppException {
		// TODO Auto-generated method stub
		return null;
	}

}
