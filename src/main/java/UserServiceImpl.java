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
	public User findUserByEmail(String email) throws StorageException {
		return storage.findUserByEmail(email);
	}

	@Override
	public void createUser(String email, String hash, String handle) throws DuplicateEmailException, StorageException {
		User u = new User(email, hash, handle);
		storage.addUser(u);

	}

	@Override
	public void updateUser(User u) throws DuplicateEmailException, StorageException {
		storage.updateUser(u);
	}

	@Override
	public void deleteUser(String email) throws StorageException {
		storage.deleteUser(email);
	}

}
