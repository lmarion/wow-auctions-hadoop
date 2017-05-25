package auctions;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

import auctions.model.Auction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuctionStatsMapper extends
		Mapper<WritableComparable, Writable, Text, Text> 
{
	
	/**
	 * 
	 */
	public void map(WritableComparable key, Writable value, Context context)
			throws IOException, InterruptedException 
	{
		//get input
		Text input = (Text) value;
		
		String inputStr = input.toString();
		
		//parse out the lines that have the following format
		//{"auc":174280633,"item":43334,"owner":"Melshell","ownerRealm":"Illidan","bid":900000,"buyout":1000000,"quantity":1,"timeLeft":"VERY_LONG","rand":0,"seed":682536064},
		if (inputStr.contains("{\"auc\""))
		{
			String jsonAuction = inputStr.substring(0, inputStr.indexOf("}") + 1);
			
			Gson gson = new GsonBuilder().create();
			Auction auction = gson.fromJson(jsonAuction, Auction.class);
			
			Text itemKey = new Text(auction.getItem());
			
			context.write(itemKey,  new Text(jsonAuction));
		}
	}
}
