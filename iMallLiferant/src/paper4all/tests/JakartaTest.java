package paper4all.tests;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

public class JakartaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			FTPClient f = new FTPClient();
		    f.connect("sokrates2.local.cs.hs-rm.de");
		    f.login("hu000004", "1q2w3e");
		    FTPListParseEngine engine = f.initiateListParsing("/in");
	
		    while (engine.hasNext()) 
		    {
		       FTPFile[] files = engine.getNext(25);  // "page size" you want
		       //do whatever you want with these files, display them, etc.
		       //expensive FTPFile objects not created until needed.
		       
		       for(int i=0;i<25;i++)
		       {
		    	   System.out.println(files[i].getName());
		       }
		    
		    }
		    
		    f.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
