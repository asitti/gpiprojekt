package paper4all.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import paper4all.WHinterface.SgtinContainer;
import paper4all.WHinterface.SgtinWhereAbouts;
import paper4all.WHinterface.Storage;
import paper4all.WHinterface.StorageService;

public class EPCTracking {
	
	public EPCTracking()
	{
		track();
	}
	
	public void track()
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String console_header = "epcTracking> ";
			System.out.println("Usage: get [gln: n..13] [date: DD/MM/YY]");
			while(true)
			{
				System.out.print(console_header);
				String user_input = in.readLine();
				if(user_input.equals("exit"))
				{
					break;
				}
				else
				{
					if(user_input.startsWith("get "))
					{
						String[] input = user_input.substring(4).split(" ");
						String gln = input[0].trim();
						String date = input[1].trim();
						
						Statement stmt = connectToDB();
						ResultSet rset = stmt.executeQuery("select sgtin from epc where gln='" + gln + "' and vk_datum='" + date + "'");
						List<String> sgtins = new ArrayList<String>();
						
						int counter = 0; 
						
				        while (rset.next()) 
				        {
				        	/*System.out.println(rset.getString(1));
				        	sgtins.add(rset.getString(1));*/
				        	
				        	String temp = rset.getString(1);
				        	String gtin = getGtinFromSgtin(temp);
				        	if(gtin.equals("2965197100101") ||
				        			gtin.equals("2965197100200") ||
				        			gtin.equals("2965197100309") ||
				        			gtin.equals("2965197100408") ||
				        			gtin.equals("2965197100507"))
				        	{
				        		sgtins.add(temp);
				        		System.out.println(temp);
				        	}
				        	counter++;
				        }
				        
				        if(counter == 0)
				        {
				        	System.out.println("No results. Please check your input.");
				        }
				        
				        if(!sgtins.isEmpty())
				        {
				        	Storage storage = new StorageService().getStoragePort();
				    		SgtinWhereAbouts res = storage.getSGTINWhereaboutsFromStorage(sgtins);
				    		List<SgtinContainer> sgtinRes = res.getSgtin();
				    		
				    		for(SgtinContainer sc : sgtinRes)
				    		{
				    			System.out.println("SGTIN: " + sc.getSgtin());
				    			System.out.println("Date: " + sc.getDate().toString());
				    			System.out.println("Location: " + sc.getCurrentLocationGLN());
				    		}
				        }
					}
					else
					{
						System.out.println("unknown command \"" + user_input + "\", please see usage");
					}
				}
				
			}
			System.exit(0);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Internal error.");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		
		EPCTracking epc = new EPCTracking();
		epc.track();
		
	    //Storage storage = new StorageService().getStoragePort();
		//SgtinWhereAbouts res = storage.getSGTINWhereaboutsFromStorage(null);
		//List<SgtinContainer> sgtinRes = res.getSgtin();
	}
	
	public void clearScreen()
	{
		System.out.flush();
		System.out.println("Usage: get [date] [gln]");
	}
	
	public Statement connectToDB()
	{
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "DARIE17";
			String psw = "1q2w3e";
			Connection conn = DriverManager.getConnection(url,user,psw);
			return conn.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author Andreas Otto
	 * @param sgtin
	 * @return
	 */
	public String getGtinFromSgtin(String sgtin) 
	{
	       BigInteger bigInt = new BigInteger(sgtin, 10);

	       String binarySGtin = bigInt.toString(2);
	       String compPre = binarySGtin.substring(12, 39);
	       String itemNum = binarySGtin.substring(39, 56);

	       final BigInteger cPre = new BigInteger(compPre, 2);
	       final BigInteger iNum = new BigInteger(itemNum, 2);

	       itemNum = iNum.toString(10);
	       compPre = cPre.toString(10);

	       while (itemNum.length() < 5) {
	           itemNum = "0" + itemNum;
	       }
	       return compPre + itemNum;
    }

}
