package kc.utils;

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import kc.vo.ScreenVO;

public class SlideShow extends Task {

	
	ImageView[] slides;
	File outputFolder;
	ScreenVO screenVO;
	
	
	public SlideShow(File outputFolder, ScreenVO screenVO)
	{
		this.outputFolder = outputFolder;
		this.screenVO = screenVO;
	}


	// The method I am running in my class
	public void start(File outputFolder, ScreenVO screenVO) {

		try{
			final Stage sliderStage = new Stage();
			StackPane root = new StackPane();
			Scene scene = new Scene(root);
			sliderStage.setScene(scene);
			sliderStage.setFullScreen(true);
			sliderStage.setX(screenVO.getScreen().getBounds().getMinX());  
			sliderStage.setY(screenVO.getScreen().getBounds().getMinY()); 
			sliderStage.show();
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		        public void handle(KeyEvent ke) {
		            if (ke.getCode() == KeyCode.ESCAPE) {
		                sliderStage.close();
		            }
		        }
		    });
		
		
		

			slides = new ImageView[outputFolder.listFiles().length-1];
			File[] listOfFiles = PhotoliciousUtils
					.filterJPEGImagesFromFolder(outputFolder.listFiles());
			slides = new ImageView[listOfFiles.length];
			int length = 0;
			for (File file : listOfFiles) {
				Image image = new Image("file:" + file.getPath());
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(scene.getWidth());
				imageView.setFitHeight(scene.getHeight());
				imageView.setPreserveRatio(true);
				imageView.setSmooth(true);
				imageView.setCache(true);
				slides[length]= imageView;
				length++;
			}
	
			SequentialTransition slideshow = new SequentialTransition();
	
			for (ImageView slide : slides) {
	
				SequentialTransition sequentialTransition = new SequentialTransition();
	
				FadeTransition fadeIn = getFadeTransition(slide, 0.0, 1.0, 2000);
				PauseTransition stayOn = new PauseTransition(Duration.millis(2000));
				FadeTransition fadeOut = getFadeTransition(slide, 1.0, 0.0, 2000);
	
				sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
				slide.setOpacity(0);
				root.getChildren().add(slide);
				slideshow.getChildren().add(sequentialTransition);
	
			}
			slideshow.setCycleCount(Timeline.INDEFINITE);
			slideshow.setAutoReverse(true);
			slideshow.play();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	// the method in the Transition helper class:

	public FadeTransition getFadeTransition(ImageView imageView,
			double fromValue, double toValue, int durationInMilliseconds) {

		FadeTransition ft = new FadeTransition(
				Duration.millis(durationInMilliseconds), imageView);
		ft.setFromValue(fromValue);
		ft.setToValue(toValue);

		return ft;

	}
	

	public static ObservableList<ScreenVO> fetchListOfScreen()
	{
		ObservableList<ScreenVO> listOfScreenVO = FXCollections.observableArrayList();
		ObservableList<Screen> listOfScreen = FXCollections.observableArrayList();
		listOfScreen = Screen.getScreens();
		for(int i=0;i< listOfScreen.size(); i++)
		{
			ScreenVO screenVO = new ScreenVO();
			if(i==0)
			{
				screenVO.setName("Primary Screen" + " : " + listOfScreen.get(i).getBounds().getWidth() + "x" + listOfScreen.get(i).getBounds().getHeight());
			}
			else
			{
				screenVO.setName("Screen"+ i+1 + " : " + listOfScreen.get(i).getBounds().getWidth() + "x" + listOfScreen.get(i).getBounds().getHeight());
			}
			screenVO.setScreen(listOfScreen.get(i));
			listOfScreenVO.add(screenVO);
		}
		return listOfScreenVO;
	}

	@Override
	protected Object call() throws Exception {
		Platform.runLater(new Runnable() {
            @Override public void run() {
            	start(outputFolder, screenVO);
            }
        });
		
		return null;
	}
	
}