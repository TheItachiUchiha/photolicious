package kc.vo;

import javax.print.PrintService;

public class PrintServiceVO {

	private PrintService printService;
	
	
	
	public PrintService getPrintService() {
		return printService;
	}



	public void setPrintService(PrintService printService) {
		this.printService = printService;
	}



	@Override
    public String toString() {
        return printService.getName();
    }
	
}
