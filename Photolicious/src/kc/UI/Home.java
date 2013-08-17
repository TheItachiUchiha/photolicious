package kc.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kc.utils.CommonConstants;

public class Home 
{
	BorderPane borderPane = null;
	File outputFolder;
	List<String> list = new ArrayList<String>();

	public Home()
	{
		borderPane = new BorderPane();
		this.outputFolder = new File("C:\\Users\\Public\\Pictures\\Sample Pictures");
	}
	public Home(File outPutFolder)
	{
		borderPane = new BorderPane();
		this.outputFolder = outPutFolder;
		
	}
	public BorderPane showHome(Stage stage)
	{
		borderPane.setLeft(leftPane());
		borderPane.setCenter(viewGallery(stage));
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
	
	public ScrollPane viewGallery(final Stage stage)
	{
		ScrollPane root = null;
		final TilePane tile = new TilePane();
	
		
		try{
			root = new ScrollPane();
			tile.setPadding(new Insets(15, 15, 15, 15));
			tile.setTileAlignment(Pos.CENTER);
			tile.setAlignment(Pos.TOP_LEFT);

			tile.setVgap(4);
		    tile.setHgap(4);
		    tile.setStyle("-fx-background-color: DAE6F3;");
		    
		    new Thread(new Runnable() {

                @Override
                public void run() {
                	while(!Thread.interrupted())
                	{
                	final File[] listOfFiles = outputFolder.listFiles();
        			System.out.println(outputFolder.listFiles().length);
        			
        			
        			
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                        	if(list.size()!=listOfFiles.length)
                        	{
                        		for (File file : listOfFiles) {
                        			if(!list.contains(file.getName())){
		                				System.out.println(file.getPath());
		                				final Image image = new Image("file:"+file.getPath());
		                				VBox vBox = new VBox();
		                				vBox.setAlignment(Pos.BOTTOM_CENTER);
		                				vBox.setId("Images");
		                				
		                				
		                				ImageView iv2 = new ImageView();
		                				iv2.setImage(image);
		                				iv2.setFitWidth(250);
		                				iv2.setPreserveRatio(true);
		                				iv2.setSmooth(true);
		                				iv2.setCache(true);
		                				vBox.getChildren().addAll(iv2, new Label(file.getName()));
		                				tile.getChildren().add(vBox);
		                				list.add(file.getName());
		                				
		                				
		                				iv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

											@Override
											public void handle(MouseEvent mouseEvent) {
												
												if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
										            
													if(mouseEvent.getClickCount() == 1 ){
														
													
													}
													
													else if(mouseEvent.getClickCount() == 2){

										            	BorderPane borderPane = new BorderPane();
										            	ImageView imageView = new ImageView();
										            	imageView.setImage(image);
										            	borderPane.setCenter(imageView);
										            	Stage newStage = new Stage();
										            	newStage.setWidth(stage.getWidth());
										            	newStage.setHeight(stage.getHeight());
										            	newStage.setTitle("My New Stage Title");
										            	newStage.setScene(new Scene(borderPane));
										                newStage.show();
										                
										            }
										            
										           
												}
											}
										});
		                				
		                				
		                				
		                				
                        			}
                        		}
                        	}
                        }
                    });
                    try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                	
                }
            }).start();
		    
		   
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        
		root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
		//	root.setFitToHeight(true);
		root.setFitToWidth(true);
        root.setContent(tile);	
		
        return root;
	}
}
