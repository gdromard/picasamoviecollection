package net.dromard.movies.gui.details;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import net.dromard.common.swing.SwingHelper;
import net.dromard.movies.gui.PicasaMovieCollection;
import net.dromard.movies.gui.beans.JMainPanel;
import net.dromard.movies.gui.beans.JThumbnail;
import net.dromard.movies.gui.beans.MainPanel;
import net.dromard.movies.gui.util.ImageLoader;
import net.dromard.movies.model.Movie;

public class JMovie extends JMainPanel implements MainPanel {
	private static final long serialVersionUID = 4058949588845080373L;
	private Movie movie;
	private JSplitPane hSplitPane;
	private JSplitPane vSplitPane;
	
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
		cover.setSize(100, 100);
	    // Synopsis
		JTextPane synopsis = new JTextPane();
		synopsis.setText(movie.getSynopsis());
		//synopsis.setEditable(false);
		//synopsis.setWrapStyleWord(true);
		synopsis.setPreferredSize(new Dimension(100, 100));
		synopsis.setSize(100, 100);

		hSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		hSplitPane.add(cover);
		hSplitPane.add(new JMovieFields(movie));
		hSplitPane.setOpaque(false);
		hSplitPane.setBorder(BorderFactory.createEmptyBorder());
		vSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vSplitPane.add(hSplitPane);
		vSplitPane.add(synopsis);
		vSplitPane.setOpaque(false);
		vSplitPane.setBorder(BorderFactory.createEmptyBorder());
		add(vSplitPane, BorderLayout.CENTER);
		//hSplitPane.setDividerLocation(PicasaMovieCollection.getInstance().getMainFrame().getWidth()/1.5);
	}

	@Override
	protected void paintComponent(Graphics g) {
		hSplitPane.setDividerLocation((int) (getParent().getWidth()*.45));
		vSplitPane.setDividerLocation((int) (getParent().getHeight()*.8));
		super.paintComponent(g);
	}
	
	
}
