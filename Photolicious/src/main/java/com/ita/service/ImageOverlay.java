package com.ita.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JOptionPane;

import org.w3c.dom.Element;

public class ImageOverlay {


	/**
	 * Method to overlay Images
	 *
	 * @param bgImage --> The background Image
	 * @param fgImage --> The foreground Image
	 * @return --> overlayed image (fgImage over bgImage)
	 */
	public static BufferedImage overlayImages(BufferedImage bgImage,
			BufferedImage fgImage) {
		try{

		/**
		 * Doing some preliminary validations.
		 * Foreground image height cannot be greater than background image height.
		 * Foreground image width cannot be greater than background image width.
		 *
		 * returning a null value if such condition exists.
		 */
		if (fgImage.getHeight() > bgImage.getHeight()
				|| fgImage.getWidth() > bgImage.getWidth()) {
			//BufferedImage newfgImage;
			ResizePic resizePic = new ResizePic();
			bgImage = resizePic.scaleImage(bgImage, fgImage.getWidth(), fgImage.getHeight());
			if (fgImage.getWidth() > bgImage.getWidth())
			{
			JOptionPane.showMessageDialog(null,
					"Foreground Image Is Bigger In One or Both Dimensions"
							+ "\nCannot proceed with overlay."
							+ "\n\n Please use smaller Image for foreground");
			return null;
			}
		}

		/**Create a Graphics  from the background image**/
		Graphics2D g = bgImage.createGraphics();
		/**Set Antialias Rendering**/
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		/**
		 * Draw background image at location (0,0)
		 * You can change the (x,y) value as required
		 */
		g.drawImage(bgImage, 0, 0, null);

		/**
		 * Draw foreground image at location (0,0)
		 * Change (x,y) value as required.
		 */
		g.drawImage(fgImage, 0, 0, null);

		g.dispose();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return bgImage;
	}

	/**
	 * This method reads an image from the file
	 * @param fileLocation -- > eg. "C:/testImage.jpg"
	 * @return BufferedImage of the file read
	 */
	public static BufferedImage readImage(String fileLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * This method writes a buffered image to a file
	 * @param img -- > BufferedImage
	 * @param fileLocation --> e.g. "C:/testImage.jpg"
	 * @param extension --> e.g. "jpg","gif","png"
	 */
	public static void writeImage(BufferedImage img, String fileLocation,
			String extension) {
		try {
			Iterator iter = ImageIO.getImageWritersByFormatName(extension);
			ImageWriter writer = (ImageWriter)iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwp.setCompressionQuality(0.9f);   // an integer between 0 and 1
			// 1 specifies minimum compression and maximum quality
			ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
		    IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, iwp);
		    setDPI(metadata);
			
			
			File file = new File(fileLocation);
			final ImageOutputStream output = ImageIO.createImageOutputStream(file);
			//FileImageOutputStream output = new FileImageOutputStream(file);
			writer.setOutput(output);
			//IIOImage image = new IIOImage(img, null, null);
			//writer.write(null, image, iwp);
			writer.write(null, new IIOImage(img, null, metadata), iwp);
			writer.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {
 		Element tree = (Element)metadata.getAsTree("javax_imageio_jpeg_image_1.0");
 		Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
 		jfif.setAttribute("Xdensity", Integer.toString(300));
 		jfif.setAttribute("Ydensity", Integer.toString(300));
 		jfif.setAttribute("resUnits", "1"); // density is dots per inch	
 		metadata.setFromTree("javax_imageio_jpeg_image_1.0", tree);
	}
}
