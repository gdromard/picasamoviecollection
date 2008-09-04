package net.dromard.movies.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

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
import net.dromard.movies.gui.xml.ElementImage;

public class JApplicationPane extends JPanel implements AppConstants {
	private static final long serialVersionUID = 5243500740199674848L;
	
	protected JCustomBar bar = new JCustomBar();
	protected JPanel mainPanel = new JPanel(new BorderLayout());
	private Hashtable<String, JPanel> panels = new Hashtable<String, JPanel>();
	
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
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(bg);
		mainPanel.setForeground(fg);
		mainPanel.setFont(ft);
		add(header(), BorderLayout.NORTH);
		//setContent(root);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(AppConf.getInstance().getPropertyAsDimension(KEY_APPLICATION_SIZE));
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
		final JCachedPanel panel = new JCachedPanel(new BorderLayout());
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
		private Domain domain;
		
		public MyButtonBar(Domain domain, JCustomBar parent) {
			super(parent, domain.getName());
			this.domain = domain;
		}

		@Override
		public void mouseReleased(MouseEvent evt) {
			bar.removeButtonsIncludingMe(this);
			setContent(domain);
		}
	}
	
	/* ------------------------- Buttons ------------------------- */
	
	@SuppressWarnings("serial")
	private class DomainButton extends BigButton {
		protected Domain domain;
		
		public DomainButton(Domain domain) {
            super(domain.getName(), domain.getIcon());
			this.domain = domain;
		}

        @Override
		public void mouseReleased(MouseEvent evt) {
        	super.mouseReleased(evt);
			setContent(domain);
		}
	}
	
	@SuppressWarnings("serial")
	private class ElementButton extends BigButton {
		protected Element element;
		
		public ElementButton(Element element) {
			super("n°" + element.getReference(), element.getIcon());
			this.element = element;
		}

        @Override
		public void mouseReleased(MouseEvent evt) {
        	super.mouseReleased(evt);
			setContent(element);
		}
	}
	
	@SuppressWarnings("serial")
	private class ImageSlider extends JPanel {
		protected Element element;
		protected int currentImage = 0;
		public ImageSlider(Element element) {
			super(new BorderLayout(10, 10));
			this.element = element;

			setOpaque(false);
			List<XmlMember> childs = element.getChilds();
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
	private class DomainPanel extends JPanel {
		Domain domain;
		
		public DomainPanel(Domain domain) {
			super(new CellFlowLayout(10, 10));
			this.domain = domain;
			
			setOpaque(false);
			List<XmlMember> childs = domain.getChilds();
			for(int i=0; i<childs.size(); ++i) {
				XmlMember child = childs.get(i);
				if (child instanceof Domain) {
					add(new DomainButton((Domain) child));
				} else if(child instanceof Element) {
					add(new ElementButton((Element) child));
				}
			}
		}
	}

	/**
	 * This panel handle the display of element's childs (Domain and Element).
	 */
	@SuppressWarnings("serial")
	private class ElementPanel extends JPanel {
		Element element;
		
		public ElementPanel(Element element) {
			super(new BorderLayout(10, 10));
			this.element = element;
			setOpaque(false);
			JForm elementDetails = new JForm(10, 10);
			elementDetails.setOpaque(false);
			Iterator<String> it = element.getAttributes().keySet().iterator();
			while(it.hasNext()) {
				String key = (String) it.next();
				String value = element.getAttribute(key);
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
			if (element.getText() != null) {
				JLabel v = new JLabel(element.getText());
				v.setFont(getDefaultFont());
				v.setForeground(getDefaultForeground());
				elementDetails.addLine(new JLabel(""), v, null);
			}
			add(elementDetails, BorderLayout.NORTH);
			add(new ImageSlider(element), BorderLayout.CENTER);
		}
	}
	
	/* ------------------------- Set Content ------------------------- */
	
    protected boolean isCached(String key) {
		if (panels.containsKey(key)) {
			setContent(panels.get(key));
			return true;
		}
		return false;
	}

	protected void addContent(JPanel panel, String key) {
		if (!isCached(key)) {
			panels.put(key, panel);
			setContent(panel);
		}
	}
	
	/**
	 * Set inner content of the application using the given panel.
	 * @param content The panel to be displayed into application.
	 */
    protected void setContent(JPanel content) {
		mainPanel.removeAll();
		mainPanel.add(content, BorderLayout.CENTER);
		//content.revalidate();
		SwingUtilities.updateComponentTreeUI(mainPanel);
	}
	
	/**
	 * Set inner content of tha application using the given element.
	 * @param element The element to be displayed into application.
	 */
	protected void setContent(Domain domain) {
		bar.addButton(new MyButtonBar(domain, bar));
		if (!isCached(""+domain.toString().hashCode())) {
			addContent(new DomainPanel(domain), ""+domain.toString().hashCode());
		}
	}
	
	/**
	 * Set inner content of tha application using the given element.
	 * @param element The element to be displayed into application.
	 */
    protected void setContent(Element element) {
		if (!isCached(""+element.toString().hashCode())) {
			addContent(new ElementPanel(element), ""+element.toString().hashCode());
		}
	}
}
