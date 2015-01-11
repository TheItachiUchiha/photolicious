package com.ita.service;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TifToPng 
{
	public static void main(String args[])
	{
		try{
		    final BufferedImage tif = ImageIO.read(new File("C:\\Users\\HOME\\Desktop\\david\\david\\david\\template3.tif"));
		    
		    BufferedImage bi = tif;
			File outputfile = new File("C:\\Users\\HOME\\Desktop\\david\\david\\david\\xyzabc.png");
			ImageIO.write(bi, "PNG", outputfile);
			
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
