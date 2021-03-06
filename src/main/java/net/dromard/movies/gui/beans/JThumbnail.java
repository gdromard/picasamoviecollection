package net.dromard.movies.gui.beans;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.dromard.common.swing.InfiniteProgressPanel;

/**
 * This class is an implementation of a button that contains an image.
 * 
 * @author Gabriel Dromard
 */
@SuppressWarnings("serial")
public abstract class JThumbnail extends JPanel implements ImageObserver {
	private Image thumbnail;
	private InfiniteProgressPanel progress = new InfiniteProgressPanel();
	private boolean loading = false;
	private int started = 0;

    /**
     * Constructor that create the button.
     * @param icon The icon to be displayed in
     */
    public JThumbnail() {
        super(new BorderLayout(10, 10));
        setOpaque(false);
    }

	/**
	 * Internal load the image.
	 * It will call the {@link #loadThumbnail()}
	 */
	private void loadImage() {
		++started;
		loading = true;
    	add(progress, BorderLayout.CENTER);
		progress.setPrimitiveWidth(30);
		progress.start();
        thumbnail = compactImage(loadThumbnail());
        remove(progress);
        progress.stop();
        repaint();
        SwingUtilities.updateComponentTreeUI(this);
        loading = false;
	}

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if (infoflags == ALLBITS) {
            repaint();
        }
        return true;
    }

	/**
	 * Load the thumbnail image
	 * @return Return the loaded image
	 */
	protected abstract Image loadThumbnail();

    /**
     * After having paint the component, we start an infinite progress and 
     * we load the thumbnail in background.
     * @see net.dromard.common.swing.JShadowPanel#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	if (!loading && started < 2 && thumbnail == null) { 
    		new Thread() {
    			@Override
    			public void run() {
    				loadImage();
    			}
    		}.start();
    	}
		if (thumbnail != null) {
            // Paint parent
            Insets insets = getInsets();

            // Initialize painted image
            int w = getWidth() - insets.left - insets.right;
            int h = getHeight() - insets.top - insets.bottom;
            
            
            int orgW = thumbnail.getWidth(null);
            int orgH = thumbnail.getHeight(null);

            int ww = w;
            int hh = ww * orgH / orgW;
            if (hh > h) {
                hh = h;
                ww = hh * orgW / orgH;
            }
            
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(thumbnail, insets.left + (w - ww) / 2, insets.top + (h - hh) / 2, ww, hh, this);
		}
    }
    
    private Image compactImage(Image image) {
    	if (image == null) return image;
        // Paint parent
        Insets insets = getInsets();

        // Initialize painted image
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        
        
        int orgW = image.getWidth(null);
        int orgH = image.getHeight(null);

        int ww = w;
        int hh = ww * orgH / orgW;
        if (hh > h) {
            hh = h;
            ww = hh * orgW / orgH;
        }
        
        if (ww > orgW || hh > orgH) return image;
        BufferedImage tmp = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = (Graphics2D) tmp.getGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2D.drawImage(image, 0, 0, ww, hh, this);
        System.out.println("[DEBUG] Compact done " + (orgW - ww)/100);
        return tmp;
    }
}
