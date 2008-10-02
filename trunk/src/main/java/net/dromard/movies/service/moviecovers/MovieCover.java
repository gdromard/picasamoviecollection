/**
 * 
 */
package net.dromard.movies.service.moviecovers;

import java.awt.Image;
import java.io.IOException;

import net.dromard.common.util.StringHelper;
import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;

/**
 *
 * @author Gabriel Dromard
 */
public class MovieCover extends Movie {
	private String IDMC;
    private String imageLink;
    private String thumbnailLink;

	public MovieCover(String IDMC) {
		this.IDMC = IDMC;
		setTitle(IDMC);
	}

	/* (non-Javadoc)
	 * @see net.dromard.movies.model.Movie#getTitle()
	 */
	@Override
	public String getTitle() {
		return StringHelper.capitalize(super.getTitle(), new char[] {});
	}

	/* (non-Javadoc)
	 * @see net.dromard.movies.model.Movie#getOriginalTitle()
	 */
	@Override
	public String getOriginalTitle() {
		return StringHelper.capitalize(super.getOriginalTitle(), new char[] {});
	}
	
	public synchronized Image getCover() {
		if (super.getCover() == null) {
			try {
				AppContext.getInstance().getServiceLocator().getMovieExtractorService().fill(getIDMC(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.getCover();
	}
	/**
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}
	/**
	 * @param imageLink the imageLink to set
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	/**
	 * @return the imageLink
	 */
	public String getThumbnailLink() {
		return thumbnailLink;
	}
	/**
	 * @param imageLink the imageLink to set
	 */
	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}

	public String getIDMC() {
		return IDMC;
	}
}
