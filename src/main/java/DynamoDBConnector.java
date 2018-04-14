import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBConnector 
{
	private static DynamoDBConnector theOnlyOne;
	private AmazonDynamoDB client;
	
	private DynamoDBConnector()
	{
		client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		
	}
	
	public static DynamoDBConnector get()
	{
		if (theOnlyOne == null)
		{
			theOnlyOne = new DynamoDBConnector();
		}
		return theOnlyOne;
		
	}
	
	public DynamoDB getDB()
	{
		return new DynamoDB(client);
	}
}
