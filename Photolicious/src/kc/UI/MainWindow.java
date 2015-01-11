package kc.UI;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kc.utils.CommonConstants;
import kc.utils.PhotoliciousUtils;
import kc.utils.SplashScreen;

public class MainWindow extends Application 
{
	Settings settings = new Settings();
	Home home = new Home();
	PhotoliciousUtils photoliciousUtils = new PhotoliciousUtils();
	ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    });
	
	public static void main(String[] args)
    {
    	String appId = "Photolicious-App";
    	PhotoliciousUtils.saveOutputFolder(CommonConstants.defalutOutPutFolder);
    	boolean running;
    	try {
			JUnique.acquireLock(appId);
			running=true;
		} 
    	catch (AlreadyLockedException e) 
		{
			running=false;
		}
    	if(running)
    	{
    		launch(MainWindow.class, args);
    	} else {
    		
    	}
    }
	@Override
    public void start(final Stage stage)
    {
		SplashScreen splash = new SplashScreen(stage);
		splash.showSplash();
    	try{
	    	// Use a border pane as the root for scene
	        BorderPane border = new BorderPane();
	        border.setCenter(upperPart(stage));
	        
	        Scene scene = new Scene(border);
	        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("kc/css/home.css").toExternalForm());
	        stage.setX(0);
		    stage.setY(0);
		    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		    stage.setScene(scene);
	        stage.setTitle("The Pica Live Studio");
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent arg0) {
					exec.shutdown();
				}
			});
	        
        }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	
	@Override
	public void stop() throws Exception {
		exec.shutdownNow();
	}
	private TabPane upperPart(final Stage stage) 
	{
    	TabPane tabPane = new TabPane();
    	tabPane.setId(("MyTabPane"));
    	
    	
    	
    	
    	
    	
    	/*tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> tab, Tab oldTab,
					Tab newTab) {
				String outputFolder = photoliciousUtils.readOutputFolder();
				if(outputFolder.equals(CommonConstants.defalutOutPutFolder))
				{
					tabA.setContent(home.showEmptyHome(stage));
				}
				else
				{
					tabA.setContent(home.showHome(stage));
				}
			}
    		
		});
*/    	
    	
    	
        
        
       
        final Tab tabB = new Tab();
        tabB.setId("tabMac");
        tabB.setClosable(false);
        tabB.setText("Settings");
        tabB.setContent(settings.showSettings(stage,tabPane, exec));
        tabPane.getTabs().add(tabB);
        
        
        return tabPane;
    }
        
}