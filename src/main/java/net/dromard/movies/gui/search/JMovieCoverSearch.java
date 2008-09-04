package net.dromard.movies.gui.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.dromard.movies.AppContext;
import net.dromard.movies.gui.PicasaMovieCollection;
import net.dromard.movies.gui.beans.JMovieButton;
import net.dromard.movies.gui.beans.JMovieCoverButton;
import net.dromard.movies.gui.beans.JMovies;
import net.dromard.movies.model.Movie;

public class JMovieCoverSearch extends JMovieSearch {
	private static final long serialVersionUID = -5847783820396760842L;

	public JMovieCoverSearch() {
		super("Search from movie cover");
	}

	protected List<Movie> search(String query) {
		List<Movie> movies = new ArrayList<Movie>();
		try {
			List<String> list = AppContext.getInstance().getServiceLocator().getMovieExtractorService().findFromWWWByTitle(query);
			for(String title : list) {
				movies.add(AppContext.getInstance().getServiceLocator().getMovieExtractorService().getFromWWWByTitle(title));
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

	protected void displayResult(List<Movie> movies) {
		List<JMovieButton> buttons = new ArrayList<JMovieButton>();
		for (Movie movie : movies) {
			buttons.add(new JMovieCoverButton(movie));
		}
		PicasaMovieCollection.getInstance().getApplicationPane().register(new JMovies("Search result", buttons));
	}
}
