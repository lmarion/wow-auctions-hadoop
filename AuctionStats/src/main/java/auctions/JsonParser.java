package auctions;

import auctions.model.ServerAuctions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser 
{

	public static ServerAuctions parseJSON(String input)
	{
		ServerAuctions auctions = null;
		 
		Gson gson = new GsonBuilder().create();
		auctions = gson.fromJson(input, ServerAuctions.class);
		
		return auctions;
	}
	
	public static String generateJSON(ServerAuctions serverAuctions)
	{
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(serverAuctions);
		
		return json;
	}
}
