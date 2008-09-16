package net.dromard.movies.gui.actions.details;

import java.awt.BorderLayout;
import java.awt.Image;

import net.dromard.movies.gui.actions.JMainPanel;
import net.dromard.movies.gui.actions.MainPanel;
import net.dromard.movies.gui.beans.JThumbnail;
import net.dromard.movies.gui.util.ImageLoader;
import net.dromard.movies.model.Movie;

public class JMoviePanel extends JMainPanel implements MainPanel {
	private static final long serialVersionUID = 4058949588845080373L;
	private Movie movie;
	
	public JMoviePanel(final Movie movie) {
		super(movie.getTitle());
		this.movie = movie;
		setLayout(new BorderLayout(10, 10));
		setContent();
	}

	private void setContent() {
		JThumbnail cover = new JThumbnail() {
			@Override
			protected Image loadThumbnail() {
				return ImageLoader.loadImage(movie.getImageLink());
			}
		};
		add(cover, BorderLayout.WEST);
	}
}
