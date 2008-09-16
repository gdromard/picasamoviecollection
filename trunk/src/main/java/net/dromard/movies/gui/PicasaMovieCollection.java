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
import net.dromard.movies.gui.actions.GuiAction;
import net.dromard.movies.gui.actions.GuiActionRunner;
import net.dromard.movies.gui.actions.search.JMovieCoverSearch;


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
	public static PicasaMovieCollection getInstance() {
		if (application == null) {
			application = new PicasaMovieCollection();
		}
		return application;
	}

	/** Constructor. */
	private PicasaMovieCollection() {
		super();
		mainFrame = new JFrame(AppConf.getInstance().getProperty(KEY_APPLICATION_TITLE));
		actionRunner = new GuiActionRunner(mainFrame);
		actionRunner.push(new GuiAction() {
			{ setMessage("Loading ..."); }
			@Override
			public void run() {
				mainFrame.getContentPane().setBackground(Color.WHITE);
				mainFrame.getContentPane().setLayout(new BorderLayout());
				applicationPane = new JApplicationPane(new JMovieCoverSearch());
				mainFrame.getContentPane().add(application.applicationPane, BorderLayout.CENTER);
				mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				mainFrame.setMinimumSize(application.mainFrame.getSize());
		    	mainFrame.pack();
		    	mainFrame.setLocationRelativeTo(null);
		    	mainFrame.setVisible(true);
			}
		});
	}
	
	/**
	 * @return the applicationPane
	 */
	public JApplicationPane getApplicationPane() {
		return applicationPane;
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
	            		getInstance();
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


