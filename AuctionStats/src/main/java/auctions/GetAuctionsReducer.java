package auctions;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

public class GetAuctionsReducer extends Reducer<Text, Text, Text, Text> 
{
	
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException 
	{
			//realistically there should only be 1 value being emitted as every server only has
			//1 auction url
			String auctions = null;
		
			for (Text value : values)
			{
				String auctionUrl = value.toString();
				
				// create the rest client instance
		    	RestClient client = new RestClient();

		    	// create the resource instance to interact with
		    	Resource resource = client.resource(auctionUrl);

				//execute web-service call to retrieve auction data
		    	auctions = resource.accept("application/json").get(String.class);
			}
			
			context.write(key, new Text(auctions));
	}
}