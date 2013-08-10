package kc.UI;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import kc.utils.CommonConstants;

public class Home 
{
	BorderPane borderPane = null;
	File outputFolder;

	public Home()
	{
		borderPane = new BorderPane();
		this.outputFolder = new File("C:\\Users\\HOME\\Desktop\\david\\input folder");
	}
	public Home(File outPutFolder)
	{
		borderPane = new BorderPane();
		this.outputFolder = outPutFolder;
		
	}
	public BorderPane showHome()
	{
		borderPane.setLeft(leftPane());
		borderPane.setCenter(viewGallery());
		return borderPane;
	}
	
	public VBox leftPane()
	{
		VBox finalBox = null;
		VBox detailsBox =null;
		VBox printOptionsBox =null;
		VBox imageViewBox =null;
		try{
			finalBox = new VBox();
			finalBox.setMinWidth(100);
			finalBox.setStyle("-fx-border-style: solid;"
					                + "-fx-border-width: 1;"
						                + "-fx-border-color: black;"
						                + "-fx-background-color: #2f4f4f");
			
			detailsBox = new VBox();
			detailsBox.setAlignment(Pos.TOP_LEFT);
			detailsBox.setPadding(new Insets(10, 30, 10, 30));
			
			
			
			
			printOptionsBox = new VBox();
			imageViewBox = new VBox();
			
			
			int noOfImages = outputFolder.listFiles().length;
			Label noOfPhotos = new Label(CommonConstants.noOfPics);
			Label number = new Label(String.valueOf(noOfImages)); 
			
			detailsBox.getChildren().addAll(noOfPhotos, number);
			
			
			
			
			
			
			finalBox.getChildren().addAll(detailsBox, printOptionsBox, imageViewBox);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return finalBox;
	}
	
	public GridPane viewGallery()
	{
		GridPane gridPane = null;
		try{
			gridPane = new GridPane();
		File[] listOfFiles = outputFolder.listFiles();
		System.out.println(outputFolder.listFiles().length);
		VBox vBox = new VBox();
		for (File file : listOfFiles) {
      	  
			System.out.println(file.getPath());
			Image image = new Image("file:"+file.getPath());
			ImageView iv2 = new ImageView();
			iv2.setImage(image);
			iv2.setFitWidth(100);
			iv2.setPreserveRatio(true);
			iv2.setSmooth(true);
			iv2.setCache(true);
			vBox.getChildren().add(iv2);
        }
		
		gridPane.add(vBox,0,0);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        
        return gridPane;
	}
}
