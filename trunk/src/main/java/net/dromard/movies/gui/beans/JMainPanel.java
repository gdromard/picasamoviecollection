package net.dromard.movies.gui.beans;

import javax.swing.JPanel;


public class JMainPanel extends JPanel implements MainPanel {
	private static final long serialVersionUID = 2312079314679943373L;
	private String title;

	public JMainPanel(final String title) {
		super();
		setTitle(title);
		setOpaque(false);
	}
	
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public JPanel getPanel() {
		return this;
	}
}
