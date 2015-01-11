package com.ita.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WarningDialog 
{
	public static void showWarning(final Stage primaryStage)
	{
		final Stage dialog = new Stage(StageStyle.TRANSPARENT);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setScene(new Scene(HBoxBuilder
				.create()
				.styleClass("modal-dialog")
				.children(
						LabelBuilder.create().text("Please Browse all Paths !")
								.build(),
						ButtonBuilder.create().text("OK").defaultButton(true)
								.onAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent actionEvent) {
										
										primaryStage.getScene().getRoot()
												.setEffect(null);
										dialog.close();
									}
								}).build()).build(), Color.TRANSPARENT));
		dialog.getScene()
				.getStylesheets()
				.add(WarningDialog.class.getResource("/kc/css/modal-dialog.css")
						.toExternalForm());
		dialog.show();
	}
}
