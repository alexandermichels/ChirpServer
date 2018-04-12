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
	public void createUser(String email, String handle) throws DuplicateEmailException, StorageException {
		User u = new User(email, handle);
		storage.addUser(u);

	}

	@Override
	public void updateUser(String email, String handle) throws DuplicateEmailException, StorageException {
		storage.updateUser(email, handle);
	}

	@Override
	public void deleteUser(String email) throws StorageException {
		storage.deleteUser(email);
	}

}
