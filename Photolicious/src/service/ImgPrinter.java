package service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kc.vo.PrintServiceVO;

public class ImgPrinter extends Task {
	private String file;
    private String size;
    private String printerName;
    private PrintService printer;
    
    public ImgPrinter(String file, String size, String printerName) {
		this.file = file;
		this.size = size;
		this.printerName = printerName;
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

	@Override
	protected Object call() throws Exception {
		for(PrintServiceVO tempPrinter:printerList()){
        	if(tempPrinter.toString().equalsIgnoreCase(printerName)){
        		this.printer = tempPrinter.getPrintService();
        	}
        }
		new PrintImage().printPage(this.file, this.size, this.printer);
		return null;
	}
	
	
}
