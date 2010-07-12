package paper4all.services;

import java.io.File;
import java.io.FileOutputStream;
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
		    FTPListParseEngine engine = f.initiateListParsing("/in");
	
		    while (engine.hasNext()) 
		    {
		       FTPFile[] files = engine.getNext(100);  // "page size" you want
		       //do whatever you want with these files, display them, etc.
		       //expensive FTPFile objects not created until needed.
		       
		      // f.retrieveFile(arg0, arg1)
		       
		       for(int i=0;i<files.length;i++)
		       {
		    	   System.out.println(files[i].getName());
		    	   
		       }
		       
		       if(files.length>0)
		       {
		    	   System.out.println(files[0].toString());
		    	   File file = new File("in/temp.edi");
		    	   OutputStream out = new FileOutputStream(file);
		    	   
		    	   if(f.retrieveFile("in/"+files[0].getName(), out))
		    	   {
		    		   System.out.println("success");
		    		   return file.getName();
		    	   }
		    	   else
		    		   System.out.println("failure");		    		   
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
