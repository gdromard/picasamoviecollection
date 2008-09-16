package net.dromard.movies.gui.list;

import java.util.List;

import net.dromard.common.swing.CellFlowLayout;
import net.dromard.movies.gui.beans.JMainPanel;
import net.dromard.movies.gui.details.JMovieButton;
import net.dromard.movies.model.Movie;


public class JMovieList extends JMainPanel {
	private static final long serialVersionUID = 5328602515614041820L;

	public JMovieList(final String title, final List<Movie> movies) {
		super(title);
		setLayout(new CellFlowLayout(10, 10));
		setContent(movies);
	}

	
	private void setContent(final List<Movie> movies) {
		for (Movie movie : movies) {
			add(new JMovieButton(movie));
		}
	}
}
