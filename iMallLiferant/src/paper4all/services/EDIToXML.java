package paper4all.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="EDIToXMLWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class EDIToXML
{
	
	@WebMethod(operationName="edi-to-xml") 
	@WebResult(name = "filenameXML-result") 
	public String ediToXml(String ediInput) 
	{ 
		try
		{
			FileWriter f = new FileWriter(new File("temp.edi"));
			f.write(ediInput);
			f.close();
			
			//aici vine transformarea din edi in xml si se intoarce continutul din xml
			
			Process p = Runtime.getRuntime().exec("ruby C:/Ruby187/lib/ruby/gems/1.8/gems/edi4r-0.9.5.2/bin/edi2xml.rb temp.edi");
			
			BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
			
			String xmlResult = "";
            int i;
            while ((i = bis.read()) != -1) {
                xmlResult += (char)i;
            }
            bis.close();
            
            return xmlResult;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
}
