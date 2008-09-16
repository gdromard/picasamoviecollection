/**
 * 
 */
package net.dromard.movies.gui.actions;

import net.dromard.movies.gui.beans.MainPanel;
import net.dromard.movies.gui.details.JMovieDetails;
import net.dromard.movies.model.Movie;

/**
 * @author Gabriel Dromard
 */
public class LoadMovieDetailsPanelAction extends AbstractLoadPanelAction {
	private Movie movie;

	public LoadMovieDetailsPanelAction(final Movie movie) {
		setMessage(movie.getTitle());
		this.movie = movie;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public MainPanel loadPanel() {
		return new JMovieDetails(movie);
	}
}
