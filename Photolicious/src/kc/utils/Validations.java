package kc.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;

public class Validations {

	public static Boolean directoryExists(String path)
	{
		//Boolean boolean1 = false;
		try{
			File file = new File(path);
			if (file.isDirectory())
			{
			    return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static Boolean fileExists(String path)
	{
		//Boolean boolean1 = false;
		try{
			File file = new File(path);
			if (!file.isDirectory())
			   file = file.getParentFile();
			if (file.exists()) {
			    return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static boolean isEmpty(TextField textField){
		Pattern p =Pattern.compile(" ");
		 Matcher m = p.matcher(textField.getText());
		 String temp=textField.getText();
		 temp=m.replaceAll("");
		
			if ((temp==null)||("".equals(temp))){
				textField.requestFocus();
				return true;
			}
			
			else{
				
			
			return false;
			}
		}
	
	public static boolean isEmpty(Object...args){
		for(int i=0;i<args.length;i++)
		{
			TextField textField = (TextField) args[i];
			Pattern p =Pattern.compile(" ");
			Matcher m = p.matcher(textField.getText());
			String temp=textField.getText();
			temp=m.replaceAll("");
			if ((temp==null)||("".equals(temp))){
				textField.requestFocus();
				return true;
			}	
		}
		return false;
		}
}
