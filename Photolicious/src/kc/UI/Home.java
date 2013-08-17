package kc.UI;

import java.io.File;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import kc.utils.CommonConstants;

public class Home 
{
	Label currentPrints;
	Label newFile;
	Label timeStamp;
	BorderPane borderPane = null;
	File outputFolder;
	List<String> list = new ArrayList<String>();
	VBox imageViewBox = new VBox();
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

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
		
		try{
			finalBox = new VBox();
			finalBox.setMaxWidth(300);
			finalBox.setId("finalBox");
			
			detailsBox = new VBox();
			detailsBox.setAlignment(Pos.TOP_LEFT);
			detailsBox.setPadding(new Insets(20, 30, 20, 30));
			detailsBox.setSpacing(15);
			
			
			
			
			printOptionsBox = new VBox();
			printOptionsBox.setId("printOptionsBox");
			printOptionsBox.getChildren().add(new Button("ABC"));
			
			
			
			
			imageViewBox.setMaxWidth(finalBox.getMaxWidth()-25);
			imageViewBox.setMaxHeight(finalBox.getHeight() - (detailsBox.getHeight() + printOptionsBox.getHeight() + 10));
			
	
			
			
			int noOfImages = outputFolder.listFiles().length;
			
			Label noOfPhotos = new Label(CommonConstants.noOfPics);
			noOfPhotos.setId("label1");
			
			Label number = new Label(String.valueOf(noOfImages));
			
			Label noOfPrints = new Label(CommonConstants.noOfPrints);
			noOfPrints.setId("label1");
			/*
			 * ..Include the prints..
			 */
			Label currentPrints = new Label(String.valueOf(0));
			
			Label newestFileLabel = new Label(CommonConstants.noOfPrints);
			newestFileLabel.setId("label1");
			newFile = new Label();
			
			Label timeStampLabel = new Label(CommonConstants.noOfPrints);
			timeStampLabel.setId("label1");
			timeStamp = new Label();
			
			detailsBox.getChildren().addAll(noOfPhotos, number, noOfPrints, currentPrints, newestFileLabel, newFile, timeStampLabel, timeStamp);
			
			
			
			
			
			
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
                        				newFile.setText(file.getName());
                        				timeStamp.setText(sdf.format(file.lastModified()));
		                				System.out.println(file.getPath());
		                				final Image image = new Image("file:"+file.getPath());
		                				VBox vBox = new VBox();
		                				vBox.setAlignment(Pos.BOTTOM_CENTER);
		                				vBox.setId("Images");
		                				
		                				
		                				ImageView iv2 = new ImageView();
		                				iv2.setImage(image);
		                				iv2.setFitWidth(150);
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
														
														ImageView imageView = new ImageView();
										            	imageView.setImage(image);
														/*imageView.setFitHeight(imageViewBox.getMaxHeight() - 10);*/
														imageView.setFitWidth(imageViewBox.getMaxWidth() - 10);
														imageView.setPreserveRatio(true);
														imageView.setSmooth(true);
														imageView.setCache(true);
														imageViewBox.getChildren().clear();
														imageViewBox.getChildren().add(imageView);
													
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
