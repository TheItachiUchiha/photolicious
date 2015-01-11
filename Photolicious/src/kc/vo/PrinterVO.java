package kc.vo;

import javafx.print.Printer;

public class PrinterVO {

	private Printer printer;
	
	public Printer getPrinter() {
		return printer;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

	@Override
    public String toString() {
        return printer.getName();
    }
	
}
