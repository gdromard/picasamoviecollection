package net.dromard.movies.gui.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import net.dromard.movies.AppContext;

public final class ImageLoader {
	private static final List<ImageLoader> POOL = new Vector<ImageLoader>();
	private static final int POOL_SIZE = 1;
	private static final int WAITTING_TIME = 100;
	private boolean buzy = false;
	
	private ImageLoader() {
	}
	
	public synchronized boolean isBuzy() {
		return buzy;
	}
	
	public Image load(final String url) {
		buzy = true;
		InputStream is = null;
		try {
			URLConnection connection = AppContext.getInstance().createHttpURLConnection(url);
			is = connection.getInputStream();
			BufferedImage image = ImageIO.read(is);
			is.close();
			System.out.println(url + " loaded successfully");
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			buzy = false;
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	public static Image loadImage(final String url) {
		int count = 0;
		while (true) {
			synchronized (POOL) {
				if (POOL.size() < POOL_SIZE) {
					POOL.add(new ImageLoader());
				}
			}
			for (ImageLoader loader : POOL) {
				if (!loader.isBuzy()) {
					return loader.load(url);
				}
			}
			++count;
			try {
				Thread.sleep(WAITTING_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}