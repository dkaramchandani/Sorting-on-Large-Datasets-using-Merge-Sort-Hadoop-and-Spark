import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class HadoopSort extends Configured implements Tool
{
	public int run(String[] args) throws Exception
	{
		if(args.length<2)  // check arguments
		{
			System.out.println("Please specify both input and output files");
			return -1;
		}

		JobConf config = new JobConf(HadoopSort.class);
		config.setJobName(this.getClass().getName());
		//System.out.println("Setting configuration parameters");
		config.setBoolean("mapreduce.map.output.compress",true);
		//Using Snappy codec
		config.set("mapred.map.output.compress.codec","org.apache.hadoop.io.compress.SnappyCodec");
		//setting map reduce task timeout to be 1 hr
		config.setInt("mapreduce.task.timeout",3600000);

		//assign input file
		FileInputFormat.setInputPaths(config, new Path(args[0]));
		
		config.setMapOutputKeyClass(Text.class);
		//System.out.println("Assigned MapOutput's");
		config.setMapOutputValueClass(Text.class);
		
		config.setOutputKeyClass(Text.class);
		//System.out.println("Assigned Output's Key and Value classes");
		config.setOutputValueClass(NullWritable.class);
		
		
		config.setMapperClass(MapperSorting.class);
		//System.out.println("Assigned Mapper and Reducer classes");
		config.setReducerClass(IdentityReducer.class);
		
		//System.out.println("Assigned Output Value class");
		config.setOutputValueClass(NullWritable.class);
		
		//assigning mappers and reducers
		config.setNumReduceTasks(4);
				
		config.setNumMapTasks(4);
		
		//assign output file
		FileOutputFormat.setOutputPath(config, new Path(args[1]));	
	
		JobClient.runJob(config);
		return 0;
	}

	public static void main(String[] args) throws Exception 
	{
		// Start Time
		long start = System.currentTimeMillis();

		int exitCode = ToolRunner.run(new HadoopSort(), args);
		
		// end Time
		long total = System.currentTimeMillis()-start;
		System.out.println("Time taken by Hadoop: " + total);
		System.exit(exitCode);
	}

	public static class MapperSorting extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable Key, Text Value, OutputCollector<Text, Text> output, Reporter rep) throws IOException
		{		
			String fileLine = Value.toString();
			
			String fileKey = fileLine.substring(0, 10);
			String fileValue = fileLine.substring(10);
						
			output.collect(new Text(fileKey), new Text(fileValue));
		}
	}

}
