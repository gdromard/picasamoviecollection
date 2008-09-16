/**
 * 
 */
package net.dromard.movies.gui.actions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;

/**
 * @author Gabriel Dromard
 */
public class SearchFromMovieCoverAction extends GuiAction {
	private String query;
	public SearchFromMovieCoverAction(final String query) {
		this.query = query;
		setMessage("Searching for " + query);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		new LoadMovieListPanelAction(searchByTitle(query)).run();
	}

	private static List<Movie> searchByTitle(String query) {
		List<Movie> movies = new ArrayList<Movie>();
		List<String> list = null;
		try {
			list = AppContext.getInstance().getServiceLocator().getMovieExtractorService().findByTitle(query);
			for (String title : list) {
				try {
					movies.add(AppContext.getInstance().getServiceLocator().getMovieExtractorService().getByTitle(title));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return movies;
	}
}
