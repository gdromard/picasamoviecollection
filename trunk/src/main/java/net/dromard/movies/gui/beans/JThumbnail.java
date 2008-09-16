package net.dromard.movies.gui.beans;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
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
	private boolean started = false;

    /**
     * Constructor that create the button.
     * @param icon The icon to be displayed in
     */
    public JThumbnail() {
        super(new BorderLayout(10, 10));
        setOpaque(false);
    	add(progress, BorderLayout.CENTER);
    }

	/**
	 * Internal load the image.
	 * It will call the {@link #loadThumbnail()}
	 */
	private void loadImage() {
		progress.setPrimitiveWidth(30);
		progress.start();
        thumbnail = loadThumbnail();
        remove(progress);
        progress.stop();
        repaint();
        SwingUtilities.updateComponentTreeUI(this);
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
    	if (started == false && thumbnail == null) { 
    		new Thread() {
    			@Override
    			public void run() {
    				loadImage();
    			}
    		}.start();
    		started = true;
    	}
		if(thumbnail != null) {
            // Paint parent
            Insets insets = getInsets();

            // Initialize painted image
            int w = getWidth() - insets.left - insets.right;
            int h = getHeight() - insets.top - insets.bottom;
            
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int orgW = thumbnail.getWidth(null);
            int orgH = thumbnail.getHeight(null);

            int ww = w;
            int hh = ww * orgH / orgW;
            if (hh > h) {
                hh = h;
                ww = hh * orgW / orgH;
            }
            g.drawImage(thumbnail, insets.left + (w - ww) / 2, insets.top + (h - hh) / 2, ww, hh, this);
		}
    }
}
