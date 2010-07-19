package paper4all.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

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
		       
		       
		       for(int i=0;i<files.length;i++)
		       {
		    	   File file = new File("in/"+ files[i].getName());
		    	   OutputStream out = new FileOutputStream(file);
		    	   
		    	   System.out.println(files[i].getName());
		    	   
		    	   if(f.retrieveFile("in/"+files[i].getName(), out))
		    	   {
		    		   //
		    		   out.close();
		    		   /*String input = "";
			   		   BufferedReader in = new BufferedReader(new FileReader(file));
			   		   String str;
			   		   while ((str = in.readLine()) != null) 
			   		   {
			   		      input += str;
			   		   }
			   		   in.close();
			   		   //f.deleteFile("in/" + files[0].getName());
			   		    */
			   		   
		    	   }	    
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
