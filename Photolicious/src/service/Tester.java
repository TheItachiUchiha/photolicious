package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.stage.Stage;

public class Tester extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		try{
			ImgPrinter printImage8 = new ImgPrinter("C:\\Users\\Abhinay\\Desktop\\output\\35172.jpg", "4r", "HP ePrint");
			ExecutorService thread = Executors.newCachedThreadPool();
			thread.submit(printImage8);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

}
