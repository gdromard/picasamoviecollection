package net.dromard.movies.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.dromard.common.Util;
import net.dromard.common.swing.CellFlowLayout;
import net.dromard.common.swing.JCachedPanel;
import net.dromard.common.swing.JCustomBar;
import net.dromard.common.swing.JCustomHeader;
import net.dromard.common.swing.JForm;
import net.dromard.common.swing.JImage;
import net.dromard.common.xml.XmlMember;
import net.dromard.movies.AppConf;
import net.dromard.movies.AppConstants;
import net.dromard.movies.gui.beans.Album;
import net.dromard.movies.gui.beans.JMainPanel;
import net.dromard.movies.gui.beans.Photo;
import net.dromard.movies.gui.search.JMovieCoverSearch;
import net.dromard.movies.gui.xml.ElementImage;

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
		register(new JMovieCoverSearch());
	}

	private Color getDefaultForeground() {
		return getForeground();
	}
	
    public Font getDefaultFont() {
		return getFont();
	}

	/**
	 * Util methods that build the header.
	 * @return The header panel.
	 * @throws IOException If an error occured while accessing the properties file.
	 */
	public JComponent header() {
		final JPanel panel = new JCachedPanel(new BorderLayout());
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
		
		
		panel.add(header, BorderLayout.CENTER);
		panel.add(bar, BorderLayout.SOUTH);
		return panel;
	}
	
	/* ------------------------- Bar ------------------------- */
	
	@SuppressWarnings("serial")
	private class MyButtonBar extends JCustomBar.CustomBarButton {
		private JMainPanel panel;
		
		public MyButtonBar(String btnName, JMainPanel panel, JCustomBar parent) {
			super(parent, btnName);
			this.panel = panel;
		}

		@Override
		public void mouseReleased(MouseEvent evt) {
			bar.removeButtonsIncludingMe(this);
		}

		@Override
		protected void fireRemoved() {
			super.fireRemoved();
			unregister(panel);
		}
	}
	
	@SuppressWarnings("serial")
	private class ImageSlider extends JMainPanel {
		protected Photo photo;
		protected int currentImage = 0;
		public ImageSlider(Photo photo) {
			super("Photos");
			setLayout(new BorderLayout(10, 10));
			this.photo = photo;

			setOpaque(false);
			List<XmlMember> childs = photo.getChilds();
			if (childs.size() > 0) {
				XmlMember child = childs.get(currentImage);
				if (child instanceof ElementImage) {
					JImage image = new JImage(Util.loadImage(((ElementImage) child).getPath()), Image.SCALE_SMOOTH);
                    image.setOpaque(false);
					add(image, BorderLayout.CENTER);
				}
			}
		}
	}
	
	/* ------------------------- Panel ------------------------- */

	/**
	 * This panel handle the display of element's childs (Domain and Element).
	 */
	@SuppressWarnings("serial")
	private class AlbumPanel extends JMainPanel {
		Album album;
		
		public AlbumPanel(Album album) {
			super("Album");
			setLayout(new CellFlowLayout(10, 10));
			this.album = album;
			
			setOpaque(false);
			List<XmlMember> childs = album.getChilds();
			for(int i=0; i<childs.size(); ++i) {
				XmlMember child = childs.get(i);
				if (child instanceof Album) {
					//add(new AlbumButton((Album) child));
				} else if(child instanceof Photo) {
					//add(new PhotoButton((Photo) child));
				}
			}
		}
	}

	/**
	 * This panel handle the display of element's childs (Domain and Element).
	 */
	@SuppressWarnings("serial")
	private class ElementPanel extends JPanel {
		Photo photo;
		
		public ElementPanel(Photo photo) {
			super(new BorderLayout(10, 10));
			this.photo = photo;
			setOpaque(false);
			JForm elementDetails = new JForm(10, 10);
			elementDetails.setOpaque(false);
			Iterator<String> it = photo.getAttributes().keySet().iterator();
			while(it.hasNext()) {
				String key = (String) it.next();
				String value = photo.getAttribute(key);
				String localizedKey = AppConf.getInstance().getProperty("application.attributes."+key);
				if (localizedKey != null) key = localizedKey;
                JLabel k = new JLabel(key);
				k.setFont(getDefaultFont());
				k.setForeground(getDefaultForeground());
				JLabel v = new JLabel(value);
				v.setFont(getDefaultFont());
				v.setForeground(getDefaultForeground());
				elementDetails.addLine(k, v, null);
			}
			if (photo.getText() != null) {
				JLabel v = new JLabel(photo.getText());
				v.setFont(getDefaultFont());
				v.setForeground(getDefaultForeground());
				elementDetails.addLine(new JLabel(""), v, null);
			}
			add(elementDetails, BorderLayout.NORTH);
			add(new ImageSlider(photo), BorderLayout.CENTER);
		}
	}
	
	/* ------------------------- Set Content ------------------------- */

	List<JMainPanel> mainPanels = new ArrayList<JMainPanel>();
	
	/**
	 * Set inner content of the application using the given panel.
	 * @param mainPanel The panel to be displayed into application.
	 */
    public void register(JMainPanel mainPanel) {
		bar.addButton(new MyButtonBar(mainPanel.getName(), mainPanel, bar));
		mainPanelContainer.removeAll();
		mainPanelContainer.add(mainPanel, BorderLayout.CENTER);
		if (!mainPanels.contains(mainPanel)) {
			mainPanels.add(mainPanel);
		}
		//content.revalidate();
		//SwingUtilities.updateComponentTreeUI(mainPanelContainer);
	}

	/**
	 * Set inner content of the application using the given panel.
	 * @param content The panel to be displayed into application.
	 */
    public void unregister(JMainPanel mainPanel) {
		bar.addButton(new MyButtonBar(mainPanel.getName(), mainPanel, bar));
		mainPanelContainer.removeAll();
		mainPanelContainer.add(mainPanel, BorderLayout.CENTER);
		int index = mainPanels.indexOf(mainPanel);
		while (mainPanels.size() >= index) {
			mainPanel.remove(index);
		}
		register(mainPanels.get(mainPanels.size() - 1));
		//maiPanels.removeElementAt()
		//content.revalidate();
		//SwingUtilities.updateComponentTreeUI(mainPanelContainer);
	}
}
