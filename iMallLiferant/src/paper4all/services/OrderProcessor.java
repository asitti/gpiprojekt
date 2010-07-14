package paper4all.services;


import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import paper4all.messages.CDE;
import paper4all.messages.Interchange;
import paper4all.messages.Message;
import paper4all.messages.Segment;
import paper4all.messages.SegmentGroup;


@WebService(name="OrderProcessorWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class OrderProcessor 
{
	
	@WebMethod(operationName="process-incoming-order") 
	@WebResult(name = "success-result") 
	public String processOrder( @WebParam(name="xmlOrderContent")String input, 
			@WebParam(name="invoiceTemplateContent") String invoice) 
	{ 
		String header = "48";
		String basisNr = "2965197";
		String filterVKE = "1";
		String filterVPE = "2";
		String partition = "4";
		try
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.messages");
			Unmarshaller u = jc.createUnmarshaller();
			
			//copiam continutul intr-un fisier pt a putea lucra cu el
			FileWriter fw = new FileWriter(new File("temp.xml"));
			fw.write(input);
			fw.close();
			
			//cream fisierul pt invoice
			fw = new FileWriter(new File("inv.xml"));
			fw.write(invoice);
			fw.close();
			Interchange interchange = (Interchange ) u.unmarshal(new File("inv.xml"));
			
			
		
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

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    
		    //gln des kaufers
		    XPathExpression expr = xpath.compile("/Interchange/Header/Segment[@name='UNB']/CDE[@name='S002']/DE[@name='0004']/text()");
		    Object glnObj = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList glnNode= (NodeList) glnObj;
		    String gln = glnNode.item(0).getNodeValue();
		    
		    //schimbare gln des empfangers
			((CDE) interchange.getHeader().getSegment().getCDEOrDE().get(2)).getDE().get(0).setvalue(gln);
			
			//datum
			((CDE) interchange.getHeader().getSegment().getCDEOrDE().get(3)).getDE().get(0).setvalue(getActualDate());
			((CDE) interchange.getHeader().getSegment().getCDEOrDE().get(3)).getDE().get(1).setvalue(getActualTime());
			
			//rechnungsnr + bagat in baza de date
			int invoiceNr = getInvoiceNr(stmt);
			((CDE)((Segment)((Message) interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(0)).getCDEOrDE().get(1)).getDE().get(0).setvalue("IN" + invoiceNr);
			stmt.executeQuery("insert into invoices values(" + invoiceNr + ", " + gln + ", " + getActualDate() + ")");
			
			//dok datum
			((CDE)((Segment)((Message) interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(1)).getCDEOrDE().get(0)).getDE().get(1).setvalue("20" + getActualDate());
			
			//bestellnr
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(2)).getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(0)).getDE().get(1).setvalue("ORD"+invoiceNr);
			
			//referenzdatum der bestellung
			expr = xpath.compile("/Interchange/Message/Segment[@name='DTM']/CDE[@name='C507']/DE[@name='2380']/text()");
		    Object refDate = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList dateNodes = (NodeList) refDate;
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(2)).getSegmentOrSegmentGroup().get(1)).getCDEOrDE().get(0)).getDE().get(1).
					setvalue(dateNodes.item(0).getNodeValue());
			
					
		    
		    //aici aflam cate produse au fost cerute
		    expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28']");//cate sunt
		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    //pt fiecare produs cerut ne uitam sa vedem ce gtin si in ce cantitate tb trimis
		    for (int i = 0; i < nodes.getLength(); i++) 
		    {
		    	//gtin
		       expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28'][" 
		    		   + (i+1) + "]/Segment[@name='LIN']/CDE[@name='C212']/DE[@name='7140']/text()");
		       Object gtinObj = expr.evaluate(doc, XPathConstants.NODESET);
		       NodeList gtinNode= (NodeList) gtinObj;
		       String gtin = gtinNode.item(0).getNodeValue();
		       
		       //qty
		       expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28'][" 
		    		   + (i+1) + "]/Segment[@name='QTY']/CDE[@name='C186']/DE[@name='6060']/text()");
		       Object qtyObj = expr.evaluate(doc, XPathConstants.NODESET);
		       NodeList qtyNode= (NodeList) qtyObj;
		       String qty = qtyNode.item(0).getNodeValue();
		      
		       System.out.println(gtin + " : cerute " + qty);
		       
		       ResultSet rset =
			         stmt.executeQuery("select * from produkt where gtin="+ gtinNode.item(0).getNodeValue());
		       while (rset.next()) 
		       {
		    	   //cate sunt disponibile in baza de date - anzahl_verfuegbar
		    	   	int verfugbar = Integer.parseInt(rset.getString(6));
			    	System.out.println(gtin + " : cerute " + qty + ": avute " + verfugbar);
			    	
			    	String produktNr = gtin.substring(7,11);
			    	
			    	//pt sa generam sgtin pt ce vindem
			    	//daca e palette
		    		if(gtin.equals("2965197100125") || gtin.equals("2965197100224")
		    				|| gtin.equals("2965197100323") || gtin.equals("2965197100422") || gtin.equals("2965197100521")  )
		    		{
		    			//tb vazut cate kartons contine si care e gtin-ul acestora
		    			ResultSet karton =  stmt.executeQuery(
		    					"select anzahl,gtin_karton from palette where gtin_palette='" 
			    				+ gtin +"'");
			    		int anzahlKarton=0;
			    		String gtinKarton=null;
			    		if(karton.next())
			    		{
			    			String  anz = karton.getString(1);
			    			if(anz != null)
			    				anzahlKarton = Integer.parseInt(anz);
			    			gtinKarton = karton.getString(2);
			    		}
			    		
			    		//si cate vke contine un karton cu gtin = gtinKarton
			    		
			    		ResultSet vke =  stmt.executeQuery(
		    					"select anzahl,gtin_vke from karton where gtin_karton='" 
			    				+ gtinKarton +"'");
			    		int anzahlVKE=0;
			    		String gtinVKE=null;
			    		if(vke.next())
			    		{
			    			String  anz = vke.getString(1);
			    			if(anz != null)
			    				anzahlVKE = Integer.parseInt(anz);
			    			gtinVKE = karton.getString(2);
			    		}
			    		
		    			System.out.println("palette: " + gtin);
		    			System.out.println("	karton: " + gtinKarton + " x " + anzahlKarton);
		    			System.out.println("		vke: " + gtinVKE + " x " + anzahlVKE);
			    		
		    			
		    			//daca avem atatea disponibile
		    			if(verfugbar >= Integer.parseInt(qty) )
		    			{    			
			    			//generare qty sgtin pallette
			    			String serialNrPalette= getSRNEPC(stmt);
			    			String teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
			    			List<String> sgtinPaletteList = generateSrn(Integer.parseInt(qty), serialNrPalette);
			    			insertEPC(stmt, teilSGTIN, sgtinPaletteList, gtin, gln);
			    			
			    			//generare qty*anzahlKatron sgtin pt kartons
			    			String serialNrKarton= getSRNEPC(stmt);
			    			teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
			    			List<String> sgtinKartonList = generateSrn(Integer.parseInt(qty)* anzahlKarton, serialNrKarton);
			    			insertEPC(stmt, teilSGTIN, sgtinKartonList, gtinKarton, gln);
			    			
			    			//generare qty*anzahlKarton*anzahlVKE sgtin vke
			    			String serialNrVKE= getSRNEPC(stmt);
			    			teilSGTIN = header + filterVKE + partition + basisNr + produktNr;
			    			List<String> sgtinVKEList = generateSrn(Integer.parseInt(qty)*anzahlKarton*anzahlVKE, serialNrPalette);
			    			insertEPC(stmt, teilSGTIN, sgtinVKEList, gtinVKE, gln);
			    			
			    			int kKarton=0, kVKE=0;
			    			/*for(String sp : sgtinPaletteList)
			    			{
			    				System.out.println("pt palette cu sgtin: " + sp);
			    				
			    				//fiecare vke intre k si k+anzahl vor fi trecute la kartonul k
			    				for(int j = kKarton; j<((kKarton+1)*anzahlKarton); j++)
			    				{
			    					System.out.println("	sgtin_karton: " + sgtinKartonList.get(j));
			    					for(int k = j*anzahlVKE; k < ((j+1)*anzahlVKE); k++)
			    						System.out.println("		sgtin_vke: " + sgtinVKEList.get(k));
			    				}
			    				kKarton += 1;
			    			}*/
		    			
		    			}
		    			
		    			
		    			
		    			
		    		}
			    	else
			    	{
			    		//daca e karton
				    	if(gtin.equals("2965197100118") || gtin.equals("2965197100217") ||
				    			gtin.equals("2965197100316") || gtin.equals("2965197100415") 
				    			|| gtin.equals("2965197100514")  )
				    	{
				    		 
				    		//tb vazut cate bucati vke sunt intr-un carton
				    		ResultSet karton =  stmt.executeQuery(
				    				"select anzahl,gtin_vke from karton where gtin_karton='" 
				    				+ gtin +"'");
				    		int anzahl=0;
				    		String gtinVKE=null;
				    		if(karton.next())
				    		{
				    			String  anz = karton.getString(1);
				    			if(anz != null)
				    				anzahl = Integer.parseInt(anz);
				    			gtinVKE = karton.getString(2);
				    		}
				    		System.out.println("gtin_karton: " + gtin + ", gtin_vke: " + gtinVKE + ", anzahl: " + anzahl );
				    		
				    		//vedem daca avem atatea disponibile si generam snr pt cartoane si vke
				    		if(verfugbar >= Integer.parseInt(qty))
				    		{
				    	//------kartons-------
				    			//vedem care e ultimul srn pt vpe din baza de date
					    		String serialNrKarton = getSRNEPC(stmt);
					    		
					    		//acuma tb generate "qty" snr pt kartons
				    			String teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
				    			List<String> sgtinList = generateSrn(Integer.parseInt(qty), serialNrKarton);
				    			insertEPC(stmt, teilSGTIN, sgtinList, gtin, gln);
				    	//-----ende kartons------
				    			
				    	//-------aici incep vke----------
				    			//generare snr pt (gty kartons cerute) * (anzahl der vke) dintr-un carton
				    			String serialNrVKE = getSRNEPC(stmt);
					    		
					    		//acuma tb generate "qty" snr pt kartons
				    			teilSGTIN = header + filterVKE + partition + basisNr + produktNr;
				    			List<String> sgtinListVKE = generateSrn(Integer.parseInt(qty)*anzahl, serialNrVKE);
				    			insertEPC(stmt, teilSGTIN, sgtinListVKE, gtinVKE, gln);
				    			
				    			//ordonarea karton - anzahl vke care se afla intr-un carton 
				    			int k=0;
				    			/*for(String sgtinVPE : sgtinList)
				    			{
				    				System.out.println("pt kartonul cu sgtin: " + sgtinVPE);
				    				
				    				//fiecare vke intre k si k+anzahl vor fi trecute la kartonul k
				    				for(int j = k; j<(k+anzahl); j++)
				    				{
				    					System.out.println("	sgtin_vke: " + sgtinListVKE.get(j));
				    				}
				    				k+=anzahl;				    				
				    			}*/
				    		}				    		
				    	}
				    	
				    	//atunci e vke
				    	else
				    	{
				    		String serialNr = getSRNEPC(stmt);
				    		System.out.println("ultimul snr este:" + serialNr);
				    		
				    		//6-anzahl verfugbare produkte	
				    		//daca avem mai multe decat se cer generam pt fiecare produs un sgtin si il bagam  in baza de date
				    		if(verfugbar >= Integer.parseInt(qty))
				    		{
				    			String teilSGTIN = header + filterVKE + partition + basisNr + produktNr;
				    			List<String> sgtinList = generateSrn(Integer.parseInt(qty), serialNr);
				    			insertEPC(stmt, teilSGTIN, sgtinList, gtin, gln);
				    		}
				    	}
			    	}
			    }
		    }
			
			stmt.close();
		    
			
			
			JAXBContext jc2 = JAXBContext.newInstance("paper4all.messages");
			Marshaller m = jc2.createMarshaller();
			m.marshal(interchange, System.out);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	//metoda intoarce "anzahl" serial numbers produse, egal de care
	private List<String> generateSrn(int anzahl, String srn)
	{
		List<String> srnList = new ArrayList<String>();
		for(int i=0; i<anzahl; i++)
		{
			
			int nr = Integer.parseInt(srn)+1;
			srn = Integer.toString(nr);
			srnList.add(srn);
		}
		
		return srnList;
	}
	
	
	private void insertEPC(Statement stmt, String teilSGTIN, List<String> sgtinList, String gtin, String gln )
	{
		try 
		{
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        String date = sdf.format(cal.getTime());
			
			for(String s : sgtinList)
			{
				String sgtinStuck = teilSGTIN + s;
				stmt.executeQuery("insert into epc values("+sgtinStuck + "," + gtin + ", " 
							+ gln + ", " + s + ",'" + date + "')");
			} 
		}
			
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	private String getSRNEPC(Statement stmt)
	{
		try 
		{
			String serial = null;
			ResultSet sgtinVKE;
			
				sgtinVKE = stmt.executeQuery("select max(srn) from epc");
			
			if(sgtinVKE.next())
			{
				serial = sgtinVKE.getString(1);
			}
			if(serial == null)
				serial = "1000";//pt ca e 4-stellig
			return serial;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private String getActualDate()
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(cal.getTime());
	}
	
	private String getActualTime()
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        return sdf.format(cal.getTime());
	}
	
	private int getInvoiceNr(Statement stmt)
	{
		try 
		{
			String nr = null;
			ResultSet invoiceNr;
			invoiceNr = stmt.executeQuery("select max(invoice_nr) from invoices");
			
			if(invoiceNr.next())
			{
				nr = invoiceNr.getString(1);
			}
			if(nr == null)
				nr = "1000";//pt ca e 4-stellig
			return Integer.parseInt(nr)+1;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1000;
		}
		
		
	}
	
	
}	
