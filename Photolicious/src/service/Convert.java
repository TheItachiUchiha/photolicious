package service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kc.utils.PhotoliciousUtils;

public class Convert implements Runnable {

	String inputFolderPath;
	String waterMarkImagePath;
	String outputFolderPath;
	ImageOverlay imageOverlay;
	ResizePic resizePic;
	List<String> currentFolderList;
	List<String> convertedFilesList;
	
	public Convert(String inputFolderPath, String waterMarkImagePath, String outputFolderPath)
	{
		this.inputFolderPath = inputFolderPath;
		this.waterMarkImagePath = waterMarkImagePath;
		this.outputFolderPath = outputFolderPath;
		imageOverlay = new ImageOverlay();
		resizePic = new ResizePic();
		currentFolderList = new ArrayList<String>();
		convertedFilesList = new ArrayList<String>();
	}
	@Override
	public void run() {
		try{
		while(!Thread.interrupted())
		{
			File inputFolder = new File(this.inputFolderPath);
      	  	File watermark = new File(this.waterMarkImagePath);
      	  	BufferedImage watermarkBuffer = imageOverlay.readImage(watermark.getPath());
            File[] listOfFiles = PhotoliciousUtils.filterImagesFromFolder(inputFolder.listFiles());
            if(listOfFiles == null) return;  // Added condition check
            System.out.println("Start Time"+ new Date());
         
            if(listOfFiles.length != convertedFilesList.size())
        	{
            	for (File file : listOfFiles) {
            		if(!convertedFilesList.contains(file.getName()))
            		{
		          	  System.out.println(file.getPath());
		          	  BufferedImage image = imageOverlay.readImage(file.getPath());
		          	  BufferedImage resizedImage = resizePic.scaleImage(image, watermarkBuffer.getWidth(), watermarkBuffer.getHeight());
		          	  BufferedImage finalImage = imageOverlay.overlayImages(resizedImage, watermarkBuffer);
		          	  imageOverlay.writeImage(finalImage, outputFolderPath+"//"+file.getName(), "jpeg");
		          	  convertedFilesList.add(file.getName());
            		}
            	}
            	System.out.println("End Time"+ new Date());
            }
            Thread.sleep(5000);
			}
		}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

		}
		
	}

}
