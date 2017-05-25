package auctions;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class AuctionsJob
{
	private static String serverdir = "server";
	private static String auctiondir = "auctions";
	private static String auctionStatsDir = "auctions_stats";
	private static String outputDir = "output";
	
	public static void main(String[] args) throws Exception 
	{

		getAuctionData(serverdir, auctiondir);
		
		calculateAuctionStats(auctiondir, auctionStatsDir);
		
		generateReport(auctiondir, outputDir);
	}
	
	/**
	 * getAuctionData
	 * 
	 * Shamelessly unnecessary map/reduce job. However it does do distribution of by server such that
	 * each reducer is responsible for performing the webservice calls and retrieving each servers auction data.
	 */
	public static void getAuctionData(String indir, String outdir)
	{
		System.out.println(">>> getAuctionData");
		
		try
		{
			Configuration conf = new Configuration();
			
			Job job = new Job(conf, "GetAuctionData");
			job.setJarByClass(AuctionsJob.class);
			job.setMapperClass(GetAuctionsMapper.class);
			job.setReducerClass(GetAuctionsReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			
			org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path(indir));
			org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new Path(outdir));

			job.waitForCompletion(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("<<< getAuctionData");
	}
	
	public static void calculateAuctionStats(String indir, String outdir)
	{
		System.out.println("calculateAuctionStats....");

		try
		{
			Configuration conf = new Configuration();
			
			Job job = new Job(conf, "AuctionStats");
			job.setJarByClass(AuctionsJob.class);
			job.setMapperClass(AuctionStatsMapper.class);
			job.setReducerClass(AuctionStatsReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			
			org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job,  new Path(indir));
			org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job,  new Path(outdir));

			job.waitForCompletion(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void generateReport(String indir, String outdir)
	{
		System.out.println(">>> generateReport");
		
		try
		{
			Configuration conf = new Configuration();
			
			Job job = new Job(conf, "GenerateReport");
			job.setJarByClass(AuctionsJob.class);
			job.setMapperClass(AuctionStatsReporterMapper.class);
			job.setReducerClass(AuctionStatsReporterReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			
			FileSystem fs = FileSystem.get(conf);
			
			Path job1Path = new Path(auctiondir);
			Path job2Path = new Path(auctionStatsDir);
			
			
			if (fs.exists(job1Path) && fs.exists(job2Path))
			{
				
				FileUtil.copyMerge(fs, job2Path, fs, job1Path, false, conf, null);
			}
			
			org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path(indir));
			org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new Path(outdir));
			
			job.waitForCompletion(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("<<< generateReport");		
	}
	
}