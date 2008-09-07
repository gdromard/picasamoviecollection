package net.dromard.movies.gui.search;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.dromard.movies.gui.PicasaMovieCollection;
import net.dromard.movies.gui.beans.JMainPanel;
import net.dromard.movies.model.Movie;

public abstract class JMovieSearch extends JMainPanel {
	private static final long serialVersionUID = 2084140883923898042L;
	public JMovieSearch(String btnName) {
		super(btnName);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(new JLabel("Search for:"));
		final JTextField field = new JTextField();
		field.setPreferredSize(new Dimension(200, 23));
		add(field);
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchInThread(field.getText());
				}
			}
		});
	}
	
	private void searchInThread(final String query) {
		if (query.length() > 2) {
			PicasaMovieCollection.runAction("Searching for " + query,
				new Runnable() {
					public void run() {
						displayResult(search(query));
					}
				}
			);
		}
	}

	protected abstract List<Movie> search(String query);
	
	protected abstract void displayResult(List<Movie> movies);
}
