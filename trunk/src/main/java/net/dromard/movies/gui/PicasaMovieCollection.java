package net.dromard.movies.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.dromard.common.Util;
import net.dromard.common.swing.InfiniteProgressPanel;
import net.dromard.common.swing.JSplashScreen;
import net.dromard.movies.AppConf;
import net.dromard.movies.AppConstants;


/**
 * This class is the main (single) form of the application.
 * 
 * @author Gabriel Dromard
 */
public class PicasaMovieCollection implements AppConstants {
	private static final long serialVersionUID = -93759228224523932L;
	/** Singleton instance. */
	private static PicasaMovieCollection application = new PicasaMovieCollection();
	private JFrame mainFrame;
	private JApplicationPane applicationPane;

	/** Retrieve instance. */
	public static PicasaMovieCollection getInstance() {
		return application;
	}

	/** Constructor. */
	private PicasaMovieCollection() {
		super();
	}
	
	/**
	 * @return the applicationPane
	 */
	public JApplicationPane getApplicationPane() {
		return applicationPane;
	}

	/**
	 * Run the FaitMain application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Show splash screen
			String splashscreen = AppConf.getInstance().getProperty(KEY_APPLICATION_SPLASHSCREEN);
			if (splashscreen != null) {
				JSplashScreen.getSplashScreen().setSplashScreenImage(Util.loadImage(splashscreen));
				JSplashScreen.getSplashScreen().showSplashScreen();
			}
 			
	        // Wait little so as to see the slash screen ...
			Thread.sleep(500);
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	try {
		    			application.mainFrame = new JFrame(AppConf.getInstance().getProperty(KEY_APPLICATION_TITLE));
		    			application.applicationPane = new JApplicationPane();
		    			application.mainFrame.getContentPane().setBackground(Color.WHITE);
		    			application.mainFrame.getContentPane().setLayout(new BorderLayout());
		    			application.mainFrame.getContentPane().add(application.applicationPane, BorderLayout.CENTER);
		    			application.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    			application.mainFrame.setMinimumSize(application.mainFrame.getSize());
		            	application.mainFrame.pack();
		            	application.mainFrame.setLocationRelativeTo(null);
		            	application.mainFrame.setVisible(true);
		            } catch (Error e) {
		            	e.printStackTrace();
		            	JSplashScreen.getSplashScreen().hideSplashScreen();
		            	JOptionPane.showMessageDialog(null, "An error occured while loading application. ("+e.getMessage()+")", "Error Message", JOptionPane.ERROR_MESSAGE);
		            }	
	            }
	        });        
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured while trying to load datas. ("+e.getMessage()+")", "Error Message", JOptionPane.ERROR_MESSAGE);
			JSplashScreen.getSplashScreen().hideSplashScreen();
			//System.exit(0);
		}	
	}

	private static final InfiniteProgressPanel progress = new InfiniteProgressPanel();
	public static void runAction(final String message, final Runnable action) {
		progress.setText(message);
		progress.setPrimitiveWidth(application.mainFrame.getWidth()/5);
		application.mainFrame.setGlassPane(progress);
		progress.start();
		new Thread() {
			public void run() {
				try {
					action.run();
				} finally {
					progress.stop();
					application.mainFrame.remove(progress);
					application.mainFrame.repaint();
				}
			}
		}.start();
	}
}


