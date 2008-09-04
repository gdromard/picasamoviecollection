package net.dromard.movies.gui.beans;

import java.awt.BorderLayout;
import java.awt.Image;

import net.dromard.common.swing.JImage;
import net.dromard.movies.model.Movie;

public abstract class JMovieButton extends JBigButton {
	private static final long serialVersionUID = -6286481500347068992L;
	protected Movie movie; 

	public JMovieButton(final Movie movie) {
		super(movie.getTitle());
		new Thread() {
			@Override
			public void run() {
				setImage();
			}
		}.start();
	}

	private void setImage() {
        thumbnail = new JImage(loadThumbnail(), Image.SCALE_SMOOTH);
        thumbnail.setOpaque(false);
        add(thumbnail, BorderLayout.CENTER);
        revalidate();
	}
	
	protected abstract Image loadThumbnail();
}
