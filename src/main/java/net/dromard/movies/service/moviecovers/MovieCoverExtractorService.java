package net.dromard.movies.service.moviecovers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import net.dromard.movies.model.Movie;
import net.dromard.movies.service.IMovieExtractorService;

public class MovieCoverExtractorService implements IMovieExtractorService {

	@Override
	public List<? extends Movie> findByTitle(String title) throws MalformedURLException, IOException {
		return MovieCoverHelper.searchMovie(title);
	}

	@Override
	public void fill(final String title, final Movie movie) throws IOException {
		MovieCoverHelper.fillMovie(title, movie);
	}
}
