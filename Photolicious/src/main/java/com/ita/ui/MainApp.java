package com.ita.ui;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
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
import javafx.util.Duration;

import com.ita.utils.CommonConstants;
import com.ita.utils.PhotoliciousUtils;

/**
 *  The starting point of the JavaFX App
 */
public class MainApp extends Application {
	public static final String APPLICATION_ICON = "/images/icon.png";
	public static final String SPLASH_IMAGE = "/images/splash.jpg";

	private Pane splashLayout;
	private ProgressBar loadProgress;
	private static final int SPLASH_WIDTH = 640;
	private static final int SPLASH_HEIGHT = 400;
	private final Window mainWindow = new Window();
	private static final int PROGRESS_TIME = 7;

	public static void main(String[] args) throws Exception {
		String appId = "Photolicious-App";
		PhotoliciousUtils.saveOutputFolder(CommonConstants.defalutOutPutFolder);
		boolean running;
		try {
			JUnique.acquireLock(appId);
			running = true;
		} catch (AlreadyLockedException e) {
			running = false;
		}
		if (running) {
			launch(MainApp.class, args);
		}
	}

	@Override
	public void init() {
		ImageView splash = new ImageView(new Image(getClass().getResource(
				SPLASH_IMAGE).toExternalForm()));
		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(SPLASH_WIDTH);
		splashLayout = new VBox();
		splashLayout.getChildren().addAll(splash, loadProgress);
		splashLayout.setStyle("-fx-padding: 5; "
				+ "-fx-background-color: black; ");
		splashLayout.setEffect(new DropShadow());
	}

	@Override
	public void start(final Stage initStage) throws Exception {
		final Task<Void> friendTask = new Task<Void>() {
			@Override
			protected Void call() throws InterruptedException {

				for (int i = 0; i < PROGRESS_TIME; i++) {
					Thread.sleep(400);
					updateProgress(i + 1, PROGRESS_TIME);
				}
				Thread.sleep(400);
				return null;
			}
		};

		showSplash(initStage, friendTask,
				() -> mainWindow.showMainStage());
		new Thread(friendTask).start();
	}

	private void showSplash(final Stage initStage, Task<?> task,
			InitCompletionHandler initCompletionHandler) {
		loadProgress.progressProperty().bind(task.progressProperty());
		task.stateProperty().addListener(
				(observableValue, oldState, newState) -> {
					if (newState == Worker.State.SUCCEEDED) {
						loadProgress.progressProperty().unbind();
						loadProgress.setProgress(1);
						initStage.toFront();
						FadeTransition fadeSplash = new FadeTransition(Duration
								.seconds(1.2), splashLayout);
						fadeSplash.setFromValue(1.0);
						fadeSplash.setToValue(0.0);
						fadeSplash.setOnFinished(actionEvent -> initStage
								.hide());
						fadeSplash.play();

						initCompletionHandler.complete();
					}
				});

		Scene splashScene = new Scene(splashLayout);
		initStage.initStyle(StageStyle.UNDECORATED);
		final Rectangle2D bounds = Screen.getPrimary().getBounds();
		initStage.setScene(splashScene);
		initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH
				/ 2);
		initStage.setY(bounds.getMinY() + bounds.getHeight() / 2
				- SPLASH_HEIGHT / 2);
		initStage.show();
	}

	public interface InitCompletionHandler {
		public void complete();
	}
}