package net.dromard.movies.service.moviecovers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dromard.common.zip.ZipReader;
import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public final class MovieCoverHelper {

	private static String MOVIE_LIST_URL = "http://www.moviecovers.com/DATA/movies.txt";
	private static String GET_ZIP_URL = "http://www.moviecovers.com/getzip.html/{0}.zip";
	private static String GET_FILM_URL = "http://www.moviecovers.com/getfilm.html";
	private static String GET_JPG_URL = "http://www.moviecovers.com/getjpg.html/{0}.jpg";
	private static String SEARCH_URL = "http://www.moviecovers.com/multicrit.html?tri=Titre&slow=2&titre={0}";

	public static List<? extends Movie> searchMovie(String title) throws MalformedURLException, IOException {
		return searchMovieFromWebForm(title);
	}

	private static List<MovieCover> searchMovieFromWebForm(String movieName) throws MalformedURLException, IOException {
		final String SEARCH_MOVIE_KEY = "<LI><A href=\"/film/titre_";
		String url = MessageFormat.format(SEARCH_URL, URLEncoder.encode(movieName, "UTF-8"));
		List<MovieCover> foundMovies = new ArrayList<MovieCover>();
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(url);
	    BufferedReader htmlCode = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line;
	    
	    // Read all input lines
	    while ((line = htmlCode.readLine()) != null) {
	    	if (line.indexOf(SEARCH_MOVIE_KEY) > -1) {
	    		String title = URLDecoder.decode(line.substring(line.indexOf(SEARCH_MOVIE_KEY) + SEARCH_MOVIE_KEY.length(), line.indexOf(".html\">", SEARCH_MOVIE_KEY.length())), "UTF-8");
	    		foundMovies.add(new MovieCover(title));
	    		System.out.println("[Found] " + title); 
	    	}
	    }
	    return foundMovies;
	}

	public static Movie fillMovie(final String exactName, final Movie movie) throws IOException {
		String url = MessageFormat.format(GET_ZIP_URL, URLEncoder.encode(exactName, "UTF-8").replace("+", "%20"));
		System.out.println(url);
		final URLConnection connection = AppContext.getInstance().createHttpURLConnection(url);
		ZipReader reader = new ZipReader(connection.getInputStream());
		reader.readAll(new ZipReader.ZipEntryReader() {
			@Override
			public void read(InputStream input, String name) {
			    try {
					if (name.endsWith(".film")) {
						loadMovieCover(new BufferedReader(new InputStreamReader(input, "iso-8859-1")), movie);
					} else if (name.endsWith(".jpg")) {
						JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(input);
						BufferedImage image = decoder.decodeAsBufferedImage();
						movie.setCover(image);
					}
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			}
		});
		reader.close();
		return movie;
	}
	
	public static Movie extractMovie(String exactName) throws MalformedURLException, IOException, ParseException {
		return extractMovieFromZip(exactName);
	}

	private static Movie loadMovieCover(final BufferedReader filmReader, final Movie movie) throws IOException, ParseException {
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

	/* This implementation takes 2122ms when the other takes 130ms !! */
	private static List<MovieCover> searchMovieFromMovieList(String movieName) throws MalformedURLException, IOException {
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(MOVIE_LIST_URL);
	    BufferedReader movies = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String movieTitle;
	    
	    List<MovieCover> foundMovies = new ArrayList<MovieCover>();
		// Read all input lines
	    while ((movieTitle = movies.readLine()) != null) {
	    	if (movieTitle.toUpperCase().indexOf(movieName.toUpperCase()) > -1) {
	    		// Transform from ordered title to IDMC
    			movieTitle = movieTitle.replaceAll("(.*) \\(([^0-9]*)\\)", "$2 $1");
	    		foundMovies.add(new MovieCover(movieTitle));
	    		System.out.println("[Found] " + movieTitle); 
	    	}
	    }
	    movies.close();
	    return foundMovies;
	}

	private static Movie extractMovieFromFilmAndJpeg(String exactName) throws MalformedURLException, IOException, ParseException {
		// Film
		String postData = "idmc=" + URLEncoder.encode(exactName, "UTF-8");
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(GET_FILM_URL);
		connection.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();

		// Movie details
		BufferedReader filmReader = null;
		MovieCover movie = null;
		try {
			String contentType = connection.getHeaderField("Content-Type");
			String charset = contentType.substring(contentType.lastIndexOf('=') + 1);
			filmReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			movie = (MovieCover) loadMovieCover(filmReader, new MovieCover(exactName));
			// Image URL
			movie.setImageLink(MessageFormat.format(GET_JPG_URL, URLEncoder.encode(exactName, "UTF-8").replace("+", "%20")));
			movie.setThumbnailLink(movie.getImageLink());
		} finally {
			if (filmReader != null) filmReader.close();
		}
		return movie;
	}

	private static Movie extractMovieFromZip(String exactName) throws MalformedURLException, IOException, ParseException {
		return fillMovie(exactName, new MovieCover(exactName));
	}
}
