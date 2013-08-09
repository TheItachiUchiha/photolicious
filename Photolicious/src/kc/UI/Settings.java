package kc.UI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import service.ImageOverlay;
import service.ResizePic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Settings 
{
	ResizePic resizePic = new ResizePic();
	ImageOverlay imageOverlay = new ImageOverlay();
	public BorderPane showSettings(final Stage stage)
	{
		BorderPane borderPane = null;
		try{
			borderPane = new BorderPane();
			GridPane gridPane = new GridPane();
			gridPane.setVgap(10);
			Label browseImagefolder = new Label("Browse Image Folder");
			Label browseWatermark = new Label("Select Watermark");
			Label browseOutputfolder = new Label("Select Output Folder");
			final TextField fieldImageFolder = new TextField();
			final TextField fieldWatermark = new TextField();
			final TextField fieldOutputfolder = new TextField();
			
			Button imageFolder = new Button();

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
	            	  File inputFolder = new File(fieldImageFolder.getText());
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
	                  System.out.println("End Time"+ new Date());
	              }
	         });
			
			
			Button reset = new Button("Reset");
			reset.setOnAction(new EventHandler<ActionEvent>() {

	              @Override
	              public void handle(ActionEvent event) {
	                 fieldImageFolder.setText("");
	                 fieldWatermark.setText("");
	                 fieldOutputfolder.setText("");
	              }
	         });
	              
			
			gridPane.add(browseImagefolder,0,1);
			gridPane.add(fieldImageFolder,1,1);
			gridPane.add(imageFolder,2,1);
			gridPane.add(browseWatermark,0,2);
			gridPane.add(fieldWatermark,1,2);
			gridPane.add(watermarkImage,2,2);
			gridPane.add(browseOutputfolder,0,3);
			gridPane.add(fieldOutputfolder,1,3);
			gridPane.add(outputFolder,2,3);
			gridPane.add(convert,1,4);
			gridPane.add(reset,2,4);
			borderPane.setCenter(gridPane);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return borderPane;
	}
}
