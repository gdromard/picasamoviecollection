/**
 * 
 */
package net.dromard.movies.gui.beans;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;

/**
 *
 * @author Gabriel Dromard
 */
public class JMovieCoverButton extends JMovieButton {
	private static final long serialVersionUID = 4967514817068907199L;

	/**
	 * @param movie
	 */
	public JMovieCoverButton(final Movie movie) {
		super(movie);
	}

	/* (non-Javadoc)
	 * @see net.dromard.movies.gui.beans.JMovieButton#loadThumbnail()
	 */
	@Override
	protected Image loadThumbnail() {
		try {
			URLConnection connection = AppContext.getInstance().createHttpURLConnection(movie.getImageLink());
			InputStream is = connection.getInputStream();
			BufferedImage image = ImageIO.read(is);
			is.close();
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
