package kc.utils;

import java.io.File;

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
}
