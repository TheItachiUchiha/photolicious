package com.ita.ui;

import java.io.File;
import java.util.concurrent.ExecutorService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import com.ita.service.Convert;
import com.ita.service.ImageOverlay;
import com.ita.service.ResizePic;
import com.ita.utils.CommonConstants;
import com.ita.utils.PhotoliciousUtils;
import com.ita.utils.Validations;

public class Settings 
{
	ResizePic resizePic = new ResizePic();
	ImageOverlay imageOverlay = new ImageOverlay();
	//Thread t1;
	//Convert convert123;
	Home home = new Home();
	public BorderPane showSettings(final Stage stage, final TabPane tabPane, final ExecutorService exec)
	{
		BorderPane borderPane = null;
		try{
			borderPane = new BorderPane();
			GridPane gridPane = new GridPane();
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setVgap(10);
			gridPane.setHgap(10);
			Label browseImagefolder = new Label("Browse Image Folder   ");
			Label browseWatermark = new Label("Select Watermark   ");
			Label browseOutputfolder = new Label("Select Output Folder   ");
			final TextField fieldImageFolder = new TextField();
			final TextField fieldWatermark = new TextField();
			final TextField fieldOutputfolder = new TextField();
			fieldImageFolder.setEditable(false);
			fieldWatermark.setEditable(false);
			fieldOutputfolder.setEditable(false);
			
			Button imageFolder = new Button();
			imageFolder.setId("#buttonStMac");

			imageFolder.setText("Browse Directory");

			imageFolder.setOnAction(new EventHandler<ActionEvent>() {

	              @Override
	              public void handle(ActionEvent event) {
	                 DirectoryChooser directoryChooser = new DirectoryChooser();
	                 File tempFile = directoryChooser.showDialog(null);
	                 if(tempFile!=null){
	                	 fieldImageFolder.setText(tempFile.getPath());
	                 }
	                 
	              }
	         });
			
			
			Button watermarkImage = new Button();
			watermarkImage.setId("#buttonStMac");
			watermarkImage.setText("Browse File");

			watermarkImage.setOnAction(new EventHandler<ActionEvent>() {

	              @Override
	              public void handle(ActionEvent event) {
	                 FileChooser fileChooser = new FileChooser();
	                 File tempFile = fileChooser.showOpenDialog(stage);
	                 if(tempFile!=null){
	                	 fieldWatermark.setText(tempFile.getPath());
	                 }
	                 
	              }
	         });
			
			
			
			Button outputFolder = new Button();
			outputFolder.setId("#buttonStMac");

			outputFolder.setText("Browse Directory");

			outputFolder.setOnAction(new EventHandler<ActionEvent>() {

	              @Override
	              public void handle(ActionEvent event) {
	                 DirectoryChooser directoryChooser = new DirectoryChooser();
	                 File tempFile = directoryChooser.showDialog(null);
	                 if(tempFile!=null){
	                	 fieldOutputfolder.setText(tempFile.getPath());
	                 }
	                 
	              }
	         });
	                 
			Button convert = new Button("Convert");
			convert.setOnAction(new EventHandler<ActionEvent>() {

	              @Override
	              public void handle(ActionEvent event) {
	            	  System.out.println("Convert Hit");
	            	 /* File inputFolder = new File(fieldImageFolder.getText());
	            	  File watermark = new File(fieldWatermark.getText());
	            	  BufferedImage watermarkBuffer = imageOverlay.readImage(watermark.getPath());
	                  File[] listOfFiles = inputFolder.listFiles();
	                  if(listOfFiles == null) return;  // Added condition check
	                  System.out.println("Start Time"+ new Date());
	                  for (File file : listOfFiles) {
	                	  
	                	  System.out.println(file.getPath());
	                	  BufferedImage image = imageOverlay.readImage(file.getPath());
	                	  BufferedImage resizedImage = resizePic.scaleImage(image, watermarkBuffer.getWidth(), watermarkBuffer.getHeight());
	                	  BufferedImage finalImage = imageOverlay.overlayImages(resizedImage, watermarkBuffer);
	                	  imageOverlay.writeImage(finalImage, fieldOutputfolder.getText()+"\\"+file.getName(), "jpeg");
	                  }
	                  System.out.println("End Time"+ new Date());*/
	            	  
	            	  if(Validations.isEmpty(fieldImageFolder,fieldOutputfolder,fieldWatermark))
	            	  {
	            		  Dialogs.create().title("Error").message(CommonConstants.BROWSE_PATH).showError();
	            		  //WarningDialog.showWarning(stage);
	            	  }
	            	  else{
	            	  
			            	  try{
			            		  FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/configuration.fxml"));
			            		  BorderPane config = (BorderPane) loader.load();
			            		  config.setLeft(null);
			            		  config.setTop(null);
	            	  			
			            		  PhotoliciousUtils.saveOutputFolder(fieldOutputfolder.getText());
			            		  final Convert convert123 = new Convert(fieldImageFolder.getText(), fieldWatermark.getText(), fieldOutputfolder.getText());
				            	  exec.execute(convert123);
				            	  final Tab tabA = new Tab();
				            	  final Tab tabB = new Tab();
				                  tabA.setId("tabMac");
				                  tabB.setId("tabMac");
				                  tabA.setClosable(false);
				                  tabB.setClosable(false);
				          		  tabA.setContent(home.showHome(stage, exec));
				                  tabA.setText("Home");
				                  tabB.setContent(config);
				                  tabB.setText("Configuration");
				                  tabPane.getTabs().remove(0);
				                  tabPane.getTabs().add(tabA);
				                  tabPane.getTabs().add(tabB);
			                  
			            	  }
				          		catch (Exception e) {
				          			e.printStackTrace();
				          }
	            	 }
	              }
	         });
			
			
			Button reset = new Button("Reset");
			reset.setOnAction(new EventHandler<ActionEvent>() {

	              @Override
	              public void handle(ActionEvent event) {
	            	 //stopThread(); 
	                 fieldImageFolder.setText("");
	                 fieldWatermark.setText("");
	                 fieldOutputfolder.setText("");
	              }
	         });
	              
			
			gridPane.add(browseImagefolder,0,1);
			gridPane.add(fieldImageFolder,1,1);
			gridPane.add(imageFolder,2,1);
			gridPane.add(browseOutputfolder,0,2);
			gridPane.add(fieldOutputfolder,1,2);
			gridPane.add(outputFolder,2,2);
			gridPane.add(browseWatermark,0,3);
			gridPane.add(fieldWatermark,1,3);
			gridPane.add(watermarkImage,2,3);
			HBox box = new HBox(20);
			box.getChildren().addAll(convert,reset);
			box.setAlignment(Pos.CENTER);
			GridPane.setColumnSpan(box, 3);
			gridPane.add(box,0,4);
			/*gridPane.add(convert,1,4);
			gridPane.add(reset,2,4);*/
			borderPane.setCenter(gridPane);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return borderPane;
	}	
}
