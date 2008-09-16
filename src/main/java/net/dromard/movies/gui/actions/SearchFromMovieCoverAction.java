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
	@Override
	public void run() {
		new LoadMovieListPanelAction(searchByTitle(query)).run();
	}

	private static List<Movie> searchByTitle(String query) {
		List<Movie> movies = new ArrayList<Movie>();
		try {
			List<String> list = AppContext.getInstance().getServiceLocator().getMovieExtractorService().findByTitle(query);
			for(String title : list) {
				movies.add(AppContext.getInstance().getServiceLocator().getMovieExtractorService().getByTitle(title));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return movies;
	}
}
