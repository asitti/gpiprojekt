package paper4all.tests;

import java.io.*;
import sun.net.ftp.*;
import sun.net.*;


public class HubConnectionSend 
{
	

    public static void main(String argv[]) {

      FtpClient ftp = null;
      try {
        System.out.print("Connecting to http://sokrates2.local.cs.hs-rm.de/...");
        ftp = new FtpClient("sokrates2.local.cs.hs-rm.de");
        System.out.println(" OK");
        System.out.print("Sending user and password...");
        ftp.login("hu000004", "1q2w3e");
        System.out.println(" OK");
        System.out.print("Setting binary transfer mode...");
        
        ftp.binary();
        System.out.println(" OK");

        BufferedInputStream in = new BufferedInputStream(
          new FileInputStream("edi/in1.xml")
        );
        System.out.println("Sending file");
        TelnetOutputStream out = ftp.put("in/in2.xml");
        byte buffer[] = new byte[2048];
        int bytesRead = 0;
        int bytesSend = 0;
        int c=1;
        while((bytesRead = in.read(buffer, 0, 2048))!=-1) {
          out.write(buffer, 0, bytesRead);
          bytesSend += bytesRead;
          System.out.print(".");
        }
        out.flush();
        out.close();
        in.close();
        buffer = null;
        System.out.println("\n"+bytesSend + " Bytes OK.");

      }
      catch(FtpLoginException e) {
        System.out.print(" Failed: ");
        System.err.println(e.getMessage());
      }
      catch(FtpProtocolException e) {
        ftp = null;
        System.out.print(" Failed: ");
        System.err.println(e.getMessage());
      }
      catch(IOException e) {
        System.out.print(" Failed: ");
        System.err.println(e.getMessage());
      }
      finally{
        if(ftp != null) {
          try {
            System.out.print("Closing connection...");
            ftp.closeServer();
            System.out.println(" OK");
          }
          catch(IOException e) {
            System.err.println(e.getMessage());
          }
        }
        ftp = null;
      }

    }

}
