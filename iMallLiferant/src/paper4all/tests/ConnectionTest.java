package paper4all.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

public class ConnectionTest {

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
		    FTPFile[] files = f.listFiles("in");
		    File file = new File("in/619.edi");
	    	 OutputStream out = new FileOutputStream(file);
	    	 if(f.retrieveFile("in/619.edi", out))
	    	 {
	    		 out.close();
	    		 String input = "";
	    		 BufferedReader in = new BufferedReader(new FileReader(file));
	    		 String str;
	    		 while ((str = in.readLine()) != null) 
	    		 {
	    			 input += str;
	    		 }
	    		 in.close();
	    		 System.out.println(input);
	    		// f.deleteFile("in/" + files[0].getName());
	    		 
	    	   }	    
		    
		    for(int i=0; i< files.length; i++)
		    {
		    	System.out.println(files[i].getName());
		    	//System.out.println(f.deleteFile("out/"+files[i].getName()));
		    }
		    
		    
		    f.disconnect();
		  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

	}

}
