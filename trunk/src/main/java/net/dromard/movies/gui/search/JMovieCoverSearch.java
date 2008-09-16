package net.dromard.movies.gui.search;

import net.dromard.movies.gui.PicasaMovieCollection;
import net.dromard.movies.gui.actions.SearchFromMovieCoverAction;

public class JMovieCoverSearch extends JMovieSearch {
	private static final long serialVersionUID = -5847783820396760842L;

	public JMovieCoverSearch() {
		super("Search from moviecovers.com");
	}

	protected void search(final String query) {
		if (query.length() > 2) {
			PicasaMovieCollection.getInstance().pushAction(new SearchFromMovieCoverAction(query));
		}
	}
}
