package paper4all.services;


import java.io.File;
import java.io.FilenameFilter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="DeleteFileWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class DeleteFile 
{
	
	@WebMethod(operationName="delete-file-from-hub") 
	@WebResult(name = "success-result") 
	public boolean fileToDelete( @WebParam(name="fileName")String filename) 
	{ 
		File f = new File("in/"+filename);
		return f.delete();
	}
}
