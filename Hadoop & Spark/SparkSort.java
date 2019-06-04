import scala.Tuple2;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;


public final class SparkSort {
  private static final String dinu = "My code!";

  public static void main(String[] args) throws Exception {

    if (args.length < 2) {
      System.err.println("Please specify both input and output files");
      System.exit(1);
    }

	
    //this or the context ??
	/*SparkSession spark = SparkSession
      .builder()
      .appName("SparkSort")
      .getOrCreate();
	  */
	 
	//initialize the configuration	
	SparkConf sparkConf = new SparkConf().setAppName("SparkSort");
	//initialize Java Spark context
    JavaSparkContext jsc = new JavaSparkContext(sparkConf);
	
	double timer_start = System.currentTimeMillis();
	//input file
	JavaRDD<String> textFile = jsc.textFile(args[0]);
	
	//Convert to key value pairs and sort them
	JavaPairRDD<String, String> SortedOp = textFile.flatMap(s -> Arrays.asList(s.split("\n")).iterator()).mapToPair(word -> new Tuple2<>(word.substring(0,10), word.substring(10,98))).reduceByKey((a, b) -> a + b );
	
	//SortedOp = SortedOp.flatMap(s -> Arrays.asList(s._1() + "  " + t._2().trim()+ "\t").iterator());
	//output file
	SortedOp.saveAsTextFile(args[1]);
	//end Time
	double timer_stop = System.currentTimeMillis();
	double total_time = timer_stop - timer_start;
	System.out.println("Time taken by Spark:" + total_time);
  
  }
}