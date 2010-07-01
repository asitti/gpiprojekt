package paper4all.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

 
public class ConverterTests {
	
	private final static String filename = "xml2edi.rb";
 
	private ConverterTests() 
	{
		//System.out.println("[" + getClass().getName() + "]");		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		
		ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
		container.setArgv(new String[]{"edi/in1.xml"});
		container.setOutput(ps);
		container.runScriptlet(PathType.CLASSPATH, filename);
		String content = baos.toString();
		System.out.println(content);
		
	}
	 
	public static void main(String[] args) {
		new ConverterTests();
	}
}
