package paper4all.tests;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import paper4all.messages.Interchange;
import paper4all.messages.Message;


public class XMLtoJava 
{

	public static void main(String[] args)
	{
		try 
		{
			JAXBContext jc = JAXBContext.newInstance("paper4all.rubyParser");
			Unmarshaller u = jc.createUnmarshaller();
		    Interchange interchange = (Interchange ) u.unmarshal(new File("edi/in1.xml"));
		    if(interchange != null)
		    {
		    	List<Object> msg = interchange.getMessageOrMsgGroup();
		    	for(Object o : msg)
		    	{
		    		if(o instanceof Message)
		    		{
		    			Message m = (Message)o;
		    			System.out.println("message trailer:" + m.getTrailer().getSegment().getName());
		    		}
		    	}
		    	System.out.println(interchange.getMessageOrMsgGroup().size());
		    	System.out.println(interchange.getTrailer().getSegment().getName());
		    }
		} 
		catch (JAXBException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
