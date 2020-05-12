import java.io.Serializable;
import java.util.Map;

import org.apache.parquet.it.unimi.dsi.fastutil.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class Job1  {
	
	private String file1;
	public Job1(String file1) {
		this.file1 = file1;
	}
	
	
	public JavaRDD<Stock> buildStocks() {
		
		SparkConf sparkConf = new SparkConf().setAppName("Job1");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		
		//Devo effettuare un .map per crearmi una lista di stock e non di linee
		JavaRDD<Stock> Job1Data = sc.textFile(file1).map(line -> Parse.parseFile1(line))
				.filter(stock -> stock!=null)
				.filter(stock -> stock.getData().getYear()+1900 >=2008);
		
		System.out.println(Job1Data.count());
		
		return Job1Data;
			 
	 }
	
	public JavaPairRDD<String, Double> minPrices(){
		
		JavaRDD<Stock> Job1Data = buildStocks();
		 
		JavaPairRDD<String, Double> min = Job1Data.mapToPair(stock -> new Tuple2<>(stock.getTicker(), stock.getMinPrezzo()))
				.reduceByKey((a,b)-> Utility.minElem(a,b));
	
		Map<String, Double> s = min.collectAsMap();
		
		System.out.println(min.count());
		for(String tick: s.keySet()) {
		
			System.out.println(tick + ":" + s.get(tick));
		}
		

		return min;
		
		
		
		
				
		
						
	}
	
		
	

}
