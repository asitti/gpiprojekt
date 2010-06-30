package paper4all.tests;

import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;
 
public class ConverterTests {
	
	private final static String filename = "ruby/tree_with_ivars.rb";
 
	private ConverterTests() 
	{
		System.out.println("[" + getClass().getName() + "]");
		ScriptingContainer container = new ScriptingContainer();
		 
		Object receiver = container.runScriptlet(PathType.CLASSPATH, filename);
		container.put("@name", "cherry blossom");
		container.put("@shape", "oval");
		container.put("@foliage", "deciduous");
		container.put("@color", "pink");
		container.put("@bloomtime", "March - April");
		container.callMethod(receiver, "update", Object.class);
		System.out.println(container.callMethod(receiver, "to_s", String.class));
		 
		container.put("@name", "cedar");
		container.put("@shape", "pyramidal");
		container.put("@foliage", "evergreen");
		container.put("@color", "nondescript");
		container.put("@bloomtime", "April - May");
		container.callMethod(receiver, "update", Object.class);
		System.out.println(container.callMethod(receiver, "to_s", String.class));
	}
	 
	public static void main(String[] args) {
		new ConverterTests();
	}
}
