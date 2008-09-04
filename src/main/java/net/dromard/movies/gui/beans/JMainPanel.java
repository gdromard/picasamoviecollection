package net.dromard.movies.gui.beans;

import javax.swing.JPanel;

public class JMainPanel extends JPanel {
	private static final long serialVersionUID = 2312079314679943373L;

	public JMainPanel(final String btnName) {
		super();
		setName(btnName);
		setOpaque(false);
	}
}
