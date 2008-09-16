/**
 * 
 */
package net.dromard.movies.gui.actions;

import net.dromard.movies.gui.beans.MainPanel;
import net.dromard.movies.gui.search.JMovieCoverSearch;

/**
 * @author Gabriel Dromard
 */
public class LoadMovieCoverSearchPanelAction extends AbstractLoadPanelAction {
	{ setMessage(""); }

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public MainPanel loadPanel() {
		return new JMovieCoverSearch();
	}
}
