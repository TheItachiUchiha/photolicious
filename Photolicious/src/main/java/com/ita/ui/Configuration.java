package com.ita.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import com.ita.service.PrintImage;
import com.ita.utils.CommonConstants;
import com.ita.utils.PhotoliciousUtils;
import com.ita.vo.PrintServiceVO;

public class Configuration implements Initializable {

	@FXML
	private Label message;

	@FXML
	private ComboBox<PrintServiceVO> printer;

	@FXML
	private ComboBox<String> printerSize;

	@FXML
	private ComboBox<String> slideTime;

	String currentSlideTime;
	String currentSize;
	String currentPrinter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Get the list of Printers currently installed
		ObservableList<PrintServiceVO> printerList = PhotoliciousUtils.printerList();
		//Set the List to the combo box
		printer.setItems(printerList);
		//Read default parameters from the properties file
		if (PhotoliciousUtils.configurationExists()) {
			currentSlideTime = PhotoliciousUtils.readSlideTime();
			currentSize = PhotoliciousUtils.readPrintSize();
			currentPrinter = PhotoliciousUtils.readDefaultPrinter();
		} else {
			PhotoliciousUtils.saveConfiguration("", "", "");
		}
		// Check and set the default values
		if(!(null==currentSlideTime || "".equals(currentSlideTime))) {
			slideTime.getSelectionModel().select(currentSlideTime);
		}
		if(!(null==currentSize || "".equals(currentSize))) {
			printerSize.getSelectionModel().select(currentSize);
		}
		if(!(null==currentPrinter || "".equals(currentPrinter))) {
			for(PrintServiceVO tempPrinter : printerList) {
				if (tempPrinter.toString().equalsIgnoreCase(currentPrinter)) {
					printer.getSelectionModel().select(tempPrinter);
					break;
				}
			}
		}
	}

	public void saveConfiguration() {
		if (null != printer.getSelectionModel().getSelectedItem()
				&& null != printerSize.getSelectionModel().getSelectedItem()
				&& null != slideTime.getSelectionModel().getSelectedItem()) {
			PhotoliciousUtils.saveConfiguration(printer.getSelectionModel()
					.getSelectedItem().toString(), printerSize
					.getSelectionModel().getSelectedItem(), slideTime
					.getSelectionModel().getSelectedItem());
			message.setText(CommonConstants.CONF_SUCCESS_MSG);
			message.setStyle("-fx-text-fill: #019002");
		} else {
			message.setText(CommonConstants.CONG_FAILURE_MSG);
			message.setStyle("-fx-text-fill: RED");
		}
	}

}