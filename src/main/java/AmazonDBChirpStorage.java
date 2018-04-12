import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class AmazonDBChirpStorage implements ChirpStorage
{
	public AmazonDBChirpStorage(AmazonDynamoDB db)
	{
		
	}
	
	private Table getTable()
	{
		return DynamoDBConnector.get().getDB().getTable("ChirpTable");
	}

	@Override
	public List<Chirp> findTweetsByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chirp findTweetByEmailAndDate(String email, Date date) throws ChirpNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Chirp t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String email, Date date) throws ChirpNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
}
