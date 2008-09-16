/**
 * 
 */
package net.dromard.movies.gui.actions;

import java.util.List;

import net.dromard.movies.gui.beans.MainPanel;
import net.dromard.movies.gui.list.JMovieList;
import net.dromard.movies.model.Movie;

/**
 * @author Gabriel Dromard
 */
public class LoadMovieListPanelAction extends AbstractLoadPanelAction {
	private List<Movie> movies;

	public LoadMovieListPanelAction(final List<Movie> movies) {
		setMessage("");
		this.movies = movies;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public MainPanel loadPanel() {
		return new JMovieList("Search result (" + movies.size() + ")", movies);
	}
}
