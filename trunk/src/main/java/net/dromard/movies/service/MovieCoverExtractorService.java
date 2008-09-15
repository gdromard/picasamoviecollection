package net.dromard.movies.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.dromard.common.io.StreamHelper;
import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;

public class MovieCoverExtractorService implements IMovieExtractorService {
	//private static String SEARCH_URL = "http://www.moviecovers.com/multicrit.html?tri=Titre&slow=2&titre={0}";
	private static String GET_ZIP_URL = "http://www.moviecovers.com/getzip.html/{0}.zip";
	private static String MOVIE_LIST_URL = "http://www.moviecovers.com/DATA/movies.txt";
	private static String GET_FILM_URL = "http://www.moviecovers.com/getfilm.html";
	private static String GET_JPG_URL = "http://www.moviecovers.com/getjpg.html/{0}.jpg";
	//private static Map<String, String> titleToUrl = new HashMap<String, String>();

	public List<String> findFromWWWByTitle(String title) throws MalformedURLException, IOException {
		return searchMovie(title);
	}

	public Movie getFromWWWByTitle(String title) throws MalformedURLException, IOException, ParseException {
		return extractMovie(title);
	}

	
	public static List<String> searchMovie(String movieName) throws MalformedURLException, IOException {
		/*
		final String SEARCH_MOVIE_KEY = "<LI><A href=\"/film/titre_";
		String url = MessageFormat.format(SEARCH_URL, URLEncoder.encode(movieName, "UTF-8"));
		List<String> foundMovies = new ArrayList<String>();
		System.out.println("Requesting to: " + url);
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(url);
	    BufferedReader htmlCode = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line;
	    
	    // Read all input lines
	    while ((line = htmlCode.readLine()) != null) {
	    	if (line.indexOf(SEARCH_MOVIE_KEY) > -1) {
	    		String titleUrl = line.substring(line.indexOf(SEARCH_MOVIE_KEY) + SEARCH_MOVIE_KEY.length(), line.indexOf(".html\">", SEARCH_MOVIE_KEY.length()));
	    		String title = line.substring(line.indexOf('>', SEARCH_MOVIE_KEY.length()) + 1).replaceAll("</[aA]>", "");
	    		titleToUrl.put(title, titleUrl);
	    		foundMovies.add(title);
	    	}
	    }
	    */
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
		// Remove Year at end of movie Name
		/*
		if (exactName.substring(exactName.length()-7).matches(" \\([0-9]*\\)")) {
			exactName = exactName.substring(0, exactName.length()-7);
		}
		String url = MessageFormat.format(GET_URL, URLEncoder.encode(exactName, "UTF-8")).replaceAll(" ", "%20");
		*/
		/**/
		// Film
		//String postData = "idmc=" + URLEncoder.encode("Matrix", "UTF-8");
		String postData = "idmc=" + URLEncoder.encode(exactName, "UTF-8");
		System.out.println("Download Film: " + GET_FILM_URL+"?"+postData);
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(GET_FILM_URL);
		connection.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();
		File movieFile = download(exactName+".film", connection.getInputStream(), AppContext.getInstance().getImagePath());
		System.out.println("Movie successfully downloaded to: " + movieFile);

		// Image
		String coverUrl = MessageFormat.format(GET_JPG_URL, URLEncoder.encode(exactName, "UTF-8").replace("+", "%20"));
		System.out.println("Download Image: " + coverUrl);
		connection = AppContext.getInstance().createHttpURLConnection(coverUrl);
		HttpURLConnection.setFollowRedirects(true);
		File coverFile = download(exactName+".jpg", connection.getInputStream(), AppContext.getInstance().getImagePath());
		System.out.println("Movie cover successfully downloaded to: " + coverFile.getAbsolutePath());
		/**/
		
		// Download Zip File
		/**
		String url = MessageFormat.format(GET_ZIP_URL, exactName.replaceAll(" ", "%20"));
		System.out.println("Download Zip: " + url);
		URLConnection connection = AppContext.getInstance().createHttpURLConnection(url);
		File zipTmp = download(exactName, connection.getInputStream(), AppContext.getInstance().getTempPath());
		System.out.println("Zip file successfully downloaded to " + zipTmp.getAbsolutePath());
		File movieFile = extractZipFile(zipTmp);
		/**/
		Movie movie = loadMovie(movieFile);
	    movie.setImageLink(coverFile.toURL().toString());

		return movie;
	}
	
    private static Movie loadMovie(File movieCoverFile) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
	    BufferedReader filmReader = new BufferedReader(new InputStreamReader(new FileInputStream(movieCoverFile), "UTF-8"));
	    String title = filmReader.readLine();
	    String[] directors = filmReader.readLine().split("/");
	    String year = filmReader.readLine();
	    String nationality = filmReader.readLine();
	    String genre = filmReader.readLine();
	    String length = filmReader.readLine(); // format is 2H00
	    String[] casting = filmReader.readLine().split(";");
	    String synopsis = filmReader.readLine();
	    String provider = filmReader.readLine();
	    String originalTitle = filmReader.readLine();
	    
	    Movie movie = new Movie();
	    movie.setTitle(title);
    	movie.setDirectors(Arrays.asList(directors));
    	movie.setCast(Arrays.asList(casting));
		movie.setNationality(nationality);
	    movie.setGenre(genre);
	    movie.setYear(Integer.parseInt(year));
	    SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
	    movie.setLength(formater.parse(length.replace('H', ':')).getTime());
	    movie.setSynopsis(synopsis);
	    movie.setOriginalTitle(originalTitle);
	    movie.setProvider(provider);
		return movie;
	}
    
	private static File extractZipFile(File zipFile) {
        
        try {
        	File image = null;
        	File film = null;
            // Create input and output streams
            ZipInputStream inStream = new ZipInputStream(new FileInputStream(zipFile));
            
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            int nrBytesRead;
            
            // Get next zip entry and start reading data
            while ((entry = inStream.getNextEntry()) != null) {
            	if (entry.getName().endsWith(".film") || entry.getName().endsWith(".jpg")) {
            		OutputStream outStream = null;
            		if (entry.getName().endsWith(".film")) {
            			film = new File(AppContext.getInstance().getTempPath().getAbsolutePath() + File.separator + entry.getName());
            			outStream = new FileOutputStream(film);
            		} else {
            			image  = new File(AppContext.getInstance().getImagePath().getAbsolutePath() + File.separator + entry.getName());
            			System.out.println("Image has been saved to : " + image.getAbsolutePath());
            			outStream = new FileOutputStream(image);
            		}
            		while ((nrBytesRead = inStream.read(buffer)) > 0) {
            			outStream.write(buffer, 0, nrBytesRead);
            		}
            		outStream.close();
            	}
            }
                    
            // Finish off by closing the streams
            inStream.close();
            return film;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	private static File download(final String fileName, final InputStream stream, final File destinationPath) throws IOException {
		File tmp = new File(destinationPath.getAbsolutePath() + File.separator + fileName);
		StreamHelper.streamCopier(stream, new FileOutputStream(tmp));
		return tmp;
	}
}
