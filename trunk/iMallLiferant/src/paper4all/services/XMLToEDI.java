package paper4all.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

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
			
			//aici vine transformarea din xml in edi si se intoarce continutul din edi
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
}
