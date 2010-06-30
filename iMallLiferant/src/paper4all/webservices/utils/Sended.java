package paper4all.webservices.utils;

public class Sended 
{
	private double preis;
	private int verlangt;
	private int geschickt;
	private String gtin;
	private String sgtin;
	
	public Sended(String gtin, String sgtin, double preis, int verlangt, int geschickt)  
	{
		this.preis = preis;
		this.verlangt = verlangt;
		this.geschickt = geschickt;
		this.gtin = gtin;
		this.sgtin = sgtin;
	}
	
	public Sended()
	{
		
	}

	public double somthing()
	{
		return 0.0;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getVerlangt() {
		return verlangt;
	}

	public void setVerlangt(int verlangt) {
		this.verlangt = verlangt;
	}

	public int getGeschickt() {
		return geschickt;
	}

	public void setGeschickt(int geschickt) {
		this.geschickt = geschickt;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getSgtin() {
		return sgtin;
	}

	public void setSgtin(String sgtin) {
		this.sgtin = sgtin;
	}
	

	
	
	
	
	
	

}
