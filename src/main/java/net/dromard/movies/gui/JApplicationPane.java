package net.dromard.movies.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import net.dromard.common.Util;
import net.dromard.common.swing.JCustomBar;
import net.dromard.common.swing.JCustomHeader;
import net.dromard.movies.AppConf;
import net.dromard.movies.AppConstants;
import net.dromard.movies.gui.beans.MainPanel;

public class JApplicationPane extends JPanel implements AppConstants {
	private static final long serialVersionUID = 5243500740199674848L;
	
	protected JCustomBar bar = new JCustomBar();
	protected JPanel mainPanelContainer = new JPanel(new BorderLayout());
	
	/**
	 * Application constructor.
	 * @throws Exception 
	 */
	protected JApplicationPane() {
		super(new BorderLayout());
		initialize();
	}
	
	/**
	 * Form initialization.
	 */
	private void initialize() {
		//root = DataLoader.loadDatas();
		// Initialize this panel
		Color bg = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_BGCOLOR);
		Color fg = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_FGCOLOR);
		Font ft = AppConf.getInstance().getPropertyAsFont(KEY_APPLICATION_FONT);
		setBackground(bg);
		setForeground(fg);
		setFont(ft);
		
		mainPanelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanelContainer.setBackground(bg);
		mainPanelContainer.setForeground(fg);
		mainPanelContainer.setFont(ft);
		add(header(), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(mainPanelContainer);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(AppConf.getInstance().getPropertyAsDimension(KEY_APPLICATION_SIZE));
	}

	/**
	 * Util methods that build the header.
	 * @return The header panel.
	 * @throws IOException If an error occured while accessing the properties file.
	 */
	public JComponent header() {
        // Titles
        Color bg = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_BGCOLOR);
        Color fg = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_FGCOLOR);
        Color bgStartColor = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_BGCOLOR_START);
        Color bgEndColor = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_BGCOLOR_END);
		Font titleFont = AppConf.getInstance().getPropertyAsFont(KEY_APPLICATION_HEADER_TITLE_FONT);
        Color titleColor = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_TITLE_COLOR);
		Font subTiltleFont = AppConf.getInstance().getPropertyAsFont(KEY_APPLICATION_HEADER_SUBTITLE_FONT);
        Color subtitleColor = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_SUBTITLE_COLOR);
        Color startLinesColor = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_LINES_COLOR_START);
        Color endLinesColor = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_HEADER_LINES_COLOR_END);
        String titleImg = AppConf.getInstance().getProperty(KEY_APPLICATION_HEADER_IMAGE_LEFT);
        String bgImg = AppConf.getInstance().getProperty(KEY_APPLICATION_HEADER_IMAGE_RIGHT);
		String title = AppConf.getInstance().getProperty(KEY_APPLICATION_HEADER_TITLE);
		String subTitle = AppConf.getInstance().getProperty(KEY_APPLICATION_HEADER_SUBTITLE);
		// Header
		JCustomHeader header = new JCustomHeader(title, subTitle);
		if (titleImg != null) header.setTitleImage(Util.loadImage(titleImg));
		if (bgImg != null) header.setBackgroundImage(Util.loadImage(bgImg));
		header.setFont(titleFont);
		header.setTitleFont(titleFont);
		header.setTitleColor(titleColor);
		header.setSubTitleFont(subTiltleFont);
		header.setSubTitleColor(subtitleColor);
		header.setBackgroundColors(bgStartColor, bgEndColor);
		header.setBackground(bg);
		header.setForeground(fg);
		header.setLinesColors(startLinesColor, endLinesColor);
		header.setPreferredSize(new Dimension(300, 120));

		// Button Bar
		bg = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_BAR_BGCOLOR);
		fg = AppConf.getInstance().getPropertyAsColor(KEY_APPLICATION_BAR_FGCOLOR);
		titleFont = AppConf.getInstance().getPropertyAsFont(KEY_APPLICATION_BAR_FONT);
		bar.setBackground(bg);
		bar.setForeground(fg);
		bar.setFont(titleFont);

		// Add the first image to bar.
		//bar.addButton(new DomainButtonBar(root, bar));
		
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(header, BorderLayout.CENTER);
		panel.add(bar, BorderLayout.SOUTH);
		return panel;
	}
	
	/* ------------------------- Bar ------------------------- */
	
	@SuppressWarnings("serial")
	private class MyButtonBar extends JCustomBar.CustomBarButton {
		private MainPanel panel;
		
		public MyButtonBar(String btnName, MainPanel panel, JCustomBar parent) {
			super(parent, btnName);
			this.panel = panel;
		}

		@Override
		public void mouseReleased(MouseEvent evt) {
			bar.removeButtonsAfterMe(this);
		}

		@Override
		protected void fireRemoved() {
			super.fireRemoved();
			unregister(panel);
		}
	}

	/* ------------------------- Set Content ------------------------- */

	List<MainPanel> mainPanels = new ArrayList<MainPanel>();
	
	/**
	 * Set inner content of the application using the given panel.
	 * @param mainPanel The panel to be displayed into application.
	 */
    public void register(MainPanel mainPanel) {
    	if (!mainPanels.contains(mainPanel)) {
			bar.addButton(new MyButtonBar(mainPanel.getTitle(), mainPanel, bar));
			mainPanels.add(mainPanel);
		}
    	mainPanelContainer.removeAll();
    	mainPanelContainer.add(mainPanel.getPanel(), BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(mainPanelContainer);
	}

	/**
	 * Set inner content of the application using the given panel.
	 * @param mainPanel The panel to be removed from application.
	 * @param buttonBar The bar button to be removed from application.
	 */
    public void unregister(MainPanel mainPanel) {
    	try {
			int index = mainPanels.indexOf(mainPanel);
			while (mainPanels.size()-1 >= index) {
				mainPanels.remove(index);
			}
			if (index > 0) {
				register(mainPanels.get(mainPanels.size() - 1));
			}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
		SwingUtilities.updateComponentTreeUI(mainPanelContainer);
	}
}
