package net.dromard.movies.gui.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import net.dromard.movies.AppContext;

public final class ImageLoader {
	public static Image loadImage(final String url) {
		try {
			URLConnection connection = AppContext.getInstance().createHttpURLConnection(url);
			InputStream is = connection.getInputStream();
			BufferedImage image = ImageIO.read(is);
			is.close();
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
