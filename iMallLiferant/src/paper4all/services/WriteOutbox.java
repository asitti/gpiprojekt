package paper4all.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;

@WebService(name="WriteOutboxWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class WriteOutbox 
{
	
	@WebMethod(operationName="write-to-outbox") 
	@WebResult(name = "success-result") 
	public boolean writeToOutbox(String fileInput) 
	{ 
		FtpClient ftp = null;
		try 
		{
	        ftp = new FtpClient("sokrates2.local.cs.hs-rm.de");
	        System.out.println(" OK");
	        ftp.login("hu000004", "1q2w3e");
	        System.out.println(" OK");
        
	        ftp.binary();
	        System.out.println(" OK");
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        String fileName = sdf.format(cal.getTime());

	        
	        FileWriter writer = new FileWriter(new File(fileName));
	        writer.write(fileInput);
	        writer.close();
	        
	        BufferedInputStream in = new BufferedInputStream(
	          new FileInputStream(fileName));
	        TelnetOutputStream out = ftp.put("out/"+fileName);
	        byte buffer[] = new byte[2048];
	        int bytesRead = 0;
	        int bytesSend = 0;
	        int c=1;
	        while((bytesRead = in.read(buffer, 0, 2048))!=-1) 
	        {
	          out.write(buffer, 0, bytesRead);
	          bytesSend += bytesRead;
	        }
	        out.flush();
	        out.close();
	        in.close();
	        buffer = null;
	        return true;

	      }
	      catch(FtpLoginException e) 
	      {
	        System.out.print(" Failed: ");
	        System.err.println(e.getMessage());
	        return false;
	      } 
	      catch (IOException e) 
	      {
			// TODO Auto-generated catch block
	    	  
			e.printStackTrace();
			return false;
		}
		
						
	}
}
