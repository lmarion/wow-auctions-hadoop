package auctions;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import auctions.model.ServerAuctionFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetAuctionsMapper extends
		Mapper<WritableComparable, Writable, Text, Text> 
{
	private static String INIT_AUCTION_URL = "http://us.battle.net/api/wow/auction/data/";

	/**
	 * 
	 */
	public void map(WritableComparable key, Writable value, Context context)
			throws IOException, InterruptedException 
	{

		//retrieve the server name from the input
		Text input = (Text) value;
		
		String server = input.toString();
		server = URLEncoder.encode(server, "UTF-8"); //this appear to be a defect with the API
		server = server.replace("+",  "-"); //url encoding for space is not a "-" it is + or %20

		//build the initial url for data retrieval
		String url = INIT_AUCTION_URL + server;
		
		System.out.println(url);

		// create the rest client instance
    	RestClient client = new RestClient();

    	// create the resource instance to interact with
    	Resource resource = client.resource(url);

		//execute web-service call to retrieve auction data url
    	String response = resource.accept("application/json").get(String.class);
    	
    	//parse the object using gson json library (google's json lib)
		Gson gson = new GsonBuilder().create();
		ServerAuctionFile file = gson.fromJson(response, ServerAuctionFile.class);
    	
		//pulled from the JSON object
		String auctionUrl = file.getFiles().get(0).getUrl();
		System.out.println("auctionUrl: " + auctionUrl);
		
		Text nkey = new Text(server);
		Text nValue = new Text(auctionUrl);
		
		context.write(nkey,  nValue);
	}
}
