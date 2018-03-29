import java.util.List;

public class UserServiceImpl implements UserService {
	
	private UserStorage storage;

	public UserServiceImpl(UserStorage storage) 
	{
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
		return storage.findUserByEmail(email);
	}

	@Override
	public void createUser(String email, String name) throws DuplicateEmailException, StorageException {
		User u = new User(email, name);
		storage.addUser(u);

	}

	@Override
	public void updateUser(String email, String name) throws DuplicateEmailException, StorageException {
		storage.updateUser(email, name);
	}

	@Override
	public void deleteUser(String email) throws StorageException {
		storage.deleteUser(email);
	}

}
