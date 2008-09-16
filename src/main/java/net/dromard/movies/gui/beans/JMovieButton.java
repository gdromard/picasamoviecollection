package net.dromard.movies.gui.beans;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;

import net.dromard.movies.gui.PicasaMovieCollection;
import net.dromard.movies.gui.actions.details.JMoviePanel;
import net.dromard.movies.gui.util.ImageLoader;
import net.dromard.movies.model.Movie;

public class JMovieButton extends JBigButton {
	private static final long serialVersionUID = -6286481500347068992L;
	protected Movie movie;

	public JMovieButton(final Movie movie) {
		super(movie.getTitle());
		setToolTipText(movie.getTitle());
		this.movie = movie;
		add(new JThumbnail() {
			@Override
			protected Image loadThumbnail() {
				return ImageLoader.loadImage(movie.getThumbnailLink());
			}
		}, BorderLayout.CENTER);
	}

	/* (non-Javadoc)
	 * @see net.dromard.movies.gui.beans.JBigButton#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent evt) {
		super.mouseReleased(evt);
		PicasaMovieCollection.getInstance().register(new JMoviePanel(movie));
	}
}
