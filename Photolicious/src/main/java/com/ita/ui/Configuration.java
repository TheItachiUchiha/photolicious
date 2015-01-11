package com.ita.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.ita.utils.CommonConstants;
import com.ita.utils.PhotoliciousUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class Configuration implements Initializable {
	
	@FXML
    private Label message;

    @FXML
    private ComboBox<String> printer;

    @FXML
    private ComboBox<String> printerSize;

    @FXML
    private ComboBox<String> slideTime;
    
    String currentSlideTime;
    String currentSize;
    String currentPrinter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		currentSlideTime = PhotoliciousUtils.readSlideTime();
		currentSize = PhotoliciousUtils.readPrintSize();
		currentPrinter = PhotoliciousUtils.readDefaultPrinter();
		
		if(!(null==currentSlideTime || "".equals(currentSlideTime)))
		{
			slideTime.getSelectionModel().select(currentSlideTime);
		}
		if(!(null==currentSize || "".equals(currentSize)))
		{
			printerSize.getSelectionModel().select(currentSize);
		}
		if(!(null==currentPrinter || "".equals(currentPrinter)))
		{
			printer.getSelectionModel().select(currentPrinter);
		}
		
	}
	
	public void saveConfiguration()
	{
		PhotoliciousUtils.saveConfiguration(printer.getSelectionModel().getSelectedItem(), printerSize.getSelectionModel().getSelectedItem(), slideTime.getSelectionModel().getSelectedItem());
		message.setText(CommonConstants.SUCCESS);
	}

}
