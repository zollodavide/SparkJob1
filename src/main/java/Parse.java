import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parse {
	
	//Da una linea del CSV mi deve creare uno stock così io succesivamente itero su stock e non su linee
	
	public static Stock parseFile1(String line) throws ParseException{
		
		String[] parts = line.split(",");
		Stock stock = new Stock();
		stock.setTicker(parts[HistoricalStockPricesCostants.TICKER]);
		stock.setPrezzoChiusura(Double.parseDouble(parts[HistoricalStockPricesCostants.CLOSE]));
		stock.setPrezzoApertura(Double.parseDouble(parts[HistoricalStockPricesCostants.OPEN]));
		stock.setMinPrezzo(Double.parseDouble(parts[HistoricalStockPricesCostants.LOWTHE]));
		stock.setMaxPrezzo(Double.parseDouble(parts[HistoricalStockPricesCostants.HIGHTHE]));
		stock.setVolume(Integer.parseInt(parts[HistoricalStockPricesCostants.VOLUME]));
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date data;
		
		data = format.parse(parts[HistoricalStockPricesCostants.DATE]);
		stock.setData(data);
		
		
		return stock;
		
		
		
	}

}
