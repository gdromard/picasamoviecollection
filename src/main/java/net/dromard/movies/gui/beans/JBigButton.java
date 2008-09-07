package net.dromard.movies.gui.beans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.dromard.common.swing.JImage;
import net.dromard.common.swing.JShadowPanel;
import net.dromard.movies.gui.PicasaMovieCollection;

/**
 * This class is an implementation of a button that contains an image.
 * 
 * @author Gabriel Dromard
 */
@SuppressWarnings("serial")
public class JBigButton extends JShadowPanel implements MouseListener {
    protected JImage thumbnail;
    protected Image tmp;
    protected JLabel label;
    protected String imageResourceName;
    protected Color bgHover;
    protected Color fgHover;
    protected Color background;
    protected Color foreground;

    /**
     * Constructor that create the button.
     * @param icon The icon to be displayed in
     * @param text The button text.
     */
    public JBigButton(String text) {
        super(new BorderLayout(10, 10));
        background = new Color(255, 255, 255);
        foreground = PicasaMovieCollection.getInstance().getApplicationPane().getForeground();
        bgHover = new Color(230, 230, 230);
        fgHover = foreground.brighter();
        setSize(new Dimension(140, 140));
        setShadowWidth(5);
        setLineBorderColor(foreground);
        //setBorder(BorderFactory.createLineBorder(foreground));
        setOpaque(true);
        setFont(PicasaMovieCollection.getInstance().getApplicationPane().getFont());
        setBackground(background);
        setForeground(foreground);
        addMouseListener(this);
        // Content
        label = new JLabel(text);
        label.setOpaque(false);
        label.setFont(getFont());
        label.setForeground(foreground);
        label.setBackground(background);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.SOUTH);
    }

    /* ------------- MouseListerner implementation ------------- */

    /**
     * Executed when the button is clicked. 
	 * NOTE: this implenentation is empty.
     */
    public void mouseClicked(MouseEvent arg0) {
        // Empty implementation
    }

    /**
     * Executed when the button is roll over. 
	 * This implenentation change the border and the background color.
     */
    public void mouseEntered(MouseEvent arg0) {
        setForeground(fgHover);
        setBackground(bgHover);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Executed when the mouse quit the button.
	 * This implenentation change back the border and the background color to its original value.
     */
    public void mouseExited(MouseEvent arg0) {
        setBackground(background);
        setForeground(foreground);
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Executed when the button is clicked. 
	 * NOTE: this implenentation is empty.
     */
    public void mousePressed(MouseEvent arg0) {
        // Empty implementation
    }

    /**
     * Executed when the button is clicked.
     * NOTE: this implenentation is empty.
     */
    public void mouseReleased(MouseEvent evt) {
        setBackground(background);
        setForeground(foreground);
    }
}
