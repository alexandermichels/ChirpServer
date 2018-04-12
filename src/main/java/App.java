import static spark.Spark.after;
import static spark.Spark.port;

import org.apache.log4j.BasicConfigurator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class App {
	
	
    public static void main(String[] args) throws StorageException {
    		port(5010);
    		BasicConfigurator.configure();
    		after((req,res) -> res.type("application/json"));
    		InMemoryUserStorage userStorage = new InMemoryUserStorage();
    		InMemoryChirpStorage tweetStorage = new InMemoryChirpStorage();
    		SampleData.addUsers(userStorage);
    		
		new Controller(new UserServiceImpl(userStorage), new ChirpServiceImpl(tweetStorage));
    }

}