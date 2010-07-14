package paper4all.tests;

import java.io.BufferedInputStream;
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
			/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
			stdin.close();*/
			
			//Process p = Runtime.getRuntime().exec("ruby C:/Ruby187/bin/edi2xml.rb in/in1.edi");
			// C:\Ruby187\lib\ruby\gems\1.8\gems\edi4r-0.9.5.2\bin
			
			Process p = Runtime.getRuntime().exec("ruby C:/Ruby187/lib/ruby/gems/1.8/gems/edi4r-0.9.5.2/bin/xml2edi.rb in/testtest.xml");
			//BufferedReader r = new BufferedReader(new InputStreamReader(p.getOutputStream()));
			//String s = System.getProperty("line.separator");
			//String line;
			//StringBuilder sb = new StringBuilder();
			
			//while((line = r.readLine()) != null)
			/*int i = 0;
			while(i < 5)
			{
				line = r.readLine();
				System.out.println(line);
				//sb.append(line).append(s);
				i++;
			}*/
			
			
			//System.out.println(p.exitValue());
			
			BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
			
			String consoleOutput = "";
            int i;
            while ((i = bis.read()) != -1) {
                consoleOutput += (char)i;
            }
            
            System.out.println(consoleOutput);
            /*
			
			System.out.println(sb);*/
			
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
