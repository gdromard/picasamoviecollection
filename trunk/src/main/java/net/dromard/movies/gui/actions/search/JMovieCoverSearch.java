package net.dromard.movies.gui.actions.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;

public class JMovieCoverSearch extends JMovieSearch {
	private static final long serialVersionUID = -5847783820396760842L;

	public JMovieCoverSearch() {
		super("Search from moviecovers.com");
	}

	protected List<Movie> search(String query) {
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
