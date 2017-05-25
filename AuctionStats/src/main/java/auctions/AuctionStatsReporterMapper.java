package auctions;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

import auctions.model.Auction;
import auctions.model.AuctionMean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuctionStatsReporterMapper extends
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
		
		//now that the first job has run, we have key json pairs
		//where the json piece is either the original auction or the auctionmean
		if (inputStr.contains("\t"))
		{
			String[] strs = inputStr.split("\t");
			String json = strs[1];
			json = json.substring(0, json.indexOf("}") + 1);
			
			if (json.contains("{\"auc\":")) //original auction json
			{	
				Gson gson = new GsonBuilder().create();
				Auction auction = gson.fromJson(json, Auction.class);
				
				Text itemKey = new Text(auction.getItem());
				
				context.write(itemKey,  new Text(json));			
			}
			
			if (json.contains("{\"item\":")) //mean auction json
			{
				Gson gson = new GsonBuilder().create();
				AuctionMean mean = gson.fromJson(json, AuctionMean.class);
				
				Text itemKey = new Text(mean.getItem());
				
				context.write(itemKey,  new Text(json));
			}
		}
		else
		{
			String json = inputStr;
			json = json.substring(0, json.indexOf("}") + 1);
			
			if (json.contains("{\"auc\":")) //original auction json
			{	
				Gson gson = new GsonBuilder().create();
				Auction auction = gson.fromJson(json, Auction.class);
				
				Text itemKey = new Text(auction.getItem());
				
				context.write(itemKey,  new Text(json));			
			}			
		}
	}
}
