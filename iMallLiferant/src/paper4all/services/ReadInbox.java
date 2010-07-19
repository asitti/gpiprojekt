package paper4all.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.OutputStream;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

@WebService(name="ReadInboxWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class ReadInbox 
{
	
	@WebMethod(operationName="read-from-inbox") 
	@WebResult(name = "filename-result") 
	public String fileToProcess() 
	{ 
		try
		{
			FTPClient f = new FTPClient();
		    f.connect("sokrates2.local.cs.hs-rm.de");
		    f.login("hu000004", "1q2w3e");
		    
		    FTPFile[] files = f.listFiles("in");
		    
		    if(files.length>0)
		    {
		    	 File file = new File("in/temp.edi");
		    	 OutputStream out = new FileOutputStream(file);
		    	 if(f.retrieveFile("in/"+files[0].getName(), out))
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
		    		// f.deleteFile("in/" + files[0].getName());
		    		 return input;
		    	   }	    
		    }
		    f.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
		/*File f = new File("in");
		if(f.isDirectory())
		{
			String[] files = f.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) 
				{
					if (name.endsWith(".xml")) return true;
					   return false;
				}
			});
			return files[0];
		}
		else
			return null;*/
	}
}
