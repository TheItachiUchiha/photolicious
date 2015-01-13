package com.ita.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.ita.utils.PhotoliciousUtils;

public class Window {
	private final Settings settings = new Settings();
	PhotoliciousUtils photoliciousUtils = new PhotoliciousUtils();
	private Stage stage;
	ExecutorService exec;

	public Window() {
		exec = Executors.newCachedThreadPool((r) -> {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		});
	}

	public void showMainStage() {
		try {
			stage = new Stage(StageStyle.DECORATED);
			stage.setX(0);
			stage.setY(0);
			stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
			stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
			stage.getIcons().add(
					new Image(getClass().getResource(MainApp.APPLICATION_ICON)
							.toExternalForm()));
			stage.setTitle("The Pica Studio");

			// Use a border pane as the root for scene
			BorderPane border = new BorderPane();
			border.setCenter(upperPart(stage));
			Scene scene = new Scene(border);
			scene.getStylesheets().add(
					getClass().getResource("/css/home.css").toExternalForm());
			stage.setScene(scene);
			stage.setOnCloseRequest((e) -> exec.shutdown());
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TabPane upperPart(final Stage stage) {
		TabPane tabPane = new TabPane();
		tabPane.setId(("MyTabPane"));
		final Tab tabB = new Tab();
		tabB.setId("tabMac");
		tabB.setClosable(false);
		tabB.setText("Settings");
		tabB.setContent(settings.showSettings(stage, tabPane, exec));
		tabPane.getTabs().add(tabB);

		return tabPane;
	}

}