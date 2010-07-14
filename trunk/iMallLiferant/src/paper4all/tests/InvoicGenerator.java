package paper4all.tests;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import paper4all.messages.CDE;
import paper4all.messages.DE;
import paper4all.messages.Message;
import paper4all.messages.Segment;
import paper4all.messages.SegmentGroup;


public class InvoicGenerator {
	
	private paper4all.messages.Interchange interchange;
	
	public InvoicGenerator()
	{
		try
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.messages");
			Unmarshaller u = jc.createUnmarshaller();
			this.interchange = (paper4all.messages.Interchange) u.unmarshal(new File("src/paper4all/templates/invoic_template.xml"));
			//System.out.println(interchange.getHeader().getSegment().getCDE().getName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gibt die komplette (generierte) Rechnung als Datei zurueck
	 * 
	 * @return
	 */
	public void generateInvoice()
	{
		//File xmlInvoic = new File("invoic.xml");
		//FileWriter fw = new FileWriter(xmlInvoic);
		//List<CDE> cde1 = interchange.getHeader().getSegment().getCDE();
		/*List<Object> list = interchange.getHeader().getSegment().getCDEOrDE();
		for(Object o : list)
		{
			if(o instanceof CDE)
			{
				CDE cde = (CDE) o;
				List<DE> de = cde.getDE();
				for(DE obj : de)
				{
					if(obj.getName().equals("0004"))
						obj.setvalue("2965197100002");
				}
			}
		}*/
		
		SegmentGroup sg = new SegmentGroup();
		sg.setName("SG26");
		Segment s = new Segment();
		s.setName("LIN");
		DE de1 = new DE();
		de1.setName("1082");
		de1.setvalue("1");
		CDE cde = new CDE();
		cde.setName("C212");
		DE de2 = new DE();
		de2.setName("7140");
		de2.setvalue("2965197100125");
		DE de3 = new DE();
		de3.setName("7143");
		de3.setvalue("SRV");
		cde.getDE().add(de2);
		cde.getDE().add(de3);
		s.getCDEOrDE().add(de1);
		s.getCDEOrDE().add(cde);
		sg.getSegmentOrSegmentGroup().add(s);
		
		List<Object> list = interchange.getMessageOrMsgGroup();
		int index = 0;
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i) instanceof Message)
			{
				Message msg = (Message) list.get(i);
				List<Object> list2 = msg.getSegmentOrSegmentGroup();
				for(int j = 0; j < list2.size(); j++)
				{
					if(list2.get(j) instanceof SegmentGroup)
					{
						// SegmentGroup
						SegmentGroup temp = (SegmentGroup) list2.get(j);
						if(temp.getName().equals("SG8"))
						{
							index = j;
							break;
						}
					}
				}
			}
		}
		
		Message msg = (Message) interchange.getMessageOrMsgGroup().get(0);
		msg.getSegmentOrSegmentGroup().add(index + 1, sg);
		//interchange.
		
	}
	
	public paper4all.messages.Interchange getInterchange()
	{
		return this.interchange;
	}
	
	public void marshall(paper4all.messages.Interchange i)
	{
		File output = new File("src/paper4all/messages/result.xml");
		try 
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.messages");
			Marshaller m = jc.createMarshaller();
			m.marshal(i, output);
		} 
		catch (JAXBException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InvoicGenerator gen = new InvoicGenerator();
		gen.generateInvoice();
		gen.marshall(gen.getInterchange());
	}

}
