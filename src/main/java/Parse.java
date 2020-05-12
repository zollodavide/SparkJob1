import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parse {
	
	//Da una linea del CSV mi deve creare uno stock così io succesivamente itero su stock e non su linee
	
	public static Stock parseFile1(String line) throws ParseException{
		
		String[] parts = line.split(",");
		Stock out = new Stock();
		try {
			
				out.setTicker(parts[HistoricalStockPricesCostants.TICKER]);
				out.setPrezzoChiusura(Double.parseDouble(parts[HistoricalStockPricesCostants.CLOSE]));
				out.setPrezzoApertura(Double.parseDouble(parts[HistoricalStockPricesCostants.OPEN]));
				out.setMinPrezzo(Double.parseDouble(parts[HistoricalStockPricesCostants.LOWTHE]));
				out.setMaxPrezzo(Double.parseDouble(parts[HistoricalStockPricesCostants.HIGHTHE]));
				out.setVolume(Integer.parseInt(parts[HistoricalStockPricesCostants.VOLUME]));
		
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date data;
				
				data = format.parse(parts[HistoricalStockPricesCostants.DATE]);
				out.setData(data);
		
		
				return out;
		}
		catch(Exception e) {
			return null;
		}
		
		
		
	}

}
