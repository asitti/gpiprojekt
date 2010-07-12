package paper4all.webservices.utils;

import paper4all.rubyParser.Interchange;

public class MyInterchange
{

	Interchange inter;
	
	public MyInterchange(Interchange inter)
	{
		this.inter = inter;
	}

	public MyInterchange(){}
	
	public Interchange getInter() {
		return inter;
	}

	public void setInter(Interchange inter) {
		this.inter = inter;
	}
	
	
}
