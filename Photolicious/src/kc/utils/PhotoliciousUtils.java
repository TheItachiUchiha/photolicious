package kc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PhotoliciousUtils 
{
	public void saveDefaultPrinter(String defaultPrinter) {
		 try {
		        Properties props = new Properties();
		        props.setProperty("defaultPrinter", defaultPrinter);
		        File f = new File("resources/printer.properties");
		        OutputStream out = new FileOutputStream( f );
		        props.store(out, "Default Properties Saved");
		    }
		    catch (Exception e ) {
		        e.printStackTrace();
		    }
	}
	
	public String readDefaultPrinter()
	{
		String defaultPrinter = null;
		try{
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("printer.properties");
			Properties p = new Properties();
			p.load(in);
			defaultPrinter = p.getProperty("defaultPrinter");
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
		return defaultPrinter;
	}
	
	public static void main(String args[]) {
		new PhotoliciousUtils().saveDefaultPrinter("hi");
	}
}