package com.ita.utils;

// SplashScreen.java
// A simple application to show a title screen in the center of the screen
// for the amount of time given in the constructor.  This class includes
// a sample main() method to test the splash screen, but it's meant for use
// with other applications.
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.UIManager;

public class SplashScreen extends JWindow {
  private int duration;
  static JProgressBar bar;
  public SplashScreen(int d) {
    duration = d;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {
	  try
	  {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    JPanel content = (JPanel)getContentPane();
		    content.setBackground(Color.white);
		
		    // Set the window's bounds, centering the window
		    int width = 640;
		    int height =400;
		    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		    int x = (screen.width-width)/2;
		    int y = (screen.height-height)/2;
		    setBounds(x,y,width,height);
		
		    // Build the splash screen
		    JLabel label = new JLabel(new ImageIcon(getClass().getResource("/images/splash.jpg")));
		    bar = new JProgressBar(0,100);
		    //bar.setValue(75);
		    JLabel copyrt = new JLabel
		      ("(c) Kryptcode", JLabel.CENTER);
		    copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		    content.add(label, BorderLayout.CENTER);
		    content.add(bar, BorderLayout.SOUTH);
		    //Color oraRed = new Color(156, 20, 20,  255);
		    //content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
		
		    // Display it
		    setVisible(true);
		    new Thread(new thread1()).start();
		    // Wait a little while, maybe while loading resources
		    try { Thread.sleep(duration); } catch (Exception e) {}
		
		    setVisible(false);
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }

  public void exitSplash() {
    //showSplash();
    System.exit(0);
  }

  public static class thread1 implements Runnable{
      public void run(){
          for (int i=0; i<=100; i++){ //Progressively increment variable i
              bar.setValue(i); //Set value
              bar.repaint(); //Refresh graphics
              try{Thread.sleep(20);} //Sleep 20 milliseconds
              catch (InterruptedException err){}
          }
      }
  }
}
