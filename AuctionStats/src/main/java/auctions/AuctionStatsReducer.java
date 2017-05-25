package auctions;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import auctions.model.Auction;
import auctions.model.AuctionMean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuctionStatsReducer extends Reducer<Text, Text, Text, Text> 
{
	
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException 
	{
			//100c = 1s, 100s = 1g
			//currency is of the form (x)g(2)s(2)c (e.g. 20 gold 15 silver, 10 copper)
			
			int totalQuantity = 0; //total items encountered (sum of all quantities)
			
			double unitBuyoutTotal = 0; //total sum of unit buyout (i.e. buyout/quantity)
			double unitBidTotal = 0; //total sum of unit bids
			
			int totalAuctions = 0;
			
			double minBuyout = 0;
			Auction minAuction = null;
			
			double maxBuyout = 0;
			Auction maxAuction = null;
		
			//initialize the gson library for json conversion
			Gson gson = new GsonBuilder().create();
			
			for (Text value : values)
			{
				//get the json string
				String jsonAuction = value.toString();
				
				//generate the auction item using gson lib	
				Auction auction = gson.fromJson(jsonAuction, Auction.class);
				
				//calculate the unit price and add it to the total
				double unitPrice = (double) auction.getBuyout()/auction.getQuantity();
				unitBuyoutTotal += unitPrice;
				
				unitBidTotal += ( (double) auction.getBid()/auction.getQuantity() );
				
				
				//set the min
				if (minBuyout == 0 || unitPrice < minBuyout)
				{
					minBuyout = unitPrice;
					minAuction = auction;
				}
				
				//set the max
				if (maxBuyout == 0 || unitPrice > maxBuyout)
				{
					maxBuyout = unitPrice;
					maxAuction = auction;
				}
				
				
				//increment the count of this item based on the quantity
				totalQuantity += auction.getQuantity();
				
				//increment number of auctions
				totalAuctions++;
			
				//emit original value for subsequent run
				//context.write(key, value);
			}

			//build an average auction item
			AuctionMean meanAuction = new AuctionMean();
			meanAuction.setItem(key.toString());
			double meanBuyout = (double) unitBuyoutTotal/totalAuctions;
			meanAuction.setMeanBuyout((long) meanBuyout);
			meanAuction.setMeanQuantity((int) totalQuantity/totalAuctions);
			
			//emit an average auction
			String meanJSON = gson.toJson(meanAuction);
			Text outputAverage = new Text(meanJSON);
			context.write(key,  outputAverage);

			
			/*
			//emit the min auction
			String minAuctionJSON = gson.toJson(minAuction);
			Text outputMin = new Text(minAuctionJSON);
			context.write(key,  outputMin);
			
			//emite the max auction
			String maxAuctionJSON = gson.toJson(maxAuction);
			Text outputMax = new Text(maxAuctionJSON);
			context.write(key, outputMax);
			*/
			
	}
}