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
			
			//aici vine transformarea din edi in xml si se intoarce continutul din xml
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
}
