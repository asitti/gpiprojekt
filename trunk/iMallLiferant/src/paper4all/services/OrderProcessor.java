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
		
		String header = "00110000";
		String filterVKE = "001";
		String filterVPE = "010";
		String partition = "100";
		String basisNr = getBinaryPositions("29651971", 27);
		
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
			 expr = xpath.compile("/Interchange/Message/Segment[@name='BGM']/CDE[@name='C106']/DE[@name='1004']/text()");
			 Object ordObj = expr.evaluate(doc, XPathConstants.NODESET);
			 NodeList ordNode= (NodeList) ordObj;
			 String orderNr = ordNode.item(0).getNodeValue();
			((CDE)((Segment)((SegmentGroup)((Message)interchange.getMessageOrMsgGroup().get(0)).getSegmentOrSegmentGroup()
					.get(2)).getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(0)).getDE().get(1).setvalue(orderNr);
			
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
			.getSegmentOrSegmentGroup().get(0)).getCDEOrDE().get(0)).getDE().get(1).setvalue(orderNr);
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
		      
		       ResultSet rset =
			         stmt.executeQuery("select * from produkt where gtin="+ gtinNode.item(0).getNodeValue());
		      
		       while (rset.next()) 
		       {
		    	   //cate sunt disponibile in baza de date - anzahl_verfuegbar
		    	   	int verfugbar = Integer.parseInt(rset.getString(6));
		    	   	float nettoP = Float.parseFloat(rset.getString(7));
			    	//System.out.println(gtin + " : cerute " + qty + ": avute " + verfugbar);
			    	String produktNr = getBinaryPositions(gtin.substring(8,13), 17);
			    	
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
			    			String produktNrKarton = null;
			    			String produktNrVKE = null;
			    			
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
				    			produktNrKarton = getBinaryPositions(gtinKarton.substring(8, 13),17);
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
				    			produktNrVKE = getBinaryPositions(gtinVKE.substring(8, 13),17);
				    		}
				    		String teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
				    		String teilSGTINKarton = header + filterVPE + partition + basisNr + produktNrKarton;
				    		String teilSGTINVKE = header + filterVKE + partition + basisNr + produktNrVKE;
				    		
			    			for(int j = 0; j< Integer.parseInt(qty); j++)
			    			{
			    				String serialNrPalette= getSRNEPC(stmt);
			    				List<String> sgtinPaletteList = generateSrn(1, serialNrPalette);
				    			insertEPC(stmt, teilSGTIN, sgtinPaletteList, gtin, glnHandler);
				    			
				    			//generare qty*anzahlKatron sgtin pt kartons
				    			String serialNrKarton= getSRNEPC(stmt);
				    			List<String> sgtinKartonList = generateSrn(anzahlKarton, serialNrKarton);
				    			insertEPC(stmt, teilSGTINKarton, sgtinKartonList, gtinKarton, glnHandler);
			    							    				
				    			//level 1
			    				SegmentGroup sg10Palette  = new SegmentGroup();
				    			sg10Palette.setName("SG10");
				    			//CPS
				    			Segment cpsPalette = new Segment();
				    			cpsPalette.setName("CPS");
				    			DE de7164Palette = new DE();
				    			de7164Palette.setName("7164");
				    			de7164Palette.setvalue("" + seqNr);
				    			cpsPalette.getCDEOrDE().add(de7164Palette);
				    			sg10Palette.getSegmentOrSegmentGroup().add(cpsPalette);
				    			//SG11
				    			SegmentGroup sg11Palette  = new SegmentGroup();
				    			sg11Palette.setName("SG11");
				    			Segment pacPalette = new Segment();
				    			pacPalette.setName("PAC");
				    			DE de7224Palette = new DE();
				    			de7224Palette.setName("7224");
				    			de7224Palette.setvalue("1");
				    			CDE c202Palette = new CDE();
				    			c202Palette.setName("C202");
				    			DE de7065Palette = new DE();
				    			de7065Palette.setName("7065");
				    			de7065Palette.setvalue("201");
				    			DE de3055Palette = new DE();
				    			de3055Palette.setName("3055");
				    			de3055Palette.setvalue("9");
				    			c202Palette.getDE().add(de7065Palette);
				    			c202Palette.getDE().add(de3055Palette);
				    			pacPalette.getCDEOrDE().add(de7224Palette);
				    			pacPalette.getCDEOrDE().add(c202Palette);
				    			sg11Palette.getSegmentOrSegmentGroup().add(pacPalette);
				    			sg10Palette.getSegmentOrSegmentGroup().add(sg11Palette);
				    			sgList.add(sg10Palette);
				    			
				    			seqNr +=1;
				    			
				    			//level 2
				    			SegmentGroup sg10karton = new SegmentGroup();
				    			sg10karton.setName("SG10");
				    			Segment cpsKarton = new Segment();
				    			cpsKarton.setName("CPS");
				    			DE de7164Karton = new DE();
				    			de7164Karton.setName("7164");
				    			de7164Karton.setvalue(Integer.toString(seqNr));
				    			cpsKarton.getCDEOrDE().add(de7164Karton);
				    			DE de7166 = new DE();
				    			de7166.setName("7166");
				    			de7166.setvalue("" + (seqNr-1));
				    			cpsKarton.getCDEOrDE().add(de7166);
				    			sg10karton.getSegmentOrSegmentGroup().add(cpsKarton);
				    			int refKarton = seqNr;
				    			seqNr++;
				    		 	
				    			//SG11
				    			SegmentGroup sg11karton = new SegmentGroup();
				    			sg11karton.setName("SG11");
				    			//PAC level 2(VKE)
				    			Segment pacKarton = new Segment();
				    			pacKarton.setName("PAC");
				    			DE de7224Karton = new DE();
				    			de7224Karton.setName("7224");
				    			de7224Karton.setvalue("1");
				    			CDE c202Karton = new CDE();
				    			c202Karton.setName("C202");
				    			DE de7065Karton = new DE();
				    			de7065Karton.setName("7065");
				    			de7065Karton.setvalue("201");
				    			DE de3055Karton = new DE();
				    			de3055Karton.setName("3055");
				    			de3055Karton.setvalue("9");
				    			c202Karton.getDE().add(de7065Karton);
				    			c202Karton.getDE().add(de3055Karton);
				    			pacKarton.getCDEOrDE().add(de7224Karton);
				    			pacKarton.getCDEOrDE().add(c202Karton);
				    			sg11karton.getSegmentOrSegmentGroup().add(pacKarton);
				    			
				    			ResultSet grosse = stmt.executeQuery("select gewicht, masse from produkt where gtin = " + gtin);
				    			
				    			String length = null;
				    			String width = null;
				    			String gewicht = null;
				    			if(grosse.next())
				    			{
				    				String dim = grosse.getString(2);
				    				String[] s = dim.split("x");
				    				length = s[0].trim();
				    				width = s[1].trim();
				    				
				    				gewicht = grosse.getString(1);
				    			}
				    			
				    			for(int l = 0; l<3; l++)
				    			{	
				    			
					    			//MEA greutate karton
					    			Segment mea1 = new Segment();
					    			mea1.setName("MEA");
					    			DE de6311_1 = new DE();
					    			de6311_1.setName("6311");
					    			de6311_1.setvalue("PD");
					    			CDE cde502_1 = new CDE();
					    			cde502_1.setName("C502");
					    			DE de6313_1 = new DE();
					    			de6313_1.setName("6313");
					    			if(l==0)
					    				de6313_1.setvalue("AAA");
					    			else 
					    			{
					    				if(l==1)
					    					de6313_1.setvalue("WD");
						    			else 
						    				de6313_1.setvalue("LN");
					    			}
					    			cde502_1.getDE().add(de6313_1);
					    			CDE c174_1 = new CDE();
					    			c174_1.setName("C174");
					    			DE de6411_1 = new DE();
					    			de6411_1.setName("6411");
					    			DE de6314_1 = new DE();
					    			de6314_1.setName("6314");
					    			if(l==0)
					    			{
					    				de6411_1.setvalue("KGM");
					    				de6314_1.setvalue(gewicht);
					    			}
					    			else 
					    			{
					    				de6411_1.setvalue("MMT");
					    				if(l==1)
					    					de6314_1.setvalue(width);
					    				else
					    					de6314_1.setvalue(length);
					    			}
					    			
					    			c174_1.getDE().add(de6411_1);
					    			c174_1.getDE().add(de6314_1);
					    			
					    			mea1.getCDEOrDE().add(de6311_1);
					    			mea1.getCDEOrDE().add(cde502_1);
					    			mea1.getCDEOrDE().add(c174_1);
					    			sg11karton.getSegmentOrSegmentGroup().add(mea1);
				    			}
				    			
				    			SegmentGroup sg13_1 = new SegmentGroup();
				    			sg13_1.setName("SG13");
				    			Segment pci_1 = new Segment();
				    			pci_1.setName("PCI");
				    			DE de4233Karton = new DE();
				    			de4233Karton.setName("4233");
				    			de4233Karton.setvalue("33E");
				    			pci_1.getCDEOrDE().add(de4233Karton);
				    			SegmentGroup sg15_1 = new SegmentGroup();
				    			sg15_1.setName("SG15");
				    			Segment gin_1 = new Segment();
				    			gin_1.setName("GIN");
				    			DE de7405 = new DE();
				    			de7405.setName("7405");
				    			de7405.setvalue("BJ");
				    			CDE c208_1 = new CDE();
				    			c208_1.setName("C208");
				    			DE de7402 = new DE();
				    			de7402.setName("7402");
				    			BigInteger bg = new BigInteger(teilSGTIN + getBinaryPositions(sgtinPaletteList.get(0),38), 2);
				    			de7402.setvalue("" + bg);
				    			c208_1.getDE().add(de7402);
				    			gin_1.getCDEOrDE().add(de7405);
				    			gin_1.getCDEOrDE().add(c208_1);
				    			sg15_1.getSegmentOrSegmentGroup().add(gin_1);
				    			sg13_1.getSegmentOrSegmentGroup().add(pci_1);
				    			sg13_1.getSegmentOrSegmentGroup().add(sg15_1);
				    			sg11karton.getSegmentOrSegmentGroup().add(sg13_1);
				    			sg10karton.getSegmentOrSegmentGroup().add(sg11karton);
				    			
				    			
				    			//se creeaza anzahlKarton de SG17 pt fiecare karton din palette
				    			for(int k=0; k< anzahlKarton; k++)
				    			{
				    				//SG17
					    			SegmentGroup sg17  = new SegmentGroup();
					    			sg17.setName("SG17");
					    			Segment linKarton = new Segment();
					    			linKarton.setName("LIN");
					    			DE de1082 = new DE();
					    			de1082.setName("1082");
					    			de1082.setvalue("" + (k+1));
					    			CDE c212 = new CDE();
					    			c212.setName("C212");
					    			DE de7140 = new DE();
					    			de7140.setName("7140");
					    			BigInteger bg1 = new BigInteger(teilSGTINKarton + getBinaryPositions(sgtinKartonList.get(k),38), 2);
					    			de7140.setvalue("" + bg1);
					    			DE de7143 = new DE();
					    			de7143.setName("7143");
					    			de7143.setvalue("SRV");
					    			c212.getDE().add(de7140);
					    			c212.getDE().add(de7143);
					    			linKarton.getCDEOrDE().add(de1082);
					    			linKarton.getCDEOrDE().add(c212);
					    			sg17.getSegmentOrSegmentGroup().add(linKarton);
					    			sg10karton.getSegmentOrSegmentGroup().add(sg17);
				    			}
				    			sgList.add(sg10karton);
				    			
				    			//level 3
				    			for(int k=0; k< anzahlKarton; k++)
				    			{
				    				SegmentGroup sg10VKE = new SegmentGroup();
				    				sg10VKE.setName("SG10");
				    				Segment cpsVKE = new Segment();
				    				cpsVKE.setName("CPS");
				    				DE de7164VKE = new DE();
				    				de7164VKE.setName("7164");
				    				de7164VKE.setvalue("" + seqNr);
				    				seqNr++;
				    				DE de7166VKE = new DE();
				    				de7166VKE.setName("7166");
				    				de7166VKE.setvalue("" + refKarton);
				    				cpsVKE.getCDEOrDE().add(de7164VKE);
				    				cpsVKE.getCDEOrDE().add(de7166VKE);
				    				sg10VKE.getSegmentOrSegmentGroup().add(cpsVKE);
				    				
				    				//SG11
					    			SegmentGroup sg11VKE = new SegmentGroup();
					    			sg11VKE.setName("SG11");
					    			//PAC level 3(VKE)
					    			Segment pacVKE = new Segment();
					    			pacVKE.setName("PAC");
					    			DE de7224VKE = new DE();
					    			de7224VKE.setName("7224");
					    			de7224VKE.setvalue("1");
					    			CDE c202VKE = new CDE();
					    			c202VKE.setName("C202");
					    			DE de7065VKE = new DE();
					    			de7065VKE.setName("7065");
					    			de7065VKE.setvalue("CT");
					    			DE de3055VKE = new DE();
					    			de3055VKE.setName("3055");
					    			de3055VKE.setvalue("9");
					    			c202VKE.getDE().add(de7065VKE);
					    			c202VKE.getDE().add(de3055VKE);
					    			pacVKE.getCDEOrDE().add(de7224VKE);
					    			pacVKE.getCDEOrDE().add(c202VKE);
					    			sg11VKE.getSegmentOrSegmentGroup().add(pacVKE);
					    			sg10VKE.getSegmentOrSegmentGroup().add(sg11VKE);
					    			
					    			
					    			//generare qty*anzahlKarton*anzahlVKE sgtin vke
					    			String serialNrVKE= getSRNEPC(stmt);
					    			List<String> sgtinVKEList = generateSrn(anzahlVKE, serialNrVKE);
					    			insertEPC(stmt, teilSGTINVKE, sgtinVKEList, gtinVKE, glnHandler);
					    			
					    			// adaugam pentru fiecare karton de pe paleta toate produsele dintr-un carton
					    			for(int p = 0; p < anzahlVKE; p++)
					    			{
					    				//SG17
						    			SegmentGroup sg17VKE  = new SegmentGroup();
						    			sg17VKE.setName("SG17");
						    			Segment linVKE = new Segment();
						    			linVKE.setName("LIN");
						    			DE de1082VKE = new DE();
						    			de1082VKE.setName("1082");
						    			de1082VKE.setvalue("" + (p+1));
						    			CDE c212VKE = new CDE();
						    			c212VKE.setName("C212");
						    			DE de7140VKE = new DE();
						    			de7140VKE.setName("7140");
						    			BigInteger bg3 = new BigInteger(teilSGTINVKE + getBinaryPositions(sgtinVKEList.get(p),38), 2);
						    			de7140VKE.setvalue("" + bg3);
						    			DE de7143VKE = new DE();
						    			de7143VKE.setName("7143");
						    			de7143VKE.setvalue("SRV");
						    			c212VKE.getDE().add(de7140VKE);
						    			c212VKE.getDE().add(de7143VKE);
						    			linVKE.getCDEOrDE().add(de1082VKE);
						    			linVKE.getCDEOrDE().add(c212VKE);
						    			sg17VKE.getSegmentOrSegmentGroup().add(linVKE);
						    			sg10VKE.getSegmentOrSegmentGroup().add(sg17VKE);
					    			}
					    			
					    			sgList.add(sg10VKE);
				    			}
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
					    		//System.out.println("gtin_karton: " + gtin + ", gtin_vke: " + gtinVKE + ", anzahl: " + anzahl );
					    						    			
				    			
				    			for(int j = 0; j<Integer.parseInt(qty); j++)
				    			{
				    				//------kartons-------
					    			//vedem care e ultimul srn pt vpe din baza de date
						    		String serialNrKarton = getSRNEPC(stmt);
						    		
						    		//acuma tb generate 1 snr pt kartons
					    			String teilSGTIN = header + filterVPE + partition + basisNr + produktNr;
					    			List<String> sgtinList = generateSrn(1, serialNrKarton);
					    			insertEPC(stmt, teilSGTIN, sgtinList, gtin, glnHandler);
					    			//-----ende kartons------
					    			
				    				//level 1
				    				SegmentGroup sg10  = new SegmentGroup();
					    			sg10.setName("SG10");
					    			//CPS
					    			Segment cps = new Segment();
					    			cps.setName("CPS");
					    			DE de7164 = new DE();
					    			de7164.setName("7164");
					    			//System.out.println("level 1: " +seqNr);
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
					    			de7065.setvalue("CT");
					    			DE de3055 = new DE();
					    			de3055.setName("3055");
					    			de3055.setvalue("9");
					    			c202.getDE().add(de7065);
					    			c202.getDE().add(de3055);
					    			pac.getCDEOrDE().add(de7224);
					    			pac.getCDEOrDE().add(c202);
					    			sg11.getSegmentOrSegmentGroup().add(pac);
					    			sg10.getSegmentOrSegmentGroup().add(sg11);
					    			seqNr +=1;
					    			sgList.add(sg10);
					    			
					    			//level 2
					    			SegmentGroup sg10karton = new SegmentGroup();
					    			sg10karton.setName("SG10");
					    			Segment cpsKarton = new Segment();
					    			cpsKarton.setName("CPS");
					    			DE de7164Karton = new DE();
					    			de7164Karton.setName("7164");
					    			//System.out.println(seqNr);
					    			de7164Karton.setvalue(Integer.toString(seqNr));
					    			cpsKarton.getCDEOrDE().add(de7164Karton);
					    			DE de7166 = new DE();
					    			de7166.setName("7166");
					    			de7166.setvalue("" + (seqNr-1));
					    			seqNr++;
					    		 	
					    			cpsKarton.getCDEOrDE().add(de7166);
					    			sg10karton.getSegmentOrSegmentGroup().add(cpsKarton);
					    			//SG11
					    			SegmentGroup sg11karton = new SegmentGroup();
					    			sg11karton.setName("SG11");
					    			//PAC level 2(VKE)
					    			Segment pacKarton = new Segment();
					    			pacKarton.setName("PAC");
					    			DE de7224Karton = new DE();
					    			de7224Karton.setName("7224");
					    			de7224Karton.setvalue("1");
					    			CDE c202Karton = new CDE();
					    			c202Karton.setName("C202");
					    			DE de7065Karton = new DE();
					    			de7065Karton.setName("7065");
					    			de7065Karton.setvalue("CT");
					    			DE de3055Karton = new DE();
					    			de3055Karton.setName("3055");
					    			de3055Karton.setvalue("9");
					    			c202Karton.getDE().add(de7065Karton);
					    			c202Karton.getDE().add(de3055Karton);
					    			pacKarton.getCDEOrDE().add(de7224Karton);
					    			pacKarton.getCDEOrDE().add(c202Karton);
					    			sg11karton.getSegmentOrSegmentGroup().add(pacKarton);
					    			
					    			ResultSet grosse = stmt.executeQuery("select gewicht, masse from produkt where gtin = " + gtin);
					    			
					    			String length = null;
					    			String width = null;
					    			String gewicht = null;
					    			if(grosse.next())
					    			{
					    				String dim = grosse.getString(2);
					    				String[] s = dim.split("x");
					    				length = s[0].trim();
					    				width = s[1].trim();
					    				
					    				gewicht = grosse.getString(1);
					    			}
					    			
					    			for(int l = 0; l<3; l++)
					    			{	
					    			
						    			//MEA greutate karton
						    			Segment mea1 = new Segment();
						    			mea1.setName("MEA");
						    			DE de6311_1 = new DE();
						    			de6311_1.setName("6311");
						    			de6311_1.setvalue("PD");
						    			CDE cde502_1 = new CDE();
						    			cde502_1.setName("C502");
						    			DE de6313_1 = new DE();
						    			de6313_1.setName("6313");
						    			if(l==0)
						    				de6313_1.setvalue("AAA");
						    			else 
						    			{
						    				if(l==1)
						    					de6313_1.setvalue("WD");
							    			else 
							    				de6313_1.setvalue("LN");
						    			}
						    			cde502_1.getDE().add(de6313_1);
						    			CDE c174_1 = new CDE();
						    			c174_1.setName("C174");
						    			DE de6411_1 = new DE();
						    			de6411_1.setName("6411");
						    			DE de6314_1 = new DE();
						    			de6314_1.setName("6314");
						    			if(l==0)
						    			{
						    				de6411_1.setvalue("KGM");
						    				de6314_1.setvalue(gewicht);
						    			}
						    			else 
						    			{
						    				de6411_1.setvalue("MMT");
						    				if(l==1)
						    					de6314_1.setvalue(width);
						    				else
						    					de6314_1.setvalue(length);
						    			}
						    			
						    			c174_1.getDE().add(de6411_1);
						    			c174_1.getDE().add(de6314_1);
						    			
						    			mea1.getCDEOrDE().add(de6311_1);
						    			mea1.getCDEOrDE().add(cde502_1);
						    			mea1.getCDEOrDE().add(c174_1);
						    			sg11karton.getSegmentOrSegmentGroup().add(mea1);
					    			}
					    			
					    			SegmentGroup sg13_1 = new SegmentGroup();
					    			sg13_1.setName("SG13");
					    			Segment pci_1 = new Segment();
					    			pci_1.setName("PCI");
					    			DE de4233 = new DE();
					    			de4233.setName("4233");
					    			de4233.setvalue("33E");
					    			pci_1.getCDEOrDE().add(de4233);
					    			SegmentGroup sg15_1 = new SegmentGroup();
					    			sg15_1.setName("SG15");
					    			Segment gin_1 = new Segment();
					    			gin_1.setName("GIN");
					    			DE de7405 = new DE();
					    			de7405.setName("7405");
					    			de7405.setvalue("BJ");
					    			CDE c208_1 = new CDE();
					    			c208_1.setName("C208");
					    			DE de7402 = new DE();
					    			de7402.setName("7402");
					    			BigInteger bg = new BigInteger(teilSGTIN + getBinaryPositions(sgtinList.get(0),38), 2);
					    			de7402.setvalue("" + bg);
					    			c208_1.getDE().add(de7402);
					    			gin_1.getCDEOrDE().add(de7405);
					    			gin_1.getCDEOrDE().add(c208_1);
					    			sg15_1.getSegmentOrSegmentGroup().add(gin_1);
					    			sg13_1.getSegmentOrSegmentGroup().add(pci_1);
					    			sg13_1.getSegmentOrSegmentGroup().add(sg15_1);
					    			sg11karton.getSegmentOrSegmentGroup().add(sg13_1);
					    			sg10karton.getSegmentOrSegmentGroup().add(sg11karton);
					    			
					    			//-------aici incep vke----------
					    			//generare snr pt (anzahl der vke) dintr-un carton
					    			String serialNrVKE = getSRNEPC(stmt);
					    			String produktNrVKE = getBinaryPositions(gtinVKE.substring(8,13),17);
					    			teilSGTIN = header + filterVKE + partition + basisNr + produktNrVKE;
					    			List<String> sgtinListVKE = generateSrn(anzahl, serialNrVKE);
					    			insertEPC(stmt, teilSGTIN, sgtinListVKE, gtinVKE, glnHandler);
					    			
					    			for(int k = 0; k<anzahl; k++)
					    			{
					    				//SG17
						    			SegmentGroup sg17  = new SegmentGroup();
						    			sg17.setName("SG17");
						    			Segment linVKE = new Segment();
						    			linVKE.setName("LIN");
						    			DE de1082 = new DE();
						    			de1082.setName("1082");
						    			de1082.setvalue("" + (k+1));
						    			CDE c212 = new CDE();
						    			c212.setName("C212");
						    			DE de7140 = new DE();
						    			de7140.setName("7140");
						    			BigInteger bg2 = new BigInteger(teilSGTIN + getBinaryPositions(sgtinListVKE.get(k),38), 2);
						    			de7140.setvalue("" + bg2);
						    			DE de7143 = new DE();
						    			de7143.setName("7143");
						    			de7143.setvalue("SRV");
						    			c212.getDE().add(de7140);
						    			c212.getDE().add(de7143);
						    			linVKE.getCDEOrDE().add(de1082);
						    			linVKE.getCDEOrDE().add(c212);
						    			sg17.getSegmentOrSegmentGroup().add(linVKE);
						    			sg10karton.getSegmentOrSegmentGroup().add(sg17);
						    			
					    			}
					    			sgList.add(sg10karton);
				    			}
					    	}
					    	
					    	//atunci e vke
					    	else
					    	{
					    		String serialNr = getSRNEPC(stmt);
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
					    			BigInteger bg = new BigInteger(teilSGTIN + getBinaryPositions(sgtinList.get(p),38), 2);
					    			de7140.setvalue("" + bg);
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
			
			//UNT rechnung
			((DE)((Message)interchange.getMessageOrMsgGroup().get(0)).getTrailer().getSegment().getCDEOrDE()
					.get(0)).setvalue("" + getSegmentCount(interchange, doc, builder, xpath));
			
			//unt lieferavis
			((DE)((Message)dispatch.getMessageOrMsgGroup().get(0)).getTrailer().getSegment().getCDEOrDE()
					.get(0)).setvalue("" + getSegmentCount(dispatch, doc, builder, xpath));
			
			stmt.close();
			
			JAXBContext jc2 = JAXBContext.newInstance("paper4all.messages");
			Marshaller m = jc2.createMarshaller();
			
			File interch = new File("interch.xml");
			m.marshal(interchange, interch);
			String inter = getInput(interch);
			interch.delete();
			//System.out.println(inter);
			docum.add(inter);
			
			File des = new File("dispatch.xml");
			m.marshal(dispatch, des);
			String disp = getInput(des);
			des.delete();
			//System.out.println(disp);
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
				String sgtinStuck = teilSGTIN + getBinaryPositions(s, 38);
				BigInteger bg = new BigInteger(sgtinStuck, 2);
				//System.out.println(sgtinStuck + " : " + sgtinStuck.length() + ", " + bg + " : " + bg.bitLength());
				stmt.executeQuery("insert into epc values("+ bg + "," + gtin + ", " 
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
	
	private String getBinaryPositions(String s, int pos)
	{
		String temp = Long.toBinaryString(Long.parseLong(s));
		while(temp.length() < pos)
		{
			temp = "0" + temp;
		}
		return temp;
	}
	
	private int getSegmentCount(Interchange dispatch, Document doc, DocumentBuilder builder, XPath xpath)
	{
		try
		{
			JAXBContext jc2 = JAXBContext.newInstance("paper4all.messages");
			Marshaller m = jc2.createMarshaller();
			
			File des_count = new File("count_dispatch.xml");
			m.marshal(dispatch, des_count);
			doc = builder.parse(des_count);
		    
		    //anzahl der segmente
			String xpath_query = "/Interchange/Message//Segment";
			XPathExpression expr_count = xpath.compile(xpath_query);
		    Object count_segments = expr_count.evaluate(doc, XPathConstants.NODESET);
		    NodeList countNode= (NodeList) count_segments;
		    
		    int unt_counter = countNode.getLength();
		    des_count.delete();
		    return unt_counter;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}	
}	
