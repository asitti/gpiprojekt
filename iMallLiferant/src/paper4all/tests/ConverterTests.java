package paper4all.tests;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

 
public class ConverterTests {
	
	private final static String filename = "edi2xml.rb";
 
	private ConverterTests() 
	{
		try
		{
		//System.out.println("[" + getClass().getName() + "]");		
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			
			ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
			container.setArgv(new String[]{"edi/in2.edi"});
			//container.setOutput(ps);
		    BufferedReader stdin =
		        new BufferedReader( new FileReader("edi/in2.edi"));
	
			container.runScriptlet(stdin, filename);
			//container.runScriptlet(PathType.CLASSPATH, filename);
			String content = baos.toString();
			System.out.println(content);
			baos.close();
			stdin.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	 
	public static void main(String[] args) {
		new ConverterTests();
	}
}
