/**
 * 
 */
package net.dromard.movies.gui.actions;

import net.dromard.movies.gui.PicasaMovieCollection;
import net.dromard.movies.gui.beans.MainPanel;

/**
 * @author Gabriel Dromard
 */
public abstract class AbstractLoadPanelAction extends GuiAction {
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		PicasaMovieCollection.getInstance().register(loadPanel());
	}
	
	abstract protected MainPanel loadPanel();
}
