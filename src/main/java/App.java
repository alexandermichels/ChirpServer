import static spark.Spark.after;
import static spark.Spark.port;

import org.apache.log4j.BasicConfigurator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class App {
	
	
    public static void main(String[] args) throws DuplicateEmailException, StorageException {
    		port(5000);
    		BasicConfigurator.configure();
    		after((req,res) -> res.type("application/json"));
    		AmazonDBUserStorage userStorage = new AmazonDBUserStorage();
    		AmazonDBChirpStorage tweetStorage = new AmazonDBChirpStorage();
		new Controller(new UserServiceImpl(userStorage), new ChirpServiceImpl(tweetStorage));
    }

}