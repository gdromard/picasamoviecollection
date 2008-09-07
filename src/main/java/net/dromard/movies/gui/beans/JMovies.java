package net.dromard.movies.gui.beans;

import java.util.List;

import net.dromard.common.swing.CellFlowLayout;


public class JMovies extends JMainPanel {
	private static final long serialVersionUID = 5328602515614041820L;

	public JMovies(final String btnName, final List<JMovieButton> movies) {
		super(btnName);
		setLayout(new CellFlowLayout(10, 10));
		setContent(movies);
	}
	
	private void setContent(final List<JMovieButton> movies) {
		for (JMovieButton movie : movies) {
			add(movie);
		}
	}
}
