import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.parquet.it.unimi.dsi.fastutil.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.sparkproject.guava.collect.Sets;


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
	
	public void run() {
		JavaRDD<Stock> Job1Data = buildStocks();

		JavaPairRDD<String,	Double> minPrices = minPrices(Job1Data);
		JavaPairRDD<String,	Double> maxPrices = maxPrices(Job1Data);
		JavaPairRDD<String, Double> variationOdds = variationOdds(Job1Data);
		JavaPairRDD<String, Long> meanVolume = meanVolume(Job1Data);
		
		JavaPairRDD<String, String> output =minPrices
				.join(maxPrices)
				.mapToPair(tup -> new Tuple2<>("Ticker: " +tup._1(),
						", Min Prices: " +  tup._2()._1()
						+", MaxPrices : " + tup._2()._2()));
		JavaPairRDD<String, String> output2 = output
				.join(variationOdds)
				.mapToPair(tup -> new Tuple2<>(tup._1(),tup._2()._1() + ",Variazione Percentuale: "+ tup._2()._2()));
		
		JavaPairRDD<String,String> output3 = output2
				.join(meanVolume)
				.mapToPair(tup -> new Tuple2<>(tup._1(), tup._2()._1() + "%, Volume Medio: " + tup._2()._2()));
		
						 

		
		stampa(output3);
	
	
	}

	private void stampa(JavaPairRDD<String,String> output3) {
		Map<String,String> out = output3.collectAsMap();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		for(String s : out.keySet()) 
			System.out.println(s + "\t" + out.get(s));
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}
	

	
	private JavaPairRDD<String, Double> minPrices(JavaRDD<Stock> Job1Data){
	
		 
		JavaPairRDD<String, Double> min = Job1Data.mapToPair(stock -> new Tuple2<>(stock.getTicker(), stock.getMinPrezzo()))
				.reduceByKey((a,b)-> Utility.minElem(a,b));
	
//		Map<String, Double> s = min.collectAsMap();
//		
//		for(String tick: s.keySet()) {
//		
//			System.out.println(tick + ":" + s.get(tick));
//		}
		

		return min;
		
			
	}

	private JavaPairRDD<String, Double> maxPrices(JavaRDD<Stock> Job1Data){
		
		
		JavaPairRDD<String, Double> max = Job1Data.mapToPair(stock -> new Tuple2<>(stock.getTicker(), stock.getMaxPrezzo()))
				.reduceByKey((a,b) -> Utility.maxElem(a, b));

//		Map<String, Double> sd = max.collectAsMap();
//		
//		for(String t: sd.keySet()) {
//			System.out.println(t + ":" + sd.get(t));
//			
//		}
		
		return max;
	}
	
	public JavaPairRDD<String,Double>variationOdds(JavaRDD<Stock> Job1Data){
		
		
		//CON QUESTO RDD HO PRESO IL TICKER CON L'ANNO ASSOCIATO DI UN RELATIVO STOCK
		JavaPairRDD<String,Stock> st = Job1Data.mapToPair(stock -> new Tuple2<>(			stock.getTicker(),stock));
	
		JavaPairRDD<String, Double> yearMinClose = st.reduceByKey((a,b) -> Utility.minDate(a, b))
				.mapToPair(tup -> new Tuple2(tup._1(), tup._2().getPrezzoChiusura()));
			
		JavaPairRDD<String, Double> yearMaxClose = st.reduceByKey((a,b) -> Utility.maxDate(a, b))
				.mapToPair(tup -> new Tuple2(tup._1(), tup._2().getPrezzoChiusura()));
		
		//Ora che ho tutti gli elementi devo svolgere l'operazione per il calcolo della variaizone Percentuale
		
		JavaPairRDD<String, Double>  yearMeanDifference = yearMinClose.join(yearMaxClose)
				.mapToPair(tup -> new Tuple2(tup._1(),((tup._2()._2() - tup._2()._1())/tup._2()._1())*100));
		
//		Map<String,Double> mapDifference = yearMeanDifference.collectAsMap();
//		
//		for (String pd : mapDifference.keySet()) {
//			System.out.println(pd + ":" + mapDifference.get(pd));
//		}
//		
		
		return yearMeanDifference;
		
		
	}
	
	public JavaPairRDD<String,Long> meanVolume (JavaRDD<Stock> Job1Data){
		
		
		JavaPairRDD<String,Integer> numVolumi = Job1Data.mapToPair
				(stock -> new Tuple2<>(stock.getTicker(), 1))
				.reduceByKey((s1,s2) -> (s1+s2));
		
		JavaPairRDD<String, Long> totVolume = Job1Data.mapToPair(stock -> new Tuple2<>(
			stock.getTicker(), stock.getVolume()))
			.reduceByKey((a,b) -> a+b);
		
		JavaPairRDD<String ,Long> tickerMeanVolume = totVolume.join(numVolumi)
				.mapToPair(tup -> new Tuple2<>(tup._1(), tup._2()._1()/tup._2()._2()));
		
		Map<String,Long> volume = tickerMeanVolume.collectAsMap();
		
		Map<String, Long> sorted = volume
		        .entrySet()
		        .stream()
		        .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
		        .collect(
		            Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
		                LinkedHashMap::new));
		
		
		
		return tickerMeanVolume;
			
	}
	
	
	
		
	

}
