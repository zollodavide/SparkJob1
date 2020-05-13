

import java.util.Date;

import scala.Serializable;


public class Stock implements Serializable{
	
	private static final long serialVersionUID = 132449349384928L;
	private String ticker;
	private Double minPrezzo;
	private Double maxPrezzo;
	private Long volume;
	private Double prezzoChiusura;
	private Double prezzoApertura;
	private Date data;
	

	public Stock() {
		
	}


	public String getTicker() {
		return ticker;
	}


	public void setTicker(String ticker) {
		this.ticker = ticker;
	}


	public Double getMinPrezzo() {
		return minPrezzo;
	}


	public void setMinPrezzo(Double minPrezzo) {
		this.minPrezzo = minPrezzo;
	}


	public Double getMaxPrezzo() {
		return maxPrezzo;
	}


	public void setMaxPrezzo(Double maxPrezzo) {
		this.maxPrezzo = maxPrezzo;
	}


	public Long getVolume() {
		return volume;
	}


	public void setVolume(Long volume) {
		this.volume = volume;
	}


	public Double getPrezzoChiusura() {
		return prezzoChiusura;
	}


	public void setPrezzoChiusura(Double prezzoChiusura) {
		this.prezzoChiusura = prezzoChiusura;
	}


	public Double getPrezzoApertura() {
		return prezzoApertura;
	}


	public void setPrezzoApertura(Double prezzoApertura) {
		this.prezzoApertura = prezzoApertura;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	

	

}
