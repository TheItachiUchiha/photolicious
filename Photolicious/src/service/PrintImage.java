package service;

import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

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

import kc.vo.PrintServiceVO;

public class PrintImage {


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

}
