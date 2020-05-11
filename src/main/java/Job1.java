import java.io.Serializable;

import org.apache.parquet.it.unimi.dsi.fastutil.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Job1  {
	
	private String job1File = ("/home/luca/daily-historical-stock-prices-1970-2018/historical_stock_prices.csv");
	
	public Job1(String file) {
		this.job1File = file;
	}
	
	
	public JavaRDD<Stock> buildStocks() {
		
		SparkConf sparkConf = new SparkConf().setAppName("Job1");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		
		//Devo effettuare un .map per crearmi una lista di stock e non di linee
		JavaRDD<Stock> Job1Data = sc.textFile(job1File).map(line -> Parse.parseFile1(line))
				.filter(stock -> stock!=null)
				.filter(stock -> stock.getData().getYear()+1900 >=2008);
		
		return Job1Data;
			 
	 }
	
	public JavaRDD<Double> minPrices(){
		
		JavaRDD<Stock> Job1Data = buildStocks();
		
		JavaRDD<Double> min =
				Job1Data.flatMap(stock -> Arrays.asList(stock.getMinPrezzo().iterator()));
		
						
	}
	
		
	

}
