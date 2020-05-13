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
		
		
		return Job1Data;
			 
	 }
	
//	private JavaPairRDD<String, Double> minPrices(){
//		
//		JavaRDD<Stock> Job1Data = buildStocks();
//		 
//		JavaPairRDD<String, Double> min = Job1Data.mapToPair(stock -> new Tuple2<>(stock.getTicker(), stock.getMinPrezzo()))
//				.reduceByKey((a,b)-> Utility.minElem(a,b));
//	
//		//Map<String, Double> s = min.collectAsMap();
//		
////		for(String tick: s.keySet()) {
////		
////			System.out.println(tick + ":" + s.get(tick));
////		}
////		
//
//		return min;//Ok stampa ma prende il valore massimo non il minimo
//		
//			
//	}
	
//	private JavaPairRDD<String, Double> maxPrices(){
//		
//		JavaRDD<Stock> Job1Data = buildStocks();
//		
//		JavaPairRDD<String, Double> max = Job1Data.mapToPair(stock -> new Tuple2<>(stock.getTicker(), stock.getMaxPrezzo()))
//				.reduceByKey((a,b) -> Utility.maxElem(a, b));
//		
//		Map<String, Double> sd = max.collectAsMap();
//		
//		for(String t: sd.keySet()) {
//			System.out.println(t + ":" + sd.get(t));
//			
//		}
//		
//		return max;
//	}
//	
	public JavaPairRDD<Tuple2<String,Integer>,Double>variationOdds(){
		
		JavaRDD<Stock> Job1Data = buildStocks();
		//CON QUESTO RDD HO PRESO IL TICKER CON L'ANNO ASSOCIATO DI UN RELATIVO STOCK
		JavaPairRDD<Tuple2<String,Integer>,Stock> st = Job1Data.mapToPair(stock -> new Tuple2<>(
			new Tuple2<>(stock.getTicker(),stock.getData().getYear()+1900),stock));
		
		JavaPairRDD<Tuple2<String, Integer>, Double> yearMinClose = st.reduceByKey((a,b) -> Utility.minDate(a, b))
				.mapToPair(tup -> new Tuple2(tup._1(), tup._2().getPrezzoChiusura()));
			
		JavaPairRDD<Tuple2<String,Integer>, Double> yearMaxClose = st.reduceByKey((a,b) -> Utility.maxDate(a, b))
				.mapToPair(tup -> new Tuple2(tup._1(), tup._2().getPrezzoChiusura()));
		
		//Ora che ho tutti gli elementi devo svolgere l'operazione per il calcolo della variaizone Percentuale
		
		JavaPairRDD<Tuple2<String,Integer>, Double>  yearMeanDifference = yearMinClose.join(yearMaxClose)
				.mapToPair(tup -> new Tuple2(tup._1(),((tup._2()._2() - tup._2()._1())/tup._2()._1())*100));
		
		Map<Tuple2<String,Integer>,Double> mapDifference = yearMeanDifference.collectAsMap();
		
		for (Tuple2<String,Integer> pd : mapDifference.keySet()) {
			System.out.println(pd + ":" + mapDifference.get(pd));
		}
		
		
		return yearMeanDifference;
		
		
	}
	
	
	
		
	

}
