package service;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;

import kc.vo.PrintServiceVO;

public class PrintImage implements Printable {

    BufferedImage img;
    BufferedImage scaled;

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {

        int result = NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.translate((int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableY()));
        if (pageIndex == 0) {
            double pageWidth = pageFormat.getImageableWidth();
            double pageHeight = pageFormat.getImageableHeight();
            if (scaled == null) {
                // Swap the width and height to allow for the rotation...
                System.out.println(pageWidth + "x" + pageHeight);
                scaled = getScaledInstanceToFit(
                        img, 
                        new Dimension((int)pageHeight, (int)pageWidth));
                System.out.println("In " + img.getWidth() + "x" + img.getHeight());
                System.out.println("Out " + scaled.getWidth() + "x" + scaled.getHeight());
            }
            double imageWidth = scaled.getWidth();
            double imageHeight = scaled.getHeight();

            AffineTransform at = AffineTransform.getRotateInstance(
                    Math.toRadians(90), 
                    pageWidth / 2d, 
                    pageHeight / 2d
            );

            AffineTransform old = g2d.getTransform();
            g2d.setTransform(at);
            double x = (pageHeight - imageWidth) / 2d;
            double y = (pageWidth - imageHeight) / 2d;
            g2d.drawImage(
                    scaled, 
                    (int)x, 
                    (int)y, 
                    null);

            g2d.setTransform(old);

            // This is not affected by the previous changes, as those were made
            // to a different copy...
            g2d.setColor(Color.RED);
            g2d.drawRect(0, 0, (int)pageWidth - 1, (int)pageHeight - 1);
            result = PAGE_EXISTS;
        }
        g2d.dispose();

        return result;
    }

    public void printPage(String file, String size, PrintService printer) {
        try {
            img = ImageIO.read(new File(file));
            PrintRequestAttributeSet aset = createAsetForMedia(size);
            DocPrintJob pj = printer.createPrintJob();
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            //PageFormat pageFormat = pj.getPageFormat(aset);
            //pj.setPrintable(this, pageFormat);
            Doc doc = new SimpleDoc(this, flavor, null);
            pj.print(doc, aset);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException | Error e) {
			e.printStackTrace();
		}
    }

    private PrintRequestAttributeSet createAsetForMedia(String size) {
        PrintRequestAttributeSet aset = null;
        try {
            aset = new HashPrintRequestAttributeSet();
            aset.add(PrintQuality.NORMAL);
            aset.add(OrientationRequested.PORTRAIT);
            /**
             * Suggesting the print DPI as 300
             */
            aset.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
            /**
             * Setting the printable area and the margin as 0
             */
            if (size.equals("3r")) {
                aset.add(new MediaPrintableArea(1, 1, 3, 5,
                        MediaPrintableArea.INCH));
            } else if (size.equals("4r")) {
                aset.add(new MediaPrintableArea(1, 1, 4, 6,
                        MediaPrintableArea.INCH));
            } else if (size.equals("5r")) {
                aset.add(new MediaPrintableArea(1, 1, 5, 7,
                        MediaPrintableArea.INCH));
            } else if (size.equals("6r")) {
                aset.add(new MediaPrintableArea(1, 1, 6, 8,
                        MediaPrintableArea.INCH));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aset;

    }

    public static BufferedImage getScaledInstanceToFit(BufferedImage img, Dimension size) {
        double scaleFactor = getScaleFactorToFit(img, size);
        return getScaledInstance(img, scaleFactor);
    }

    public static BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor) {

        return getScaledInstance(img, dScaleFactor, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    }

    public static double getScaleFactorToFit(BufferedImage img, Dimension size) {

        double dScale = 1;
        if (img != null) {
            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();
            dScale = getScaleFactorToFit(new Dimension(imageWidth, imageHeight), size);
        }
        return dScale;

    }

    public static double getScaleFactorToFit(Dimension original, Dimension toFit) {

        double dScale = 1d;

        if (original != null && toFit != null) {
            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = getScaleFactor(original.height, toFit.height);
            dScale = Math.min(dScaleHeight, dScaleWidth);
        }
        return dScale;

    }

    public static double getScaleFactor(int iMasterSize, int iTargetSize) {
        return (double) iTargetSize / (double) iMasterSize;
    }

    protected static BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor, Object hint) {

        BufferedImage imgScale = img;

        int iImageWidth = (int) Math.round(img.getWidth() * dScaleFactor);
        int iImageHeight = (int) Math.round(img.getHeight() * dScaleFactor);

        if (dScaleFactor <= 1.0d) {
            imgScale = getScaledDownInstance(img, iImageWidth, iImageHeight, hint);
        } else {
            imgScale = getScaledUpInstance(img, iImageWidth, iImageHeight, hint);
        }
        return imgScale;

    }

    protected static BufferedImage getScaledDownInstance(BufferedImage img,
            int targetWidth,
            int targetHeight,
            Object hint) {

//      System.out.println("Scale down...");
        int type = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage) img;

        if (targetHeight > 0 || targetWidth > 0) {

            int w = img.getWidth();
            int h = img.getHeight();

            do {
                if (w > targetWidth) {
                    w /= 2;
                    if (w < targetWidth) {
                        w = targetWidth;
                    }
                }
                if (h > targetHeight) {
                    h /= 2;
                    if (h < targetHeight) {
                        h = targetHeight;
                    }
                }
                BufferedImage tmp = new BufferedImage(Math.max(w, 1), Math.max(h, 1), type);
                Graphics2D g2 = tmp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
                g2.drawImage(ret, 0, 0, w, h, null);
                g2.dispose();
                ret = tmp;
            } while (w != targetWidth || h != targetHeight);

        } else {
            ret = new BufferedImage(1, 1, type);
        }
        return ret;
    }

    protected static BufferedImage getScaledUpInstance(BufferedImage img,
            int targetWidth,
            int targetHeight,
            Object hint) {

        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage) img;
        int w = img.getWidth();
        int h = img.getHeight();

        do {
            if (w < targetWidth) {
                w *= 2;
                if (w > targetWidth) {
                    w = targetWidth;
                }
            }

            if (h < targetHeight) {
                h *= 2;
                if (h > targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
            tmp = null;

        } while (w != targetWidth || h != targetHeight);

        return ret;

    }
}
