package net.dromard.movies.gui.details;

import java.awt.BorderLayout;
import java.awt.Image;

import net.dromard.movies.gui.beans.JMainPanel;
import net.dromard.movies.gui.beans.JThumbnail;
import net.dromard.movies.gui.beans.MainPanel;
import net.dromard.movies.gui.util.ImageLoader;
import net.dromard.movies.model.Movie;

public class JMovie extends JMainPanel implements MainPanel {
	private static final long serialVersionUID = 4058949588845080373L;
	private Movie movie;
	
	public JMovie(final Movie movie) {
		super(movie.getTitle());
		this.movie = movie;
		setLayout(new BorderLayout(10, 10));
		initialize();
	}

	private void initialize() {
		JThumbnail cover = new JThumbnail() {
			@Override
			protected Image loadThumbnail() {
				return ImageLoader.loadImage(movie.getImageLink());
			}
		};
		add(cover, BorderLayout.CENTER);
		add(new JMovieFields(movie));
	}
}
