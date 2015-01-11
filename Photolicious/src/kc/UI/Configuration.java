package kc.UI;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import com.sun.javafx.print.Units;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import kc.utils.CommonConstants;
import kc.utils.PhotoliciousUtils;
import service.PrintImage8;

public class Configuration implements Initializable {

	@FXML
	private Label message;

	@FXML
	private ComboBox<Printer> printer;

	@FXML
	private ComboBox<Paper> printerSize;

	@FXML
	private ComboBox<String> slideTime;

	String currentSlideTime;
	String currentSize;
	String currentPrinter;
	PrintImage8 printImage;

	public Configuration() {
		printImage = new PrintImage8();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableSet<Printer> printerList = printImage.printerList();
		printer.setItems(FXCollections.observableArrayList(printerList));
		if (PhotoliciousUtils.configurationExists()) {
			currentSlideTime = PhotoliciousUtils.readSlideTime();
			currentSize = PhotoliciousUtils.readPrintSize();
			currentPrinter = PhotoliciousUtils.readDefaultPrinter();
		} else {

			PhotoliciousUtils.saveConfiguration("", "", "");

			if (!(null == currentSlideTime || "".equals(currentSlideTime))) {
				slideTime.getSelectionModel().select(currentSlideTime);
			}

			Iterator<Printer> printItr = printerList.iterator();
			printer.setItems(FXCollections.observableArrayList(printerList));
			while (printItr.hasNext()) {
				if (!(null == currentPrinter || "".equals(currentPrinter))) {
					Printer tempPrinter = printItr.next();
					if (tempPrinter.getName().equals(currentPrinter)) {
						printer.getSelectionModel().select(tempPrinter);
					}
				} else {
					break;
				}
			}

		}

		printer.setCellFactory(new Callback<ListView<Printer>, ListCell<Printer>>() {

			@Override
			public ListCell<Printer> call(ListView<Printer> listViewPrinter) {
				return new ListCell<Printer>() {
					@Override
					protected void updateItem(Printer item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.getName());
						}
					}
				};
			}
		});

		printerSize
				.setCellFactory(new Callback<ListView<Paper>, ListCell<Paper>>() {

					@Override
					public ListCell<Paper> call(ListView<Paper> arg0) {
						return new ListCell<Paper>() {
							@Override
							protected void updateItem(Paper item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setGraphic(null);
								} else {
									setText(item.getName());
								}
							}
						};
					}
				});

		printer.valueProperty().addListener(new ChangeListener<Printer>() {

			@Override
			public void changed(ObservableValue<? extends Printer> value,
					Printer oldChoice, Printer newChoice) {
				try {
					if (null != newChoice) {
						Set<Paper> paperList = newChoice.getPrinterAttributes()
								.getSupportedPapers();
						/**
						 * Adding two custom paper to the list
						 */
						
						System.out.println(paperList);

						ObservableList<Paper> paperListNames = FXCollections
								.observableArrayList();
						Paper tempPaper = null;
						Constructor<Paper> c1 = Paper.class
								.getDeclaredConstructor(String.class,
										double.class, double.class, Units.class);
						c1.setAccessible(true);
						tempPaper = c1.newInstance("4r Potrait", 4, 6,
								Units.INCH);
						paperListNames.add(tempPaper);
						Constructor<Paper> c2 = Paper.class
								.getDeclaredConstructor(String.class,
										double.class, double.class, Units.class);
						c2.setAccessible(true);
						tempPaper = c2.newInstance("4r Landspace", 6, 4,
								Units.INCH);
						paperListNames.add(tempPaper);
						Iterator<Paper> itr = paperList.iterator();
						while (itr.hasNext()) {
							paperListNames.add(itr.next());
						}
						
						printerSize.setItems(FXCollections
								.observableArrayList(paperListNames));
						if (!(null == currentSize || "".equals(currentSize))) {
							for (Paper paper : printerSize.getItems()) {
								if (paper.getName().equals(currentSize))
									printerSize.getSelectionModel().select(
											paper);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void saveConfiguration() {
		if (null != printer.getSelectionModel().getSelectedItem()
				&& null != printerSize.getSelectionModel().getSelectedItem()
				&& null != slideTime.getSelectionModel().getSelectedItem()) {
			PhotoliciousUtils.saveConfiguration(printer.getSelectionModel()
					.getSelectedItem().getName(), printerSize
					.getSelectionModel().getSelectedItem().getName(), slideTime
					.getSelectionModel().getSelectedItem());
			message.setText(CommonConstants.CONF_SUCCESS_MSG);
			message.setStyle("-fx-text-fill: #019002");
		} else {
			message.setText(CommonConstants.CONG_FAILURE_MSG);
			message.setStyle("-fx-text-fill: RED");
		}
	}
}
