package net.dromard.movies.gui.beans;

import javax.swing.JPanel;

import net.dromard.movies.gui.MainPanel;

public class JMainPanel extends JPanel implements MainPanel {
	private static final long serialVersionUID = 2312079314679943373L;

	public JMainPanel(final String btnName) {
		super();
		setName(btnName);
		setOpaque(false);
	}
	
	public JPanel getPanel() {
		return this;
	}
}
