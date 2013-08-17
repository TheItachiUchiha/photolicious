package service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class PrintImage {  
	
	public PrintService[] getPrintServices()
	{
		PrintService printServices[];
		try{
			printServices = PrintServiceLookup.lookupPrintServices(null, null);
	        System.out.println("Number of print services: " + printServices.length);

	        for (PrintService printer : printServices)
	            System.out.println("Printer: " + printer.getName()); 
	    }
		catch(Exception e)
		{
			throw e;
		}
		return printServices;
	}
	
	public static void main(String args[])
	{
		new PrintImage().getPrintServices();
	}
        
}  
