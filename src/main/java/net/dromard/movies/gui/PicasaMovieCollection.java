package net.dromard.movies.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.dromard.common.Util;
import net.dromard.common.swing.JSplashScreen;
import net.dromard.movies.AppConf;
import net.dromard.movies.AppConstants;
import net.dromard.movies.gui.actions.ApplicationLoaderAction;
import net.dromard.movies.gui.actions.GuiAction;
import net.dromard.movies.gui.actions.GuiActionRunner;
import net.dromard.movies.gui.beans.MainPanel;


/**
 * This class is the main (single) form of the application.
 * 
 * @author Gabriel Dromard
 */
public class PicasaMovieCollection implements AppConstants {
	private static final long serialVersionUID = -93759228224523932L;
	/** Singleton instance. */
	private static PicasaMovieCollection application;
	private JFrame mainFrame;
	private JApplicationPane applicationPane;
	private GuiActionRunner actionRunner;

	/** Retrieve instance. */
	public synchronized static PicasaMovieCollection getInstance() {
		if (application == null) {
			application = new PicasaMovieCollection();
			application.mainFrame = new JFrame(AppConf.getInstance().getProperty(KEY_APPLICATION_TITLE));
			application.mainFrame.getContentPane().setBackground(Color.WHITE);
			application.mainFrame.getContentPane().setLayout(new BorderLayout());
			application.mainFrame.getContentPane().add(application.getApplicationPane(), BorderLayout.CENTER);
			application.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			application.mainFrame.setMinimumSize(application.mainFrame.getSize());
	    	application.mainFrame.pack();
	    	application.mainFrame.setLocationRelativeTo(null);
	    	application.mainFrame.setVisible(true);
    		application.actionRunner = new GuiActionRunner(application.mainFrame);
	    }
		return application;
	}

	/** Constructor. */
	private PicasaMovieCollection() {
		super();
	}
	
	/**
	 * @return the applicationPane
	 */
	public synchronized JApplicationPane getApplicationPane() {
		if (applicationPane == null) {
			applicationPane = new JApplicationPane();
		}
		return applicationPane;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setFocus() {
		getApplicationPane().setFocus();
	}

	public void pushAction(final GuiAction guiAction) {
		actionRunner.push(guiAction);
	}

	public void register(final MainPanel panel) {
		getApplicationPane().register(panel);
	}

	/**
	 * Run the FaitMain application.
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			// Show splash screen
			String splashscreen = AppConf.getInstance().getProperty(KEY_APPLICATION_SPLASHSCREEN);
			if (splashscreen != null) {
				JSplashScreen.getSplashScreen().setSplashScreenImage(Util.loadImage(splashscreen));
				JSplashScreen.getSplashScreen().showSplashScreen();
				// Wait little so as to see the slash screen ...
				Thread.sleep(500);
			}
 			
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	try {
	            		getInstance().pushAction(new ApplicationLoaderAction(application));
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
}


