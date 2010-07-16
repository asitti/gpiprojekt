package paper4all.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
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
import paper4all.messages.DE;
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
	public List<String> processOrder( @WebParam(name="xmlOrderContent")String input, 
			@WebParam(name="invoiceTemplateContent") String invoice,
			@WebParam(name="desadvTemplateContent") String desadv) 
	{ 
		List<String> docum = new ArrayList<String>();
		String header = "48";//"00110000";
		String filterVKE = "1";//"001";
		String filterVPE = "2";//"010";
		String partition = "4";//"100";
		String basisNr = "29651971";//Long.toBinaryString(29651971);
		
		//BigInteger bg = new BigInteger(basisNr, 2);
		
		
		try
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.messages");
			Unmarshaller u = jc.createUnmarshaller();
			
			//copiam continutul intr-un fisier pt a putea lucra cu el
			File temp = new File("temp.xml");
			
			FileWriter fw = new FileWriter(temp);
			fw.write(input);
			fw.close();
			
			//cream fisierul pt invoice
			fw = new FileWriter(new File("inv.xml"));
			fw.write(invoice);
			fw.close();
			Interchange interchange = (Interchange ) u.unmarshal(new File("inv.xml"));
			
			//cream fisierul pt desadv
			fw = new FileWriter(new File("desadv.xml"));
			fw.write(desadv);
			fw.close();
			Interchange dispatch = (Interchange ) u.unmarshal(new File("desadv.xml"));
								
		
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
		    Document doc = builder.parse(temp);

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    
		    //gln des kaufers
		    XPathExpression expr = xpath.compile("/Interchange/Header/Segment[@name='UNB']/CDE[@name='S002']/DE[@name='0004']/text()");
		    Object glnObj = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList glnNode= (NodeList) glnObj;
		    String glnHandler = glnNode.item(0).getNodeValue();
		    
		    //schimbare gln des empfangers
			((CDE) interchange.getHeader().getSegment().getCDEOrDE().get(2)).getDE().get(0).setvalue(glnHandler);
			
			//datum
			((CDE) interchange.getHeader().getSegment().getCDEOrDE().get(3)).getDE().get(0).setvalue(getActualDate());
			((CDE) interchange.getHeader().getSegment().getCDEOrDE().get(3)).getDE().get(1).setvalue(getActualTime());
			
			//rechnungsnr + bagat in baza de date
			int invoiceNr = getInvoiceNr(stmt);
			((CDE)((Segment)((Message) interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(0)).getCDEOrDE().get(1)).getDE().get(0).setvalue("IN" + invoiceNr);
			stmt.executeQuery("insert into invoices values(" + invoiceNr + ", " + glnHandler + ", " + getActualDate() + ")");
			
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
			
			//gln des handlers
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(3)).getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(1)).getDE().get(0).
					setvalue(glnHandler);
			
			//rechnungsempfanger
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(5)).getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(1)).getDE().get(0).
					setvalue(glnHandler);
			
			
	//DEASDV
			
			//date
			((CDE)dispatch.getHeader().getSegment().getCDEOrDE().get(3)).getDE().get(0).setvalue(getActualDate());
			//time
			((CDE)dispatch.getHeader().getSegment().getCDEOrDE().get(3)).getDE().get(1).setvalue(getActualTime());
			//nr desadv
			((CDE)((Segment)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(0)).getCDEOrDE().get(1)).getDE().get(0).setvalue("DES" + invoiceNr);
			((DE)((Message) dispatch.getMessageOrMsgGroup().get(0)).getHeader().getSegment().getCDEOrDE().
					get(0)).setvalue("DES" + invoiceNr);
			((DE)((Message)dispatch.getMessageOrMsgGroup().get(0)).getTrailer().getSegment().getCDEOrDE().
					get(1)).setvalue("DES" + invoiceNr);
			//dok datum
			((CDE)((Segment)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(1)).getCDEOrDE().get(0)).getDE().get(1).setvalue("20" + getActualDate());
			//dispatch date
			((CDE)((Segment)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(2)).getCDEOrDE().get(0)).getDE().get(1).setvalue("20" + getActualDate());
			//delivery date
			((CDE)((Segment)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().
					get(3)).getCDEOrDE().get(0)).getDE().get(1).setvalue("20" + getActualDate());
			//order nr
			((CDE)((Segment)((SegmentGroup)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().get(4))
			.getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(0)).getDE().get(1).setvalue("" + invoiceNr);
			//bestell datum
			((CDE)((Segment)((SegmentGroup)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().get(4))
			.getSegmentOrSegmentGroup().get(1)).getCDEOrDE().get(0)).getDE().get(1).setvalue(dateNodes.item(0).getNodeValue());
			//gln handler
			((CDE)((Segment)((SegmentGroup)((Message) dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().get(5))
					.getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(1)).getDE().get(0).setvalue(glnHandler);
			
			
		    
		    //aici aflam cate produse au fost cerute
		    expr = xpath.compile("/Interchange/Message/SegmentGroup[@name='SG28']");//cate sunt
		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    
		    
		 
		    int anzahlLin = 0;
		    float totalPreis = 0;
		    float totalTaxes = 0;
		    int seqNr = 1;
		    List<SegmentGroup> sgList = new ArrayList<SegmentGroup>();
		    //pt fiecare produs cerut ne uitam sa vedem ce gtin si in ce cantitate tb trimis
		    for (int i = 0; i < nodes.getLength(); i++) 
		    {
		    	
		    	 //pt adaugare de produse in rechnung
		        SegmentGroup sg26 = new SegmentGroup();
			    sg26.setName("SG26");
			    //LIN
			    Segment lin = new Segment();
			    lin.setName("LIN");
			    DE linCounter = new DE();
			    linCounter.setName("1082");
			    CDE linCDE = new CDE();
			    linCDE.setName("C212");
			    DE linGTIN = new DE();
			    linGTIN.setName("7140");
			    DE eanUCC = new DE();
			    eanUCC.setName("7143");
			    eanUCC.setvalue("SRV");
			    linCDE.getDE().add(linGTIN);
			    linCDE.getDE().add(eanUCC);
			    lin.getCDEOrDE().add(linCounter);
			    lin.getCDEOrDE().add(linCDE);
			    sg26.getSegmentOrSegmentGroup().add(lin);
			    //END LIN
			    
			    //QTY
			    Segment qtyProdukt = new Segment();
			    qtyProdukt.setName("QTY");
			    CDE qtyCDE = new CDE();
			    qtyCDE.setName("C186");
			    DE qtyGeliefert = new DE();
			    qtyGeliefert.setName("6063");
			    qtyGeliefert.setvalue("46");
			    DE qtyMenge = new DE();
			    qtyMenge.setName("6060");
			    qtyCDE.getDE().add(qtyGeliefert);
			    qtyCDE.getDE().add(qtyMenge);
			    qtyProdukt.getCDEOrDE().add(qtyCDE);
			    sg26.getSegmentOrSegmentGroup().add(qtyProdukt);
			    //END QTY
			    
			    //SegmentGroup SG27
			    SegmentGroup sg27 = new SegmentGroup();
			    sg27.setName("SG27");
			    //Segment MOA
			    Segment moa = new Segment();
			    moa.setName("MOA");
			    CDE moaCDE = new CDE();
			    moaCDE.setName("C516");
			    DE itemAmount = new DE();
			    itemAmount.setName("5025");
			    itemAmount.setvalue("203");
			    DE amount = new DE();
			    amount.setName("5004");
			    moaCDE.getDE().add(itemAmount);
			    moaCDE.getDE().add(amount);
			    moa.getCDEOrDE().add(moaCDE);
			    sg27.getSegmentOrSegmentGroup().add(moa);
			    sg26.getSegmentOrSegmentGroup().add(sg27);
			    
			    //SegmentGroup SG29
			    SegmentGroup sg29 = new SegmentGroup();
			    sg29.setName("SG29");
			    //Segment PRI
			    Segment pri = new Segment();
			    pri.setName("PRI");
			    CDE priCDE = new CDE();
			    priCDE.setName("C509");
			    DE nettoPreis = new DE();
			    nettoPreis.setName("5125");
			    nettoPreis.setvalue("AAA");
			    DE amountPRI = new DE();
			    amountPRI.setName("5118");
			    DE vertragsPreis = new DE();
			    vertragsPreis.setName("5375");
			    vertragsPreis.setvalue("CT");
			    priCDE.getDE().add(nettoPreis);
			    priCDE.getDE().add(amountPRI);
			    priCDE.getDE().add(vertragsPreis);
			    pri.getCDEOrDE().add(priCDE);
			    sg29.getSegmentOrSegmentGroup().add(pri);
			    sg26.getSegmentOrSegmentGroup().add(sg29);
			    
			    //SegmentGroup SG34
			    SegmentGroup sg34  = new SegmentGroup();
			    sg34.setName("SG34");
			    //Segment TAX
			    Segment tax = new Segment();
			    tax.setName("TAX");
			    DE steuern = new DE();
			    steuern.setName("5283");
			    steuern.setvalue("7");
			    CDE c241 = new CDE();
			    c241.setName("C241");
			    DE mwst = new DE();
			    mwst.setName("5153");
			    mwst.setvalue("VAT");
			    c241.getDE().add(mwst);
			    CDE c243 = new CDE();
			    c243.setName("C243");
			    DE mwstWert = new DE();
			    mwstWert.setName("5278");
			    mwstWert.setvalue("19");
			    c243.getDE().add(mwstWert);
			    DE stdRate = new DE();
			    stdRate.setName("5305");
			    stdRate.setvalue("S");
			    tax.getCDEOrDE().add(steuern);
			    tax.getCDEOrDE().add(c241);
			    tax.getCDEOrDE().add(c243);
			    tax.getCDEOrDE().add(stdRate);
			    sg34.getSegmentOrSegmentGroup().add(tax);
			    //Segment MOA
			    Segment moa2 = new Segment();
			    moa2.setName("MOA");
			    CDE moaCDE2 = new CDE();
			    moaCDE2.setName("C516");
			    DE taxAmount = new DE();
			    taxAmount.setName("5025");
			    taxAmount.setvalue("124");
			    DE amount2 = new DE();
			    amount2.setName("5004");
			    moaCDE2.getDE().add(taxAmount);
			    moaCDE2.getDE().add(amount2);
			    moa2.getCDEOrDE().add(moaCDE2);
			    sg34.getSegmentOrSegmentGroup().add(moa2);
			    sg26.getSegmentOrSegmentGroup().add(sg34);
		//--------------------------------------------------------
		    	
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
		      
		       //System.out.println(gtin + " : cerute " + qty);
		       
		       ResultSet rset =
			         stmt.executeQuery("select * from produkt where gtin="+ gtinNode.item(0).getNodeValue());
		      
		       while (rset.next()) 
		       {
		    	   //cate sunt disponibile in baza de date - anzahl_verfuegbar
		    	   	int verfugbar = Integer.parseInt(rset.getString(6));
		    	   	float nettoP = Float.parseFloat(rset.getString(7));
			    	System.out.println(gtin + " : cerute " + qty + ": avute " + verfugbar);
			    	
			    	String produktNr = gtin.substring(8,12); //Long.toBinaryString(Long.parseLong(gtin.substring(8,12)));
			    	
			    	if(verfugbar >= Integer.parseInt(qty))
			    	{
			    		anzahlLin++;
			    		
			    		stmt.execute("update produkt set anzahl_verfuegbar=" + (verfugbar - Integer.parseInt(qty))
			    				+ " where gtin=" + gtin);
			    		
			    		//introducere elem
			    		linCounter.setvalue(Integer.toString(anzahlLin));
			    		linGTIN.setvalue(gtin);
			    		qtyMenge.setvalue(qty);
			    		amount.setvalue(Float.toString(nettoP * Integer.parseInt(qty)));
			    		totalPreis += nettoP * Integer.parseInt(qty);
			    		amountPRI.setvalue(Float.toString(nettoP));
			    		float calc15 = (Float.parseFloat(amount.getvalue()) * Integer.parseInt(mwstWert.getvalue()))/100;
			    		totalTaxes += calc15;
			    		amount2.setvalue(Float.toString(calc15));
			    					    		
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
				    		
			    			/*System.out.println("palette: " + gtin);
			    			System.out.println("	karton: " + gtinKarton + " x " + anzahlKarton);
			    			System.out.println("		vke: " + gtinVKE + " x " + anzahlVKE);
				    		*/
				    		
			    			//generare qty sgtin pallette
			    			String serialNrPalette= getSRNEPC(stmt);
			    			String teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
			    			List<String> sgtinPaletteList = generateSrn(Integer.parseInt(qty), serialNrPalette);
			    			insertEPC(stmt, teilSGTIN, sgtinPaletteList, gtin, glnHandler);
			    			
			    			//generare qty*anzahlKatron sgtin pt kartons
			    			String serialNrKarton= getSRNEPC(stmt);
			    			teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
			    			List<String> sgtinKartonList = generateSrn(Integer.parseInt(qty)* anzahlKarton, serialNrKarton);
			    			insertEPC(stmt, teilSGTIN, sgtinKartonList, gtinKarton, glnHandler);
			    			
			    			//generare qty*anzahlKarton*anzahlVKE sgtin vke
			    			String serialNrVKE= getSRNEPC(stmt);
			    			teilSGTIN = header + filterVKE + partition + basisNr + produktNr;
			    			List<String> sgtinVKEList = generateSrn(Integer.parseInt(qty)*anzahlKarton*anzahlVKE, serialNrPalette);
			    			insertEPC(stmt, teilSGTIN, sgtinVKEList, gtinVKE, glnHandler);
			    			
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
					    		
				    	//------kartons-------
				    			//vedem care e ultimul srn pt vpe din baza de date
					    		String serialNrKarton = getSRNEPC(stmt);
					    		
					    		//acuma tb generate "qty" snr pt kartons
				    			String teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
				    			List<String> sgtinList = generateSrn(Integer.parseInt(qty), serialNrKarton);
				    			insertEPC(stmt, teilSGTIN, sgtinList, gtin, glnHandler);
				    	//-----ende kartons------
				    			
				    	//-------aici incep vke----------
				    			//generare snr pt (gty kartons cerute) * (anzahl der vke) dintr-un carton
				    			String serialNrVKE = getSRNEPC(stmt);
					    		
					    		//acuma tb generate "qty" snr pt kartons
				    			teilSGTIN = header + filterVKE + partition + basisNr + produktNr;
				    			List<String> sgtinListVKE = generateSrn(Integer.parseInt(qty)*anzahl, serialNrVKE);
				    			insertEPC(stmt, teilSGTIN, sgtinListVKE, gtinVKE, glnHandler);
				    			
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
				    			}
				    			*/
					    					    		
					    	}
					    	
					    	//atunci e vke
					    	else
					    	{
					    		String serialNr = getSRNEPC(stmt);
					    		//System.out.println("ultimul snr este:" + serialNr);
					    		
				    			String teilSGTIN = header + filterVKE + partition + basisNr + produktNr;
				    			List<String> sgtinList = generateSrn(Integer.parseInt(qty), serialNr);
				    			insertEPC(stmt, teilSGTIN, sgtinList, gtin, glnHandler);
				    			
				    			SegmentGroup sg10  = new SegmentGroup();
				    			sg10.setName("SG10");
				    			//CPS
				    			Segment cps = new Segment();
				    			cps.setName("CPS");
				    			DE de7164 = new DE();
				    			de7164.setName("7164");
				    			de7164.setvalue("" + seqNr);
				    			cps.getCDEOrDE().add(de7164);
				    			sg10.getSegmentOrSegmentGroup().add(cps);
				    			//SG11
				    			SegmentGroup sg11  = new SegmentGroup();
				    			sg11.setName("SG11");
				    			Segment pac = new Segment();
				    			pac.setName("PAC");
				    			DE de7224 = new DE();
				    			de7224.setName("7224");
				    			de7224.setvalue("1");
				    			CDE c202 = new CDE();
				    			c202.setName("C202");
				    			DE de7065 = new DE();
				    			de7065.setName("7065");
				    			de7065.setvalue("EN");
				    			DE de3055 = new DE();
				    			de3055.setName("3055");
				    			de3055.setvalue("9");
				    			c202.getDE().add(de7065);
				    			c202.getDE().add(de3055);
				    			pac.getCDEOrDE().add(de7224);
				    			pac.getCDEOrDE().add(c202);
				    			sg11.getSegmentOrSegmentGroup().add(pac);
				    			sg10.getSegmentOrSegmentGroup().add(sg11);
				    			
				    			for(int p=0; p<Integer.parseInt(qty); p++ )
				    			{
				    				//SG17
					    			SegmentGroup sg17  = new SegmentGroup();
					    			sg17.setName("SG17");
					    			Segment linVKE = new Segment();
					    			linVKE.setName("LIN");
					    			DE de1082 = new DE();
					    			de1082.setName("1082");
					    			de1082.setvalue("" + (p+1));
					    			CDE c212 = new CDE();
					    			c212.setName("C212");
					    			DE de7140 = new DE();
					    			de7140.setName("7140");
					    			de7140.setvalue("" + teilSGTIN + sgtinList.get(p));
					    			DE de7143 = new DE();
					    			de7143.setName("7143");
					    			de7143.setvalue("SRV");
					    			c212.getDE().add(de7140);
					    			c212.getDE().add(de7143);
					    			linVKE.getCDEOrDE().add(de1082);
					    			linVKE.getCDEOrDE().add(c212);
					    			sg17.getSegmentOrSegmentGroup().add(linVKE);
					    			sg10.getSegmentOrSegmentGroup().add(sg17);
				    			}
				    			sgList.add(sg10);
				    			
				    			
				    			
				    			
				    			seqNr++;
				    			
				    			
					    		
					    	}
				    	}
			    		
			    		//aici tb bagat in interchange
			    		//sunt de la 0-8 seg si seggroup inaintea lui
			    		((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().add(8+anzahlLin, sg26);
			    		
				    }
		       } 
		    }
		    int afterSG2 = 8;
		    for(int k = sgList.size()-1; k>=0; k-- )
		    	((Message)dispatch.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup().add(afterSG2, sgList.get(k));
		    
		    
		    
		    //aici se poate det cate produse pot fi livrate
	        //SG CNT
			((CDE)((Segment)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(8 + anzahlLin + 2)).getCDEOrDE().get(0)).getDE().get(1).setvalue(Integer.toString(anzahlLin));
			
			//SG50 total line items amount
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(16 + anzahlLin)).getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(0)).getDE().get(1).
					setvalue(Float.toString(totalPreis));
			
			//SG50 total taxable amount
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(12 + anzahlLin)).getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(0)).getDE().get(1).
					setvalue(Float.toString(totalTaxes));
			//22 + 6*anzahlLin
			
			((DE)((Message)interchange.getMessageOrMsgGroup().get(0)).getTrailer().getSegment().getCDEOrDE()
					.get(0)).setvalue(Integer.toString(22 + 6 * anzahlLin));
			
			stmt.close();
		    
			
			
			JAXBContext jc2 = JAXBContext.newInstance("paper4all.messages");
			Marshaller m = jc2.createMarshaller();
			File interch = new File("interch.xml");
			m.marshal(interchange, interch);
			String inter = getInput(interch);
			interch.delete();
			System.out.println(inter);
			docum.add(inter);
			
			
			File des = new File("dispatch.xml");
			m.marshal(dispatch, des);
			String disp = getInput(des);
			des.delete();
			System.out.println(disp);
			docum.add(disp);
			
			
			temp.delete();
			return docum;
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
	
	private String getInput(File f)
	{
		try
		{
			String input = "";
		    BufferedReader in = new BufferedReader(new FileReader(f));
		    String str;
		    while ((str = in.readLine()) != null) 
		    {
		        input += str;
		    }
		    in.close();
		    return input;
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		  
		}
		return null;
	  
	}
	
}	
