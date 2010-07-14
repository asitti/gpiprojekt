package paper4all.services;


import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import paper4all.ORDERS.CDE;
import paper4all.ORDERS.DE;
import paper4all.ORDERS.Interchange;
import paper4all.ORDERS.Segment;
import paper4all.ORDERS.SegmentGroup;

@WebService(name="OrderProcessorWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class OrderProcessor 
{
	
	@WebMethod(operationName="process-incoming-order") 
	@WebResult(name = "success-result") 
	public boolean processOrder( @WebParam(name="xmlOrderContent")String input) 
	{ 
		String header = "48";
		String basisNr = "2965197";
		String filterVKE = "1";
		String filterVPE = "2";
		String partition = "4";
		try
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.ORDERS");
			Unmarshaller u = jc.createUnmarshaller();
			
			FileWriter fw = new FileWriter(new File("temp.xml"));
			fw.write(input);
			fw.close();
			Interchange interchange = (Interchange ) u.unmarshal(new File("temp.xml"));
		
			//conexiune la baza de date
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "darie17";
			String psw = "1q2w3e";
			Connection conn = DriverManager.getConnection(url,user,psw);
			Statement stmt = conn.createStatement();

			//initializarea docum pt xpath
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this!
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse("temp.xml");

		    //aici aflam cate produse au fost cerute
		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28']");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    //pt fiecare produs cerut ne uitam sa vedem ce gtin si in ce cantitate tb trimis
		    for (int i = 0; i < nodes.getLength(); i++) 
		    {
		       expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28'][" 
		    		   + (i+1) + "]/Segment[@name='LIN']/CDE[@name='C212']/DE[@name='7140']/text()");
		       Object gtinObj = expr.evaluate(doc, XPathConstants.NODESET);
		       NodeList gtinNode= (NodeList) gtinObj;
		       String gtin = gtinNode.item(0).getNodeValue();
		       
		       expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28'][" 
		    		   + (i+1) + "]/Segment[@name='QTY']/CDE[@name='C186']/DE[@name='6060']/text()");
		       Object qtyObj = expr.evaluate(doc, XPathConstants.NODESET);
		       NodeList qtyNode= (NodeList) qtyObj;
		       String qty = qtyNode.item(0).getNodeValue();
		      
		       
		       System.out.println(gtinNode.item(0).getNodeValue());
		       
		       ResultSet rset =
			         stmt.executeQuery("select * from produkt where gtin="+ gtinNode.item(0).getNodeValue());
		       while (rset.next()) 
		       {
		    	   //cate sunt disponibile in baza de date - anzahl_verfuegbar
			    	System.out.println(gtin + " : cerute " + qty + ": avute " +rset.getLong(6));
			    	
			    	String produktNr = gtin.substring(7,11);
			    	
			    	//daca e palette
		    		if(gtin.equals("2965197100125") || gtin.equals("2965197100224")
		    				|| gtin.equals("2965197100323") || gtin.equals("2965197100422") || gtin.equals("2965197100521")  )
		    		{
		    			String sgtinVerpackung = header + filterVPE + partition + basisNr + produktNr; //+ serial number
		    			//mai tb sgtin generate pt toate unitatile din interior
		    			
		    		}
			    	else
			    	{
			    		//daca e karton
				    	if(gtin.equals("2965197100118") || gtin.equals("2965197100217") ||
				    			gtin.equals("2965197100316") || gtin.equals("2965197100415") 
				    			|| gtin.equals("2965197100514")  )
				    	{
				    		String sgtinVerpackung = header + filterVPE + partition + basisNr + produktNr; //+ serial number
				    		
				    		//mai tb sgtin generate pt nr de cartoane care sunt inauntru si pt fiecare unitate in parte
				    	}
			    		
			    	}
			    	
			    	if(Integer.parseInt(rset.getString(6))>=Integer.parseInt(qtyNode.item(0).getNodeValue()))
			    	{
			    		System.out.println("se poate trimite toata cant");
			    	}
			    	else
			    		System.out.println("se poate trimite doar o parte");
			    }
		       
		       
		    }
			
			stmt.close();
		    
			
			/*
			JAXBContext jc2 = JAXBContext.newInstance("paper4all.ORDERS");
			Marshaller m = jc2.createMarshaller();
			m.marshal(interchange, System.out);
			*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
