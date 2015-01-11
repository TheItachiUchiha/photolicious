package service;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;

import com.sun.javafx.print.Units;

import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

public class PrintImage8 extends Task<String> {

	String file=null;
	String size=null;
	String printerName=null;
	static Printer printer = null;
	
	public PrintImage8() {
		
	}
	
	public PrintImage8(String file, String size, String printerName){
		this.file= file;
		this.size= size;
		this.printerName= printerName;
	}
	
	public static void printImage8(String file, String size, String printerName) {
	    try {
	        Image img = new Image("file:" + file);
	        ImageView imageView = new ImageView(img);
	        Paper paper = null;
	        
	        /*if(size.equalsIgnoreCase("3r")){
	        	Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                        double.class, double.class, Units.class);
				c.setAccessible(true);
				paper = c.newInstance("3x5", 3.5, 5, Units.INCH);
	        }
	        if(size.equalsIgnoreCase("4r")){
	        	Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                        double.class, double.class, Units.class);
				c.setAccessible(true);
				paper = c.newInstance("4x6", 6, 4, Units.INCH);
	        }
	        else if(size.equalsIgnoreCase("5r")){
	        	Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                        double.class, double.class, Units.class);
				c.setAccessible(true);
				paper = c.newInstance("5x7", 5, 7, Units.INCH);
	        }
	        else if(size.equalsIgnoreCase("6r")){
	        	Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                        double.class, double.class, Units.class);
				c.setAccessible(true);
				paper = c.newInstance("6x8", 6, 8, Units.INCH);
	        }*/
	        
	        ObservableSet<Printer> printerList = Printer.getAllPrinters();
	        for(Printer tempPrinter:printerList){
	        	if(tempPrinter.getName().equalsIgnoreCase(printerName)){
	        		printer = tempPrinter;
	        	}
	        }
	        
	        	boolean found = false;
	        	PageLayout pageLayout = null;
	        	Set<Paper> paperList = printer.getPrinterAttributes().getSupportedPapers();
	        	Iterator<Paper> iterator = paperList.iterator();
	        	while(iterator.hasNext()){
	        		paper = iterator.next();
	        		if(size.equalsIgnoreCase(paper.getName())) {
	        			found = true;
	        			pageLayout = printer.createPageLayout(paper, PageOrientation.PORTRAIT, 0.0, 0.0, 0.0, 0.0);
	        			break;
	        		}
	        	}
	        	if(!found){
	        		if(size.equalsIgnoreCase("4r Potrait")){
	    	        	Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
	                            double.class, double.class, Units.class);
	    				c.setAccessible(true);
	    				paper = c.newInstance("4r Potrait", 4, 6, Units.INCH);
	    				imageView.setRotate(90);
	    				pageLayout = printer.createPageLayout(paper, PageOrientation.PORTRAIT, 0.0, 0.0, 0.0, 0.0);
	    	        } else if(size.equalsIgnoreCase("4r Landspace")) {
	    	        	Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
	                            double.class, double.class, Units.class);
	    				c.setAccessible(true);
	    				paper = c.newInstance("4r Landspace", 6, 4, Units.INCH);
	    				pageLayout = printer.createPageLayout(paper, PageOrientation.LANDSCAPE, 0.0, 0.0, 0.0, 0.0);
	    	        }
	        	}
	        
		        double scaleX = pageLayout.getPrintableWidth() / imageView.getBoundsInParent().getWidth();
		        double scaleY = pageLayout.getPrintableHeight() / imageView.getBoundsInParent().getHeight();
		        imageView.getTransforms().add(new Scale(scaleX, scaleY));
			    PrinterJob job = PrinterJob.createPrinterJob(printer);
			    if (job != null) {
			        System.out.println("Job created!");
			        boolean success = job.printPage(pageLayout, imageView);
			        if (success) {
			            System.out.println("Job successfully finished!");
			            job.endJob();
			        } else {
			            System.out.println("Job NOT successful!");
			        }
			    }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	public ObservableSet<Printer> printerList() {
		ObservableSet<Printer> printers = Printer.getAllPrinters();
		return printers;
	}
	
	@Override
	protected String call() throws Exception {
        	printImage8(this.file, this.size, this.printerName);
        return null;
	}
	
}
