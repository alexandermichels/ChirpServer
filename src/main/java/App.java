import static spark.Spark.after;
import static spark.Spark.port;

import org.apache.log4j.BasicConfigurator;

public class App {
	
	
    public static void main(String[] args) throws StorageException {
    		port(5010);
    		BasicConfigurator.configure();
    		after((req,res) -> res.type("application/json"));
    		InMemoryUserStorage userStorage = new InMemoryUserStorage();
    		SampleData.addUsers(userStorage);
		new UserController(new UserServiceImpl(userStorage));
    }

}