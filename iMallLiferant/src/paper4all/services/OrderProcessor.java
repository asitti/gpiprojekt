package paper4all.services;


import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import paper4all.ORDERS.CDE;
import paper4all.ORDERS.DE;
import paper4all.ORDERS.Header;
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
			
			List<SegmentGroup> segGroup = interchange.getMessage().getSegmentGroup();
			
			for(SegmentGroup sg : segGroup)
			{
				if(sg.getName().equals("SG28"))
				{
					List<Segment> linAndQty = sg.getSegment();
					for(Segment s : linAndQty)
					{
						if()
					}
				}
			}
			
			
			
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
