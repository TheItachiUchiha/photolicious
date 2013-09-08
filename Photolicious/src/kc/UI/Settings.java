package kc.UI;

import java.io.File;

import javax.xml.bind.ValidationEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kc.utils.PhotoliciousUtils;
import kc.utils.Validations;
import service.Convert;
import service.ImageOverlay;
import service.ResizePic;

public class Settings 
{
	ResizePic resizePic = new ResizePic();
	ImageOverlay imageOverlay = new ImageOverlay();
	//Thread t1;
	//Convert convert123;
	Home home = new Home();
	public BorderPane showSettings(final Stage stage, final TabPane tabPane)
	{
		BorderPane borderPane = null;
		try{
			borderPane = new BorderPane();
			GridPane gridPane = new GridPane();
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setVgap(10);
			Label browseImagefolder = new Label("Browse Image Folder   ");
			Label browseWatermark = new Label("Select Watermark   ");
			Label browseOutputfolder = new Label("Select Output Folder   ");
			final TextField fieldImageFolder = new TextField();
			final TextField fieldWatermark = new TextField();
			final TextField fieldOutputfolder = new TextField();
			
			
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
	            	  
	            	  /*if(!(Validations.directoryExists(fieldImageFolder.getText())))
	            	  {
	            		  Dialogs.showErrorDialog(stage, "Input folder path is Incorrect !");
	            	  }
	            	  else if(!(Validations.directoryExists(fieldOutputfolder.getText())))
	            	  {
	            		  Dialogs.showErrorDialog(stage, "Output folder path is Incorrect !");
	            	  }
	            	  else if(!(Validations.fileExists(fieldWatermark.getText())))
	            	  {
	            		  Dialogs.showErrorDialog(stage, "Watermark path is Incorrect !");
	            	  }
	            	  else{*/
	            	  
		            	  /*DialogResponse response = Dialogs.showConfirmDialog(stage,
		            			    "Input Folder : " + fieldImageFolder.getText() + "\n" +
		            			    "Output Folder : " + fieldOutputfolder.getText() + "\n",
		            			    "Confirm Dialog", "title");*/
	
		            	  /*if(response.equals(DialogResponse.YES))
		            	  {*/
		            		  PhotoliciousUtils.saveOutputFolder(fieldOutputfolder.getText());
		            		  final Convert convert123 = new Convert(fieldImageFolder.getText(), fieldWatermark.getText(), fieldOutputfolder.getText());
			            	  new Thread(convert123).start();
			            	  final Tab tabA = new Tab();
			                  tabA.setId("tabMac");
			                  tabA.setClosable(false);
			          		  tabA.setContent(home.showHome(stage));
			                  tabA.setText("Home");
			                  tabPane.getTabs().remove(0);
			                  tabPane.getTabs().add(tabA);
		            	  //}
		            	  
	            	  //}
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
	
	public void startThread(Convert convert)
	{
		new Thread(convert).start();
	}
	
}
