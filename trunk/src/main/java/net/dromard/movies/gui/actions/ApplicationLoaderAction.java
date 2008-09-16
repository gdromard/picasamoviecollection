/**
 * 
 */
package net.dromard.movies.gui.actions;

import net.dromard.movies.gui.PicasaMovieCollection;

/**
 *
 * @author Gabriel Dromard
 */
public class ApplicationLoaderAction extends GuiAction {
	public ApplicationLoaderAction(PicasaMovieCollection application) {
		setMessage("Loading ...");
	}

	@Override
	public void run() {
		new LoadFirstPanelAction().run();
	}
}
