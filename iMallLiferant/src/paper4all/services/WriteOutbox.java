package paper4all.services;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="WriteOutboxWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class WriteOutbox 
{
	
	@WebMethod(operationName="write-to-outbox") 
	@WebResult(name = "success-result") 
	public boolean writeToOutbox(String input, String filename) 
	{ 
		File f = new File("out/"+filename);
		try 
		{
			FileWriter writer = new FileWriter(f);
			writer.write(input);
			writer.close();
			return true;
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
}
