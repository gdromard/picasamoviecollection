package net.dromard.movies.service.moviecovers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import net.dromard.movies.model.Movie;
import net.dromard.movies.service.IMovieExtractorService;

public class MovieCoverExtractorService implements IMovieExtractorService {

	public List<String> findByTitle(String title) throws MalformedURLException, IOException {
		return MovieCoverHelper.searchMovie(title);
	}

	public Movie getByTitle(String title) throws MalformedURLException, IOException, ParseException {
		return MovieCoverHelper.extractMovie(title);
	}
}
