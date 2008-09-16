package net.dromard.movies.gui.actions.list;

import java.util.List;

import net.dromard.common.swing.CellFlowLayout;
import net.dromard.movies.gui.actions.JMainPanel;
import net.dromard.movies.gui.actions.details.JMovieButton;


public class JMovieList extends JMainPanel {
	private static final long serialVersionUID = 5328602515614041820L;

	public JMovieList(final String btnName, final List<JMovieButton> movies) {
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