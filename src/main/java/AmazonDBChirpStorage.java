import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class AmazonDBChirpStorage implements ChirpStorage
{
	public AmazonDBChirpStorage()
	{
		
	}
	
	private Table getTable()
	{
		return DynamoDBConnector.get().getDB().getTable("ChirpTable");
	}

	@Override
	public ArrayList<Chirp> findChirpsByEmail(String email) {
		Table t = getTable();
		ScanFilter s = new ScanFilter("creator").eq(email);
		ItemCollection<ScanOutcome> c = t.scan(s);
		ArrayList<Chirp> chirps = new ArrayList<>();
		for(Item item: c) 
		{
			chirps.add(Chirp.fromItem(item));
		}
		return chirps;
	}

	@Override
	public Chirp findChirpByEmailAndDate(String email, Date date) throws ChirpNotFoundException {
		Table t = getTable();
		return Chirp.fromItem(t.getItem(new PrimaryKey("chirpID", email+date.getTime())));
	}
	
	@Override
	public ArrayList<Chirp> findChirpsWithMentions(String handle)
	{
		Table t = getTable();
		ScanFilter s = new ScanFilter("message").contains("&"+handle);
		ItemCollection<ScanOutcome> c = t.scan(s);
		ArrayList<Chirp> chirps = new ArrayList<>();
		for(Item item: c) 
		{
			chirps.add(Chirp.fromItem(item));
		}
		return chirps;
	}

	@Override
	public void add(Chirp c) 
	{
		Table t = getTable();
		t.putItem(c.toItem());
	}

	@Override
	public void remove(Chirp c) throws ChirpNotFoundException 
	{
		Table t = getTable();
		t.deleteItem(new PrimaryKey("chirpID", c.getID()));
	}
	
}
