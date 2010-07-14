package paper4all.tests;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import paper4all.nachrichten.CDE;


public class InvoicGenerator {
	
	private paper4all.nachrichten.Interchange interchange;
	
	public InvoicGenerator()
	{
		try
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.INVOIC");
			Unmarshaller u = jc.createUnmarshaller();
			this.interchange = (paper4all.nachrichten.Interchange) u.unmarshal(new File("src/paper4all/templates/invoic_template.xml"));
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
	public File generateInvoice()
	{
		//File xmlInvoic = new File("invoic.xml");
		//FileWriter fw = new FileWriter(xmlInvoic);
		//List<CDE> cde1 = interchange.getHeader().getSegment().getCDE();
		return null;
	}
	
	public paper4all.nachrichten.Interchange getInterchange()
	{
		return this.interchange;
	}
	
	public void marshall(paper4all.nachrichten.Interchange i)
	{
		File output = new File("src/paper4all/INVOIC/result.xml");
		try 
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.INVOIC");
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
		//gen.marshall(gen.getInterchange());
	}

}
