package net.dromard.movies.service.moviecovers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;
import net.dromard.movies.service.IMovieExtractorService;

public class MovieCoverExtractorService implements IMovieExtractorService {
	private static String MOVIE_LIST_URL = "http://www.moviecovers.com/DATA/movies.txt";
	private static String GET_FILM_URL = "http://www.moviecovers.com/getfilm.html";
	private static String GET_JPG_URL = "http://www.moviecovers.com/getjpg.html/{0}.jpg";

	public List<String> findByTitle(String title) throws MalformedURLException, IOException {
		return searchMovie(title);
	}

	public Movie getByTitle(String title) throws MalformedURLException, IOException, ParseException {
		return extractMovie(title);
	}

	
	public static List<String> searchMovie(String movieName) throws MalformedURLException, IOException {
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(MOVIE_LIST_URL);
	    BufferedReader movies = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String movieTitle;
	    
	    List<String> foundMovies = new ArrayList<String>();
		// Read all input lines
	    while ((movieTitle = movies.readLine()) != null) {
	    	if (movieTitle.toUpperCase().indexOf(movieName.toUpperCase()) > -1) {
	    		// Transform from ordered title to IDMC
    			movieTitle = movieTitle.replaceAll("(.*) \\(([^0-9]*)\\)", "$2 $1");
	    		foundMovies.add(movieTitle);
	    		System.out.println("[Found] " + movieTitle); 
	    	}
	    }
	    movies.close();
	    return foundMovies;
	}
	
	public static Movie extractMovie(String exactName) throws MalformedURLException, IOException, ParseException {
		// Film
		String postData = "idmc=" + URLEncoder.encode(exactName, "UTF-8");
		System.out.println("Download Film: " + GET_FILM_URL+"?"+postData);
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(GET_FILM_URL);
		connection.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();

		// Movie details
		BufferedReader filmReader = null;
		Movie movie = null;
		try {
			String contentType = connection.getHeaderField("Content-Type");
			String charset = contentType.substring(contentType.lastIndexOf('=') + 1);
			filmReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			movie = loadMovieCover(filmReader);
			// Image URL
			movie.setImageLink(MessageFormat.format(GET_JPG_URL, URLEncoder.encode(exactName, "UTF-8").replace("+", "%20")));
			movie.setThumbnailLink(movie.getImageLink());
			System.out.println("Movie " + movie + " successfully downloaded");
		} finally {
			if (filmReader != null) filmReader.close();
		}
		return movie;
	}

	private static Movie loadMovieCover(BufferedReader filmReader) throws IOException, ParseException {
	    SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
		String title = filmReader.readLine();
	    String[] directors = filmReader.readLine().split("/");
	    String year = filmReader.readLine();
	    String[] nationalities = filmReader.readLine().split(",");
	    String[] genres = filmReader.readLine().split(",");
	    long length = formater.parse(filmReader.readLine().replace('H', ':')).getTime(); // format is 2H00
	    String[] casting = filmReader.readLine().split(";");
	    String synopsis = filmReader.readLine();
	    String provider = filmReader.readLine();
	    String originalTitle = filmReader.readLine();
	    
	    MovieCover movie = new MovieCover();
	    movie.setTitle(title);
    	movie.setDirectors(Arrays.asList(directors));
    	movie.setCast(Arrays.asList(casting));
		movie.setNationalities(Arrays.asList(nationalities));
	    movie.setGenres(Arrays.asList(genres));
	    movie.setYear(Integer.parseInt(year));
	    movie.setLength(length);
	    movie.setSynopsis(synopsis);
	    movie.setOriginalTitle(originalTitle);
	    movie.setProvider(provider);
		return movie;
	}
}
