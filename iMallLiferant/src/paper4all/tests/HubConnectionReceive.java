package paper4all.tests;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;
import sun.net.ftp.FtpProtocolException;

public class HubConnectionReceive 
{
	public static void main(String argv[]) 
	{

		try 
		{
			System.out.print("Connecting to http://sokrates2.local.cs.hs-rm.de/...");
			SunFtpWrapper ftp;
			ftp = new SunFtpWrapper("sokrates2.local.cs.hs-rm.de");
			System.out.println(" OK");
	        System.out.println("Sending user and password...");
			ftp.login("hu000004", "1q2w3e");
			ftp.cd("in");
			
			//ftp.deleteFile("in1.xml");
			Vector<String> vector = ftp.listRaw();
			for(String s : vector)
			{
				System.out.println(s);
			}
			 
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	      /*FtpClient ftp = null;
	      try 
	      {
	        System.out.print("Connecting to http://sokrates2.local.cs.hs-rm.de/...");
	        ftp = new FtpClient("sokrates2.local.cs.hs-rm.de");
	        System.out.println(" OK");
	        System.out.print("Sending user and password...");
	        ftp.login("hu000004", "1q2w3e");
	        System.out.println(" OK");
	        System.out.print("Setting binary transfer mode...");
	        
	         ftp.binary();
	        //System.out.println(" OK");

	        BufferedOutputStream in = new BufferedOutputStream(
	          new FileOutputStream("edi/out.xml")
	        );
	        System.out.println("Reading file");
	        ftp.cd("in");
	        TelnetInputStream list = ftp.list();
	        
	        TelnetInputStream input = ftp.get("in1.xml");
	        
	        
	        
	        byte buffer[] = new byte[2048];
	        int bytesRead = 0;
	        int c=1;
	        while((bytesRead = input.read(buffer, 0, 2048))!=-1) {
	          in.write(buffer, 0, bytesRead);
	          System.out.print(".");
	        }
	        in.flush();
	        in.close();
	        in.close();
	        
	        byte buffer[] = new byte[2048];
	        int bytesRead = 0;
	        int c=1;
	        while((bytesRead = list.read(buffer, 0, 2048))!=-1) {
	          in.write(buffer, 0, bytesRead);
	          System.out.print(".");
	        }
	        in.flush();
	        in.close();
	        in.close();
	        

	      }
	      catch(FtpLoginException e) 
	      {
	        e.printStackTrace();
	      }
	      catch(FtpProtocolException e) 
	      {
	        ftp = null;
	        e.printStackTrace();
	      }
	      catch(IOException e) 
	      {
	        e.printStackTrace();
	      }
	      finally
	      {
	        if(ftp != null) 
	        {
	          try 
	          {
	            System.out.print("Closing connection...");
	            ftp.closeServer();
	            System.out.println(" OK");
	          }
	          catch(Exception e) 
	          {
	            e.printStackTrace();
	          }
	        }
	        ftp = null;
	      }*/

	    }


}
