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
		try
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.ORDERS");
			Unmarshaller u = jc.createUnmarshaller();
			
			FileWriter fw = new FileWriter(new File("temp.xml"));
			fw.write(input);
			fw.close();
			Interchange interchange = (Interchange ) u.unmarshal(new File("temp.xml"));
			/*List<Segment> msgSegments = interchange.getMessage().getSegment();
			
			for(Segment s : msgSegments)
			{
				List<CDE> cde = s.getCDE();
				for(CDE c : cde)
				{
					if(c.getName().equals("C507"))
					{
						List<DE> de1 = c.getDE();
						for(DE de : de1)
						{
							System.out.println(de.getName() + " : " + de.getContent());
						}
					}
				}
			}*/
			
			// ### Produkte aus der Bestellung extrahieren und die entsprechende Menge aus der DB loeschen ###
			
			/*List<SegmentGroup> segGroup = interchange.getMessage().getSegmentGroup();
			
			for(SegmentGroup sg : segGroup)
			{
				String gtin=null,quantity=null;
				if(sg.getName().equals("SG28"))
				{
					List<Segment> linAndQty = sg.getSegment();
					for(Segment s : linAndQty)
					{
						
						if(s.getName().equals("LIN"))
						{
							List<CDE> listCde = s.getCDE();
							for(CDE cde: listCde)
							{
								if(cde.getName().equals("C212"))
								{
									List<DE> productGTIN = cde.getDE();
									for(DE de:productGTIN)
									{
										if(de.getName().equals("7140"))
											gtin = de.getContent();
									}
								}
							}
						}
						else
							if(s.getName().equals("QTY"))
							{
								List<CDE> listCde = s.getCDE();
								for(CDE cde: listCde)
								{
									if(cde.getName().equals("C186"))
									{
										List<DE> productGTIN = cde.getDE();
										for(DE de:productGTIN)
										{
											if(de.getName().equals("6060"))
												quantity = de.getContent();
										}
									}
								}
							}
						
					}
					System.out.println(gtin + " : " + quantity);
				}
				
				
			}*/
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this!
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse("temp.xml");

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28']/Segment[@name='LIN']/CDE[@name='C212']/DE[@name='7140']/text()");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    for (int i = 0; i < nodes.getLength(); i++) 
		    {
		        System.out.println(nodes.item(i).getNodeValue()); 
		    }
			
			/*DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "darie17";
			String psw = "1q2w3e";
			Connection conn =
		         DriverManager.getConnection(url,user,psw);

			if(conn != null)
				System.out.println("nu e null");
			else
				System.out.println("null");
			
		    Statement stmt = conn.createStatement();
		    ResultSet rset =
		         stmt.executeQuery("select * from produkt");

			if(rset != null)
				System.out.println("rset nu e null");
			else
				System.out.println("rset null");
		    
		    System.out.println(rset.next());
		    while (rset.next()) {
		    	System.out.println("while");
		         System.out.println (rset.getLong(0));
		    }
		    stmt.close();
		    
		    System.out.println("END");*/
			
			
			
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
