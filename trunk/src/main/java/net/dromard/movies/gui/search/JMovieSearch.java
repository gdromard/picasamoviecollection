package net.dromard.movies.gui.search;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.dromard.movies.gui.beans.JMainPanel;

public abstract class JMovieSearch extends JMainPanel {
	private static final long serialVersionUID = 2084140883923898042L;
	private JTextField field; 
	public JMovieSearch(String title) {
		super(title);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(new JLabel("Search for:"));
		field = new JTextField();
		field.setPreferredSize(new Dimension(200, 23));
		add(field);
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search(field.getText());
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		super.requestFocus();
		field.requestFocus();
	}

	abstract protected void search(final String query);
}
