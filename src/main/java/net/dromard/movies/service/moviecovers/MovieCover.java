/**
 * 
 */
package net.dromard.movies.service.moviecovers;

import net.dromard.common.util.StringHelper;
import net.dromard.movies.model.Movie;

/**
 *
 * @author Gabriel Dromard
 */
public class MovieCover extends Movie {

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
	
}
