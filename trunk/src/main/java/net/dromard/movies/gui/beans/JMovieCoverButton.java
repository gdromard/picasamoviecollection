/**
 * 
 */
package net.dromard.movies.gui.beans;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	public JMovieCoverButton(Movie movie) {
		super(movie);
	}

	/* (non-Javadoc)
	 * @see net.dromard.movies.gui.beans.JMovieButton#loadThumbnail()
	 */
	@Override
	protected Image loadThumbnail() {
		try {
			return ImageIO.read(new File(movie.getThumbnailLink()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
