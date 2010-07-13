package paper4all.services;


import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="OrderProcessorWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class OrderProcessor 
{
	
	@WebMethod(operationName="process-incoming-order") 
	@WebResult(name = "success-result") 
	public boolean processOrder( @WebParam(name="xmlOrderContent")String input) 
	{ 
		return false;
	}
}
