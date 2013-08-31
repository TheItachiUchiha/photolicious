package kc.UI;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kc.utils.CommonConstants;
import kc.utils.PhotoliciousUtils;

public class MainWindow extends Application 
{
	Settings settings = new Settings();
	Home home = new Home();
	PhotoliciousUtils photoliciousUtils = new PhotoliciousUtils();
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
    	}  
    }
	@Override
    public void start(final Stage stage)
    {
    	
    	try{

	    	// Use a border pane as the root for scene
	        BorderPane border = new BorderPane();
	        border.setCenter(upperPart(stage));
	        
	        Scene scene = new Scene(border);
	        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("kc/css/home.css").toString());
	        stage.setX(0);
		    stage.setY(0);
		    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		    stage.setScene(scene);
	        stage.setTitle("Photolicious");
	       
	        
				
	        stage.show();
        }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
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
        tabB.setContent(settings.showSettings(stage,tabPane));
        tabPane.getTabs().add(tabB);
        
        
        return tabPane;
    }
        
        
}