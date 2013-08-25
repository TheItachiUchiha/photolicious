package kc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
	
	
	public static File[] filterJPEGImagesFromFolder (File[] folder)
	{
		List<File> files = new ArrayList<File>();
		int i=0;
		for(File file:folder)
		{
			if(file.getName().endsWith(".jpg")||file.getName().endsWith(".JPG"))
			{
				files.add(file);
			}
		}
		File[] listOfJPEGImages = new File[files.size()];
		for(Object object:files)
		{
			listOfJPEGImages[i] = (File)object;
			i++;
		}
		return listOfJPEGImages;
	}
	
	
	public static File[] filterImagesFromFolder (File[] folder)
	{
		List<File> files = new ArrayList<File>();
		int i=0;
		for(File file:folder)
		{
			if(file.getName().endsWith(".jpg")||file.getName().endsWith(".png")
					|| file.getName().endsWith(".JPG") || file.getName().endsWith(".PNG"))
			{
				files.add(file);
			}
		}
		File[] listOfImages = new File[files.size()];
		for(Object object:files)
		{
			listOfImages[i] = (File)object;
			i++;
		}
		return listOfImages;
	}
	
	public static List<String> nameOfFiles(File[] files)
	{
		List<String> name = new ArrayList<String>();
		for(File file:files)
		{
			name.add(file.getName());
		}
		return name;
	}
	
	
	public static void main(String args[]) {
		File file = new File("C:\\Users\\HOME\\Pictures\\Google Talk Received Images");
		new PhotoliciousUtils().filterJPEGImagesFromFolder(file.listFiles());
	}
}