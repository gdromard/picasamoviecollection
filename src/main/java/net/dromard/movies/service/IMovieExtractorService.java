package net.dromard.movies.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import net.dromard.movies.model.Movie;

public interface IMovieExtractorService {

	public List<? extends Movie> findByTitle(String title) throws MalformedURLException, IOException;
	
	public void fill(String title, Movie movie) throws IOException;
}
