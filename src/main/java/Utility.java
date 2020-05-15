

public class Utility {
	
	public static Double minElem(Double a, Double b) {
		
		if(a < b) {
			return a;
		}
		
		else {
			return b;
		}
		
		
	}
	
	public static Double maxElem(Double a, Double b) {
		
		if(a > b) {
			return a;
			
		}
		else {
			return b;
		}
	}
	
	//Creare due metodi per prendere data minima e data massima
	public static Stock minDate(Stock a, Stock b) {
		if(a.getData().compareTo(b.getData())< 0) {
			return a;
			
		}
		else {
			return b;
		}
	}
	
	public static Stock maxDate (Stock a, Stock b) {
		if(a.getData().compareTo(b.getData())> 0) {
			return a;
			
		}
		else {
			return b;
		}
	}
	
	
}
