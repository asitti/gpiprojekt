package paper4all.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="XMLToEDIWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class XMLToEDI
{
	
	@WebMethod(operationName="xml-to-edi") 
	@WebResult(name = "filenameEDI-result") 
	public String xmlToEdi(String xmlInput) 
	{ 
		try
		{
			FileWriter f = new FileWriter(new File("temp.xml"));
			f.write(xmlInput);
			f.close();
			
			//aici vine transformarea din xml in edi si se intoarce continutul din edi
			
			Process p = Runtime.getRuntime().exec("ruby C:/Ruby187/lib/ruby/gems/1.8/gems/edi4r-0.9.5.2/bin/xml2edi.rb temp.xml");
			
			BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
			
			String ediResult = "";
            int i;
            while ((i = bis.read()) != -1) {
                ediResult += (char)i;
            }
            bis.close();
            
            return ediResult;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
}
