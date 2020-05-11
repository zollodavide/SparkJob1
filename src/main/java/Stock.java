import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.hadoop.io.Writable;

public class Stock implements Serializable{
	
	private String ticker;
	private double minPrezzo;
	private double maxPrezzo;
	private int volume;
	private double prezzoChiusura;
	private double prezzoApertura;
	private Date data;
	

	public Stock(String ticker, double minPrezzo, double maxPrezzo, int volume, double prezzoChiusura,double prezzoApertura, Date data) {
		super();
		this.ticker = ticker;
		this.minPrezzo = minPrezzo;
		this.maxPrezzo = maxPrezzo;
		this.volume = volume;
		this.prezzoChiusura = prezzoChiusura;
		this.prezzoApertura = prezzoApertura;
		this.data = data;
	}

	
	public Stock() {
		super();
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public Date getData() {
		return data;
	}
	
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
		
	}
	
	public String getTicker() {
		return ticker;
	}




	public double getMinPrezzo() {
		return minPrezzo;
	}

	public void setMinPrezzo(double minPrezzo) {
		this.minPrezzo = minPrezzo;
	}

	public double getMaxPrezzo() {
		return maxPrezzo;
	}

	public void setMaxPrezzo(double maxPrezzo) {
		this.maxPrezzo = maxPrezzo;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public double getPrezzoApertura() {
		return prezzoApertura;
	}
	
	public void setPrezzoApertura(double prezzoApertura) {
		this.prezzoApertura = prezzoApertura;
	}

	public double getPrezzoChiusura() {
		return prezzoChiusura;
	}
	
	public void setPrezzoChiusura(double prezzoChiusura) {
		this.prezzoChiusura = prezzoChiusura;
	}

	
	
	@Override 
	public String toString() {
		return getMinPrezzo()+ "," + getMaxPrezzo()+ "," +getPrezzoChiusura()+ "," +getVolume()+ "," +
				getData();
	}

//	@Override
//	public void write(DataOutput out) throws IOException {
//		out.writeDouble(minPrezzo);
//		out.writeDouble(maxPrezzo);
//		out.writeInt(volume);
//		out.writeDouble(prezzoChiusura);
//		out.writeInt(anno);
//		out.writeInt(mese);
//		out.writeInt(giorno);
//	}
//
//	@Override
//	public void readFields(DataInput in) throws IOException {
//		minPrezzo = in.readDouble();
//		maxPrezzo = in.readDouble();
//		volume = in.readInt();
//		prezzoChiusura = in.readDouble();
//		anno = in.readInt();
//		mese = in.readInt();
//		giorno = in.readInt();
//		
//	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		long temp;
		temp = Double.doubleToLongBits(maxPrezzo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minPrezzo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(prezzoChiusura);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + volume;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		
		if (Double.doubleToLongBits(maxPrezzo) != Double.doubleToLongBits(other.maxPrezzo))
			return false;
		if (Double.doubleToLongBits(minPrezzo) != Double.doubleToLongBits(other.minPrezzo))
			return false;
		if (Double.doubleToLongBits(prezzoChiusura) != Double.doubleToLongBits(other.prezzoChiusura))
			return false;
		if (volume != other.volume)
			return false;
		return true;
	}


}
