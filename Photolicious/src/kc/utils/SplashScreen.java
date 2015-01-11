package kc.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("rawtypes")
public class SplashScreen {

	Task copyWorker;
	Stage initStage;
	Stage mainStage;
	private Pane splashLayout;
	private ProgressBar loadProgress;
	private static final int SPLASH_WIDTH = 640;
	private static final int SPLASH_HEIGHT = 400;

	public SplashScreen(Stage mainStage) {
		this.mainStage = mainStage;
		ImageView splash = new ImageView(new Image(this.getClass()
				.getResourceAsStream("/kc/css/splash.jpg")));
		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(SPLASH_WIDTH);
		splashLayout = new VBox();
		splashLayout.getChildren().addAll(splash, loadProgress);
		splashLayout.setEffect(new DropShadow());
	}

	// A simple little method to show a title screen in the center
	// of the screen for the amount of time given in the constructor
	public void showSplash() {
		try {
			Scene splashScene = new Scene(splashLayout);
			splashScene.getStylesheets().add(this.getClass().getClassLoader().getResource("kc/css/splash.css").toExternalForm());
		    initStage = new Stage();
			initStage.setScene(splashScene);
			initStage.initStyle(StageStyle.UNDECORATED);
			final Rectangle2D bounds = Screen.getPrimary().getBounds();
			initStage.setScene(splashScene);
			initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH
					/ 2);
			initStage.setY(bounds.getMinY() + bounds.getHeight() / 2
					- SPLASH_HEIGHT / 2);
			initStage.show();
			mainStage.toBack();
			startProgressbar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void startProgressbar(){
		copyWorker = createWorker();
		loadProgress.setProgress(1);
		loadProgress.progressProperty().bind(copyWorker.progressProperty());
		new Thread(copyWorker).start();
		/*
		 * To remove the splash 
		 * when the thread Succeeds and start the Main Window
		 * 
		 */
		copyWorker.stateProperty().addListener(new ChangeListener<Task.State>() {
	        @SuppressWarnings("incomplete-switch")
			@Override
	        public void changed(ObservableValue<? extends Task.State> observableValue, Task.State oldState, Task.State state) {
	        	switch (state) {
		            case SUCCEEDED:
		            	exitSplash();
	        	}
	        }
		});
	}

	public void exitSplash() {
		loadProgress.progressProperty().unbind();
		initStage.hide();
		mainStage.show();
	}

	
	public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
            	for (int i = 0; i < 10; i++) {
                    Thread.sleep(350);
                    updateProgress(i + 1, 10);
                }
                return true;
            }
        };
    }
}
