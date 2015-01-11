package kc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javafx.collections.ObservableSet;
import javafx.print.Printer;

public class PhotoliciousUtils {
	public static void saveConfiguration(String defaultPrinter,
			String printSize, String slideTime) {
		try {
			Properties props = new Properties();
			props.setProperty("defaultPrinter", defaultPrinter);
			props.setProperty("printSize", printSize);
			props.setProperty("slideTime", slideTime);
			File f = new File("settings.properties");
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Default Properties Saved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readDefaultPrinter() {
		String defaultPrinter = null;
		try {
			File f = new File("settings.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			defaultPrinter = p.getProperty("defaultPrinter");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultPrinter;
	}

	public static String readPrintSize() {
		String printSize = null;
		try {
			File f = new File("settings.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			printSize = p.getProperty("printSize");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return printSize;
	}

	public static String readSlideTime() {
		String slideTime = null;
		try {
			File f = new File("settings.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			slideTime = p.getProperty("slideTime");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return slideTime;
	}

	public static File[] filterJPEGImagesFromFolder(File[] folder) {
		List<File> files = new ArrayList<File>();
		File[] listOfJPEGImages = null;
		int i = 0;
		try {
			for (File file : folder) {
				if (file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".JPG")
						|| file.getName().endsWith(".JPEG")
						|| file.getName().endsWith(".jpeg")) {
					files.add(file);
				}
			}
			listOfJPEGImages = new File[files.size()];
			for (Object object : files) {
				listOfJPEGImages[i] = (File) object;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfJPEGImages;
	}

	public static File[] filterImagesFromFolder(File[] folder) {
		List<File> files = new ArrayList<File>();
		File[] listOfImages = null;
		int i = 0;
		try {
			for (File file : folder) {
				if (file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".jpeg")
						|| file.getName().endsWith(".png")
						|| file.getName().endsWith(".JPG")
						|| file.getName().endsWith(".JPEG")
						|| file.getName().endsWith(".PNG")) {
					files.add(file);
				}
			}
			listOfImages = new File[files.size()];
			for (Object object : files) {
				listOfImages[i] = (File) object;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfImages;
	}

	public static List<String> nameOfFiles(File[] files) {
		List<String> name = new ArrayList<String>();
		try {
			for (File file : files) {
				name.add(file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	public static void saveOutputFolder(String outputFolder) {
		try {
			Properties props = new Properties();
			props.setProperty("output", outputFolder);
			File f = new File("../outputFolder.properties");
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Default Properties Saved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readOutputFolder() {
		String outputFolder = null;
		try {
			File f = new File("../outputFolder.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			outputFolder = p.getProperty("output");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFolder;
	}
	
	public static boolean configurationExists(){
		String slideTime = null;
		String defaultPrinter = null;
		String printSize = null;
		try{
			File file = new File("settings.properties");
			if(file.exists()){
				InputStream in = new FileInputStream(file);
				Properties p = new Properties();
				p.load(in);
				defaultPrinter = p.getProperty("defaultPrinter");
				slideTime = p.getProperty("slideTime");
				printSize = p.getProperty("printSize");
				if(null != defaultPrinter && 
						!"".equals(defaultPrinter) &&
						null != slideTime && 
						!"".equals(slideTime) &&
						null != printSize &&
						!"".equals(printSize)){
					return true;
				}
			} 
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean printerExists(String printerName){
		ObservableSet<Printer> printerList = Printer.getAllPrinters();
        for(Printer tempPrinter:printerList){
        	if(tempPrinter.getName().equalsIgnoreCase(printerName)){
        		return true;
        	}
        }
        return false;
	}

	public static void main(String args[]) {
		System.out.println(new PhotoliciousUtils()
				.configurationExists());
	}
}