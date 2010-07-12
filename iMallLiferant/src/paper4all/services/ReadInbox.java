package paper4all.services;

import java.io.File;
import java.io.FilenameFilter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="ReadInboxWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class ReadInbox 
{
	
	@WebMethod(operationName="read-from-inbox") 
	@WebResult(name = "filename-result") 
	public String fileToProcess() 
	{ 
		File f = new File("in");
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
			return null;
	}
}
