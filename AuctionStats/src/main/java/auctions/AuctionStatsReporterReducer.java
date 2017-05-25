package auctions;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import auctions.model.Auction;
import auctions.model.AuctionMean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuctionStatsReporterReducer extends Reducer<Text, Text, Text, Text> 
{

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException 
	{
			//100c = 1s, 100s = 1g
			//currency is of the form (x)g(2)s(2)c (e.g. 20 gold 15 silver, 10 copper)
			double stdDeviationPart = 0.0;
			double meanUnitBuyout = 0;
			int meanQuantity = 0;
			
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
				String json = value.toString();
				
				//json is either the mean or its an auction row
				if (json.contains("meanBuyout"))
				{
					AuctionMean meanAuction = gson.fromJson(json,  AuctionMean.class);
					meanUnitBuyout = meanAuction.getMeanBuyout();
					meanQuantity = meanAuction.getMeanQuantity();
				}
				else
				{
					Auction auction = gson.fromJson(json, Auction.class);
					
					double unitBuyout = (double) auction.getBuyout()/auction.getQuantity();
					
					double deviation = Math.pow((unitBuyout - meanUnitBuyout), 2);
					
					stdDeviationPart += deviation;
					
					
					//set the min
					if (minBuyout == 0 || unitBuyout < minBuyout)
					{
						minBuyout = unitBuyout;
						minAuction = auction;
					}
					
					//set the max
					if (maxBuyout == 0 || unitBuyout > maxBuyout)
					{
						maxBuyout = unitBuyout;
						maxAuction = auction;
					}
					
					//increment number of auctions
					totalAuctions++;
					
				}
			}
			
			//calculate standard deviation
			double standardDeviation = Math.sqrt(stdDeviationPart/totalAuctions);

			//build an average auction item
			AuctionMean meanAuction = new AuctionMean();
			
			meanAuction.setMeanBuyoutStr(getCurrency((long)meanUnitBuyout));
			meanAuction.setMeanBuyout((long) meanUnitBuyout); //cast to long ("int")
			meanAuction.setMeanQuantity(meanQuantity);
			meanAuction.setStandardDeviation(String.valueOf(standardDeviation));
			
			//emit an average auction
			String meanJSON = gson.toJson(meanAuction);
			Text outputMean = new Text(meanJSON);
			context.write(key,  outputMean);
			
			//emit the min auction
			String minAuctionJSON = gson.toJson(minAuction);
			Text outputMin = new Text(minAuctionJSON);
			context.write(key, outputMin);
			
			//emit the max auction
			String maxAuctionJSON = gson.toJson(maxAuction);
			Text outputMax = new Text(maxAuctionJSON);
			context.write(key, outputMax);
	}
	
	private static String getCurrency(long buyout)
	{
		StringBuffer currency = new StringBuffer();
		
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
				currency.append(copper);
				currency.append("c");
				break;
			case 3:
			case 4:
				copper = longStr.substring(length - 2);
				silver = longStr.substring(0, length-2);
				currency.append(silver);
				currency.append("s ");
				currency.append(copper);
				currency.append("c");
				break;
			default:
				copper = longStr.substring(length - 2);
				silver = longStr.substring(length-4, length-2);
				gold = longStr.substring(0, length-4);
				currency.append(gold);
				currency.append("g ");
				currency.append(silver);
				currency.append("s ");
				currency.append(copper);
				currency.append("c");
				break;
		}
		
		return currency.toString();
	}
}