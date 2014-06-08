package service;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.JPanel;

import kc.vo.PrintServiceVO;

public class PrintImage extends Task {
	
	String file=null;
	
	public PrintImage()
	{
		
	}
	
	public PrintImage(String file)
	{
		this.file=file;
	}


	public void print(String file, PrintService printService, final Stage stage)
			throws FileNotFoundException, InterruptedException, PrintException {
		String filename = file;
		DocFlavor flavor = DocFlavor.INPUT_STREAM.GIF;
		DocPrintJob job = printService.createPrintJob();
		PrintJobListener listener = new PrintJobAdapter() {
			public void printDataTransferCompleted(PrintJobEvent e) {
				stage.close();
			}
		};
		job.addPrintJobListener(listener);
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		FileInputStream fis = new FileInputStream(filename);
		DocAttributeSet das = new HashDocAttributeSet();
		Doc doc = new SimpleDoc(fis, flavor, das);
		job.print(doc, pras);
		Thread.sleep(3000);
	}

	public ObservableList<PrintServiceVO> printerList() {
		ObservableList<PrintServiceVO> observableList = FXCollections
				.observableArrayList();
		PrintService printService[] = PrinterJob.lookupPrintServices();
		for (PrintService printService2 : printService) {
			PrintServiceVO printServiceVO = new PrintServiceVO();
			printServiceVO.setPrintService(printService2);
			observableList.add(printServiceVO);
		}
		return observableList;
	}
	
	public static void printImage(String file) {
	    //new Painter();

	    MediaTracker tracker = new MediaTracker(new JPanel());

	    try {
	        Image img = ImageIO.read(new File(file));
	        tracker.addImage(img, 1);
	        tracker.waitForAll();
	        print(img);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

	private static void print(final Image img) {
	    PrinterJob printjob = PrinterJob.getPrinterJob();
	    printjob.setJobName("Print");

	    ImgPrinter printable = new ImgPrinter(img);

	    try {
	        System.out.println("Printing.");
	        printable.printPage();
	    } catch (PrinterException ex) {
	        System.out.println("NO PAGE FOUND." + ex);
	    }										
	}

	private static class ImgPrinter implements Printable {

	    Image img;
	    //private int currentPage = -1;

	    public ImgPrinter(Image img) {
	        this.img = img;
	    }

	    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
	        if (pageIndex != 0) {
	            return Printable.NO_SUCH_PAGE;
	        }

	        //BufferedImage bufferedImage = new BufferedImage(img.getWidth(null),
	        //img.getHeight(null), BufferedImage.TYPE_INT_RGB);
	        //bufferedImage.getGraphics().drawImage(img, 0, 0, null);

	        Graphics2D g2 = (Graphics2D) graphics;
	        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	        g2.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
	        return Printable.PAGE_EXISTS;
	    	
	    	
	    	
	    	/*if (pageIndex != 0) {
	            return Printable.NO_SUCH_PAGE;
	        }
	        


	        Graphics2D graphics2D = (Graphics2D) graphics;

	        int width = (int)Math.round(pageFormat.getImageableWidth());
	        int height = (int)Math.round(pageFormat.getImageableHeight());

	        if (currentPage != pageIndex || img == null) {
	            currentPage = pageIndex;    

	            

	            BufferedImage imageCopy = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = imageCopy.createGraphics();
	            g2d.drawImage(imageCopy, 0, 0, null);
	            g2d.dispose();

	            double scaleFactor = getScaleFactorToFit(new Dimension(imageCopy.getWidth(), imageCopy.getHeight()), new Dimension(width, height));

	            int imageWidth = (int)Math.round(imageCopy.getWidth() * scaleFactor);
	            int imageHeight = (int)Math.round(imageCopy.getHeight() * scaleFactor);

	            double x = ((pageFormat.getImageableWidth() - imageWidth) / 2) + pageFormat.getImageableX();
	            double y = ((pageFormat.getImageableHeight() - imageHeight) / 2) + pageFormat.getImageableY();

	            img = imageCopy.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

	        }

	        graphics2D.drawRenderedImage(img, AffineTransform.getTranslateInstance(x, y));

	        return PAGE_EXISTS;*/
	    }

	    public void printPage() throws PrinterException {
	        try{
	    	PrinterJob job = PrinterJob.getPrinterJob();
	    	PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
	        //PageFormat pf = job.pageDialog(aset);
	        //job.setPrintable(new PrintDialogExample(), pf);
	    	job.setJobName("TEST JOB");
            PageFormat pf = job.pageDialog(aset);
            job.setPrintable(this, pf);
	        boolean ok = job.printDialog(aset);
	        if (ok) {
	            
	            job.print(aset);
	        }
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	    }
	    
	    /*public double getScaleFactor(int iMasterSize, int iTargetSize) {
	        double dScale = 1;
	        if (iMasterSize > iTargetSize) {
	            dScale = (double) iTargetSize / (double) iMasterSize;
	        } else {
	            dScale = (double) iTargetSize / (double) iMasterSize;
	        }
	        return dScale;
	    }

	    public double getScaleFactorToFit(BufferedImage img, Dimension size) {
	        double dScale = 1;
	        if (img != null) {
	            int imageWidth = img.getWidth();
	            int imageHeight = img.getHeight();
	            dScale = getScaleFactorToFit(new Dimension(imageWidth, imageHeight), size);
	        }
	        return dScale;
	    }

	    public double getScaleFactorToFit(Dimension original, Dimension toFit) {
	        double dScale = 1d;
	        if (original != null && toFit != null) {
	            double dScaleWidth = getScaleFactor(original.width, toFit.width);
	            double dScaleHeight = getScaleFactor(original.height, toFit.height);

	            dScale = Math.min(dScaleHeight, dScaleWidth);
	        }
	        return dScale;
	    }*/
	}
	
	public static void main(String args[])
	{
		PrintImage.printImage("C:\\Users\\Abhinay_Kryptcoder\\Desktop\\Untitled.jpg");
		
		
	}

	@Override
	protected Object call() throws Exception {

		printImage(this.file);
		return null;
	}

}
