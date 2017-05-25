package auctions;

import java.io.IOException;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import auctions.model.ServerAuctionFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Test {

    public static void main(String[] args) throws IOException {

    	/*
        try(Reader reader = new InputStreamReader(Test.class.getResourceAsStream("illidan_auctions.json"), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            ServerAuctions p = gson.fromJson(reader, ServerAuctions.class);
            
            System.out.println(p.getAlliance().getAuctions().get(0).getBuyout());
        }
        */
    	/*
    	// create the rest client instance
    	RestClient client = new RestClient();

    	// create the resource instance to interact with
    	Resource resource = client.resource("http://us.battle.net/api/wow/auction/data/medivh");

    	// issue the request
    	String response = resource.accept("text/plain").get(String.class);
    	
    	System.out.println(response);
    	
		Gson gson = new GsonBuilder().create();
		ServerAuctionFile file = gson.fromJson(response, ServerAuctionFile.class);
    	
		String url = file.getFiles().get(0).getUrl();
		System.out.println("url: " + url);
    	
		resource = client.resource(url);
		
		response = resource.accept("application/json").get(String.class);
		
		System.out.println(response);

	*/
    	getCurrency(12222550);
    }
    
	private static String getCurrency(long buyout)
	{
		String copper = null;
		String silver = null;
		String gold = null;
		
		String longStr = String.valueOf(buyout);
		int length = longStr.length();
		
		switch (length) 
		{
			case 1:
			case 2:
				copper = longStr;
				break;
			case 3:
			case 4:
				copper = longStr.substring(length - 2);
				silver = longStr.substring(0, length-2);
				break;
			default:
				copper = longStr.substring(length - 2);
				silver = longStr.substring(length-4, length-2);
				gold = longStr.substring(0, length-4);
				break;
		}
	
		System.out.println("c: " + copper);
		System.out.println("s: "+ silver);
		System.out.println("g:"+ gold);
		
		StringBuffer currency = new StringBuffer();
		
		return currency.toString();
		
		
	}
}